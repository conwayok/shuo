package com.conway.shuo.service

import com.conway.shuo.repository.HelloRepository
import com.conway.shuo.dto.HelloVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/** Created by Conway */
@Service
class HelloService @Autowired constructor(
  private val helloRepository: HelloRepository
) {

  fun checkHelloWorld(): HelloVo? {
    val optional = helloRepository.findById(1)
    if (optional.isPresent) {
      val model = optional.get()
      return HelloVo(model.col1, model.col2)
    }
    return null
  }
}