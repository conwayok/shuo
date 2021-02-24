package com.conway.shuo

import com.conway.shuo.util.ProjectUtils
import com.fasterxml.jackson.core.type.TypeReference
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID


/** Created by Conway */
class IndependentTest {

  @Test
  fun jwtSecretKey() {
    val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    val base64Key = Encoders.BASE64.encode(key.encoded)
    println(base64Key)
  }

  @Test
  fun uuid() {
    val uid = UUID.randomUUID().toString().replace("-", "")
    println("post-$uid".length)
  }

  @Test
  fun time() {
    val t = LocalDateTime.now(Clock.systemUTC())

    println(t)
  }

  @Test
  fun stringListConvert() {
    val l = listOf("a", "b")
    val s = ProjectUtils.OBJECT_MAPPER.writeValueAsString(l)
    val l2 = ProjectUtils.OBJECT_MAPPER.readValue(
      s,
      object : TypeReference<List<String>?>() {})
    println()
  }
}