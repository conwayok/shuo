package com.conway.shuo.entity

import com.conway.shuo.dto.LikeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/** Created by Conway */
@Entity
@Table(name = "vLikes")
class LikeModel(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  var id: Long? = null,

  @Column(name = "username", columnDefinition = "VARCHAR(255)")
  var username: String,

  @Column(name = "post_id", columnDefinition = "CHAR(32)")
  var postId: String,

  @Enumerated(EnumType.STRING)
  @Column(name = "like_type", columnDefinition = "VARCHAR(8)")
  var likeType: LikeType
)