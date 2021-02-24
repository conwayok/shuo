package com.conway.shuo.repository

import com.conway.shuo.entity.PostModel
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

/** Created by Conway */
interface PostRepository :
  JpaRepository<PostModel, String>,
  JpaSpecificationExecutor<PostModel> {
  fun countByPostId(postId: String): Long

  fun findAllByUsername(username: String, pageable: Pageable): List<PostModel>
}