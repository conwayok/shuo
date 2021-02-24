package com.conway.shuo.repository

import com.conway.shuo.entity.CommentModel
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/** Created by Conway */
@Repository
interface CommentRepository : JpaRepository<CommentModel, Long> {
  fun findAllByPostId(postId: String, pageable: Pageable): List<CommentModel>
  fun findAllByPostId(postId: String): List<CommentModel>
}