package com.conway.shuo.service

import com.conway.shuo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/** Created by Conway */
@Service(value = "shuoUserDetailsServiceImpl")
class UserDetailsServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserDetailsService {

  @Transactional(readOnly = true)
  override fun loadUserByUsername(username: String): UserDetails {
    val userOptional = userRepository.findUserModelByUsername(username)
    if (userOptional.isPresent) {
      val userModel = userOptional.get()
      return User.withUsername(userModel.username).password(userModel.password).authorities(arrayListOf()).build()
    }
    throw UsernameNotFoundException("User not found")
  }
}