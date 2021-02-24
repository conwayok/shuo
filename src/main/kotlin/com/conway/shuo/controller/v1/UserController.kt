package com.conway.shuo.controller.v1

import com.conway.shuo.dto.json.CreateUserRequest
import com.conway.shuo.dto.json.CustomResponse
import com.conway.shuo.service.UserService
import com.conway.shuo.util.CustomResponseUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/** Created by Conway */
@RestController
@RequestMapping(value = ["/${V1Path.PATH}/users"])
class UserController @Autowired constructor(private val userService: UserService) {

  private val logger = LoggerFactory.getLogger(UserController::class.java)

  @PostMapping
  fun createUser(
    @RequestBody
    createUserRequest: CreateUserRequest
  ): ResponseEntity<CustomResponse<Unit>> {
    logger.info("createUser ${createUserRequest.username}")
    val response = userService.createUser(createUserRequest)
    return CustomResponseUtil.toResponseEntity(response)
  }


}