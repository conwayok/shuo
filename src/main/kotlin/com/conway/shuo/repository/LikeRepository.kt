package com.conway.shuo.repository

import com.conway.shuo.entity.LikeModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

/** Created by Conway */
@Repository
interface LikeRepository : JpaRepository<LikeModel, Long> {

  @Query(value = "select l from LikeModel l where l.username = :username and l.postId = :postId")
  fun getLikeStatus(
    @Param("username")
    username: String,
    @Param("postId")
    postId: String
  ): Optional<LikeModel>

  @Query(value = "select l from LikeModel  l where l.username = :username and l.postId in :postIds")
  fun getLikesFromPostIds(
    @Param("username")
    username: String,
    @Param("postIds")
    postIds: List<String>
  ): List<LikeModel>
}