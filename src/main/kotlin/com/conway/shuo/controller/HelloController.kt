package com.conway.shuo.controller

import com.conway.shuo.service.HelloService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/** Created by Conway
 *
 * controller for testing.
 *
 * */
@RestController
class HelloController constructor(
  @Autowired
  private val helloService: HelloService
) {
  @GetMapping(value = ["/hello"])
  fun hello(): ResponseEntity<Any?> {
    val vo = helloService.checkHelloWorld()
    return if (vo == null) ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    else ResponseEntity.status(HttpStatus.OK).body(vo)
  }

  /**
   * authenticated
   */
  @GetMapping(value = ["/hello2"])
  fun hello2(): ResponseEntity<Unit> {
    return ResponseEntity.ok(Unit)
  }
}