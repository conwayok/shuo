package com.conway.shuo.service

import com.conway.shuo.dto.json.CreateUserRequest
import com.conway.shuo.dto.json.CustomResponse
import com.conway.shuo.entity.UserModel
import com.conway.shuo.repository.UserRepository
import com.conway.shuo.util.CustomResponseUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

/** Created by Conway */
@Service
class UserService @Autowired constructor(
  private val userRepository: UserRepository,
  private val passwordEncoder: PasswordEncoder
) {

  fun createUser(createUserRequest: CreateUserRequest): CustomResponse<Unit> {

    val userName = createUserRequest.username

    // check if user exists
    val userExists = userRepository.findUserModelByUsername(userName).isPresent

    if (userExists) return CustomResponseUtil.dataExistsError()

    val userModel = UserModel()
    userModel.username = userName
    userModel.password = passwordEncoder.encode(createUserRequest.password)
    userModel.email = createUserRequest.email

    userRepository.save(userModel)

    return CustomResponseUtil.ok()
  }

}