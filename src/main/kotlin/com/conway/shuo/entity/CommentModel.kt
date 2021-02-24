package com.conway.shuo.entity

import java.time.Clock
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

/** Created by Conway */
@Entity
@Table(name = "vComments")
class CommentModel(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(name = "username", columnDefinition = "VARCHAR(255)")
  val username: String,

  @Column(name = "post_id", columnDefinition = "CHAR(37)")
  val postId: String,

  @Column(name = "text_content", columnDefinition = "TEXT")
  val textContent: String,

  @Column(name = "create_time", columnDefinition = "DATETIME")
  var createTime: LocalDateTime? = null
) {
  @PrePersist
  fun prePersist() {
    createTime = LocalDateTime.now(Clock.systemUTC())
  }
}