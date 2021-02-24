package com.conway.shuo.dto.json

import com.conway.shuo.dto.LikeType
import java.time.LocalDateTime

/** Created by Conway */
data class PostResponse(
  val postId: String,
  val author: String,
  val title: String,
  val textContent: String?,
  val images: List<String>?,
  val createTime: LocalDateTime,
  val views: Long,
  val commentCount: Long,
  val points: Long,

  // user specific data
  val likeType: LikeType?
)