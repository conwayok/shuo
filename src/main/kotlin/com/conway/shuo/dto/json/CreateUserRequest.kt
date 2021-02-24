package com.conway.shuo.dto.json

import javax.validation.constraints.Pattern

/** Created by Conway */
data class CreateUserRequest(
  @Pattern(regexp = "^[A-Za-z0-9_]+$*")
  val username: String,
  val password: String,
  val email: String
)