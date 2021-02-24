package com.conway.shuo.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

/** Created by Conway */
@Entity
@Table(name = "vHello")
class HelloModel(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @Column(columnDefinition = "varchar(32)", nullable = false)
  var col1: String,

  @Column(columnDefinition = "varchar(32)", nullable = false)
  var col2: String
)