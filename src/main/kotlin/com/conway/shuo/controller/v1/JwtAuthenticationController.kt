package com.conway.shuo.controller.v1

import com.conway.shuo.dto.json.JwtRequest
import com.conway.shuo.dto.json.JwtResponse
import com.conway.shuo.util.JwtUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/** Created by Conway */
@RestController
class JwtAuthenticationController @Autowired constructor(
  private val authenticationManager: AuthenticationManager,
  private val jwtUtil: JwtUtil
) {

  private val logger = LoggerFactory.getLogger(JwtAuthenticationController::class.java)

  @PostMapping(value = ["/${V1Path.PATH}/authenticate"])
  fun createAuthenticationToken(
    @RequestBody
    jwtRequest: JwtRequest
  ): JwtResponse {
    val username = jwtRequest.username
    logger.info("authenticate $username")
    authenticationManager.authenticate(
      UsernamePasswordAuthenticationToken(
        username,
        jwtRequest.password
      )
    )
    val token = jwtUtil.generateJwt(username)
    return JwtResponse(token)
  }
}