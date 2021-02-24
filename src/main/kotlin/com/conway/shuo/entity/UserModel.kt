package com.conway.shuo.entity

import java.time.Clock
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.PrePersist
import javax.persistence.Table

/** Created by Conway */
@Entity
@Table(name = "vUsers")
class UserModel(
  @Id
  @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255)")
  var username: String? = null,

  @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
  var password: String? = null,

  @Column(name = "email", columnDefinition = "VARCHAR(255)", nullable = false)
  var email: String? = null,

  @Column(name = "create_time", columnDefinition = "DATETIME", nullable = false)
  var createTime: LocalDateTime? = null
) {
  @PrePersist
  fun prePersist() {
    createTime = LocalDateTime.now(Clock.systemUTC())
  }
}