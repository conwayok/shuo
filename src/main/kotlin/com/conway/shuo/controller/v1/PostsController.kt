package com.conway.shuo.controller.v1

import com.conway.shuo.dto.json.*
import com.conway.shuo.service.PostsService
import com.conway.shuo.util.CustomResponseUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/** Created by Conway */
@RestController
@RequestMapping(value = ["${V1Path.PATH}/posts"])
class PostsController @Autowired constructor(private val postsService: PostsService) {

  private val logger = LoggerFactory.getLogger(PostsController::class.java)

  @PostMapping
  fun createPost(
    authentication: Authentication,
    @RequestBody
    createPostRequest: CreatePostRequest
  ): ResponseEntity<CustomResponse<PostIdResponse?>> {
    val username = authentication.name
    logger.info("createPost $username")
    val response = postsService.createPost(username, createPostRequest)
    return CustomResponseUtil.toResponseEntity(response)
  }

  @GetMapping(value = ["/{postId}"])
  fun getPost(
    authentication: Authentication?,
    @PathVariable
    postId: String
  ): ResponseEntity<CustomResponse<PostResponse?>> {
    val username = authentication?.name
    val r = postsService.getPost(postId, username)
    return CustomResponseUtil.toResponseEntity(r)
  }

  @DeleteMapping(value = ["/{postId}"])
  fun deletePost(
    authentication: Authentication,
    @PathVariable
    postId: String
  ): ResponseEntity<CustomResponse<Unit>> {
    val username = authentication.name
    logger.info("delete post $username $postId")
    val r = postsService.deletePost(postId, username)
    return CustomResponseUtil.toResponseEntity(r)
  }

  @GetMapping
  fun getPosts(
    // authentication is optional
    authentication: Authentication?,
    pageable: Pageable
  ): ResponseEntity<CustomResponse<List<PostResponse>>> {
    val username = authentication?.name
    val r = postsService.getPosts(username, pageable)
    return CustomResponseUtil.toResponseEntity(r)
  }

  @PostMapping(value = ["/{postId}/comments"])
  fun createComment(
    authentication: Authentication,
    @PathVariable
    postId: String,
    @RequestBody
    commentRequest: CreateCommentRequest
  ): ResponseEntity<CustomResponse<Unit>> {
    val username = authentication.name
    logger.info("createComment $username")
    val r = postsService.createComment(username, postId, commentRequest)
    return CustomResponseUtil.toResponseEntity(r)
  }

  @GetMapping(value = ["/{postId}/comments"])
  fun getComments(
    @PathVariable
    postId: String, pageable: Pageable
  ): ResponseEntity<CustomResponse<List<Comment>>> {
    val r = postsService.getComments(postId, pageable)
    return CustomResponseUtil.toResponseEntity(r)
  }

  @PostMapping(value = ["/{postId}/like"])
  fun likePost(
    authentication: Authentication,
    @PathVariable
    postId: String,
    @RequestBody
    likeRequest: LikeRequest
  ): ResponseEntity<CustomResponse<Unit>> {
    val username = authentication.name
    logger.info("likePost $username")
    val r = postsService.likePost(username, postId, likeRequest)
    return CustomResponseUtil.toResponseEntity(r)
  }

  @GetMapping(value = ["/search"])
  fun searchPost(
    authentication: Authentication?,
    @RequestParam(value = "k")
    keyword: String,
    pageable: Pageable
  ): ResponseEntity<CustomResponse<List<PostResponse>>> {
    val username = authentication?.name
    logger.info("searchPost $keyword")
    val r = postsService.searchPost(username, keyword, pageable)
    return CustomResponseUtil.toResponseEntity(r)
  }
}