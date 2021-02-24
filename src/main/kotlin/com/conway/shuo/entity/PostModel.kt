package com.conway.shuo.entity

import com.conway.shuo.entity.converter.StringListConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

/** Created by Conway */
@Entity
@Table(name = "vPosts")
class PostModel(
  @Id
  @Column(name = "post_id", columnDefinition = "CHAR(32)")
  var postId: String? = null,

  @Column(name = "username", columnDefinition = "VARCHAR(255)", nullable = false)
  var username: String,

  @Column(name = "title", columnDefinition = "VARCHAR(255)", nullable = false)
  var title: String,

  @Column(name = "text_content", columnDefinition = "TEXT")
  var textContent: String? = null,

  @Convert(converter = StringListConverter::class)
  @Column(name = "images", columnDefinition = "JSON")
  var images: List<String>? = null,

  @Column(name = "create_time", columnDefinition = "DATETIME")
  var createTime: LocalDateTime? = null,

  @Column(name = "update_time", columnDefinition = "DATETIME")
  var updateTime: LocalDateTime? = null,

  @Column(name = "views")
  var views: Long = 0,

  @Column(name = "comment_count")
  var commentCount: Long = 0,

  @Column(name = "points")
  var points: Long = 0
) {
  @PrePersist
  fun prePersist() {
    createTime = LocalDateTime.now(ZoneOffset.UTC);
  }
}