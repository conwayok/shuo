package com.conway.shuo.service

import com.conway.shuo.dto.LikeType
import com.conway.shuo.dto.json.Comment
import com.conway.shuo.dto.json.CreateCommentRequest
import com.conway.shuo.dto.json.CreatePostRequest
import com.conway.shuo.dto.json.CustomResponse
import com.conway.shuo.dto.json.LikeRequest
import com.conway.shuo.dto.json.PostResponse
import com.conway.shuo.dto.json.PostIdResponse
import com.conway.shuo.entity.CommentModel
import com.conway.shuo.entity.LikeModel
import com.conway.shuo.entity.PostModel
import com.conway.shuo.repository.CommentRepository
import com.conway.shuo.repository.LikeRepository
import com.conway.shuo.repository.PostRepository
import com.conway.shuo.repository.specifications.PostSpecs
import com.conway.shuo.util.CustomResponseUtil
import com.conway.shuo.util.ProjectUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

/** Created by Conway */
@Service
class PostsService @Autowired constructor(
  private val postRepository: PostRepository,
  private val commentRepository: CommentRepository,
  private val likeRepository: LikeRepository
) {

  fun createPost(
    username: String,
    createPostRequest: CreatePostRequest
  ): CustomResponse<PostIdResponse?> {
    var postId = ProjectUtils.generateUUID()
    while (postRepository.countByPostId(postId) > 0) {
      postId = ProjectUtils.generateUUID()
    }
    val postModel = PostModel(
      postId = postId,
      username = username,
      title = createPostRequest.title,
      textContent = createPostRequest.textContent,
      images = createPostRequest.images
    )
    postRepository.save(postModel)
    return CustomResponseUtil.ok(PostIdResponse(postId))
  }

  fun getPost(postId: String, username: String?): CustomResponse<PostResponse?> {

    val postModelOptional = postRepository.findById(postId)

    if (!postModelOptional.isPresent) {
      return CustomResponseUtil.notFoundError()
    }

    val postModel = postModelOptional.get()


    var likeType: LikeType? = null

    if (username != null) {
      likeRepository.getLikeStatus(username, postId).ifPresent {
        likeType = it.likeType
      }
    }

    val postBasicResponse = PostResponse(
      postId = postId,
      author = postModel.username,
      title = postModel.title,
      textContent = postModel.textContent,
      images = postModel.images,
      createTime = postModel.createTime!!,
      commentCount = postModel.commentCount,
      views = postModel.views,
      points = postModel.points,
      likeType = likeType
    )

    postModel.views = postModel.views + 1
    postRepository.save(postModel)

    return CustomResponseUtil.ok(postBasicResponse)
  }

  fun getPosts(
    username: String?,
    pageable: Pageable
  ): CustomResponse<List<PostResponse>> {

    // todo: can return Page object?

    val pages = postRepository.findAll(pageable)
    val postModels = pages.content
    val posts = mapPostModelsToPostBasicResponse(username, postModels)
    return CustomResponseUtil.ok(posts)
  }

  fun getUserPosts(
    authUsername: String?,
    getUsername: String,
    pageable: Pageable
  ): CustomResponse<List<PostResponse>> {
    val postModels = postRepository.findAllByUsername(getUsername, pageable)
    val posts = mapPostModelsToPostBasicResponse(authUsername, postModels)
    return CustomResponseUtil.ok(posts)
  }

  fun deletePost(postId: String, username: String): CustomResponse<Unit> {
    val postOptional = postRepository.findById(postId)
    if (!postOptional.isPresent) return CustomResponseUtil.notFoundError()
    val postModel = postOptional.get()
    if (postModel.username != username) return CustomResponseUtil.forbiddenError()
    postRepository.delete(postModel)
    val comments = commentRepository.findAllByPostId(postId)
    commentRepository.deleteAll(comments)
    return CustomResponseUtil.ok()
  }

  fun createComment(
    username: String,
    postId: String,
    createCommentRequest: CreateCommentRequest
  ): CustomResponse<Unit> {

    val postOpt = postRepository.findById(postId)

    if (!postOpt.isPresent) return CustomResponseUtil.notFoundError()

    val commentModel =
      CommentModel(
        postId = postId,
        username = username,
        textContent = createCommentRequest.textContent
      )
    commentRepository.save(commentModel)

    val post = postOpt.get()
    post.commentCount = post.commentCount + 1
    postRepository.save(post)

    return CustomResponseUtil.ok()
  }

  fun getComments(postId: String, pageable: Pageable): CustomResponse<List<Comment>> {
    val commentModels = commentRepository.findAllByPostId(postId, pageable)
    val comments = commentModels.map {
      Comment(author = it.username, createTime = it.createTime!!, textContent = it.textContent)
    }
    return CustomResponseUtil.ok(comments)
  }

  fun likePost(username: String, postId: String, likeRequest: LikeRequest): CustomResponse<Unit> {
    val postOptional = postRepository.findById(postId)

    if (!postOptional.isPresent) return CustomResponseUtil.notFoundError()

    val post = postOptional.get()


    val likeModelOptional = likeRepository.getLikeStatus(username, postId)

    if (likeModelOptional.isPresent) {
      val likeModel = likeModelOptional.get()
      // revert
      when (likeModel.likeType) {
        LikeType.UPVOTE -> post.points = post.points - 1
        LikeType.DOWNVOTE -> post.points = post.points + 1
      }
      likeRepository.delete(likeModel)
    } else {
      val likeType = likeRequest.likeType
      when (likeType) {
        LikeType.UPVOTE -> post.points = post.points + 1
        LikeType.DOWNVOTE -> post.points = post.points - 1
      }
      val likeModel = LikeModel(username = username, postId = postId, likeType = likeType)
      likeRepository.save(likeModel)
    }

    postRepository.save(post)

    return CustomResponseUtil.ok()
  }

  fun searchPost(
    username: String?,
    keyword: String,
    pageable: Pageable
  ): CustomResponse<List<PostResponse>> {
    // todo: multiple keywords search possible?

    val postModels = postRepository.findAll(
      Specification.where(PostSpecs.titleContains(keyword))
        .or(PostSpecs.textContentContains(keyword))
        .or(PostSpecs.usernameContains(keyword)),
      pageable
    ).content

    val posts = mapPostModelsToPostBasicResponse(username, postModels)
    return CustomResponseUtil.ok(posts)
  }


  private fun mapPostModelsToPostBasicResponse(
    username: String?,
    postModels: List<PostModel>,
  ): List<PostResponse> {

    val likeTypes = mutableMapOf<String, LikeType>()

    if (username != null) {
      val postIds = postModels.map { it.postId!! }
      likeRepository.getLikesFromPostIds(username, postIds).forEach {
        likeTypes[it.postId] = it.likeType
      }
    }

    return postModels.map {
      PostResponse(
        postId = it.postId!!,
        author = it.username,
        title = it.title,
        textContent = it.textContent,
        images = it.images,
        createTime = it.createTime!!,
        views = it.views,
        commentCount = it.commentCount,
        points = it.points,
        likeType = likeTypes[it.postId]
      )
    }
  }
}