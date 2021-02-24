package com.conway.shuo.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/** Created by Conway */
@Component
class JwtUtil {

  @Value(value = "\${shuo.jwt.secret}")
  private lateinit var jwtSecret: String

  fun generateJwt(username: String): String {
    val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret))
    return Jwts.builder().setSubject(username).signWith(key).compact()
  }

  fun getUsernameFromJwt(token: String): String {
    val parser = Jwts.parserBuilder().setSigningKey(jwtSecret).build()
    val claims = parser.parseClaimsJws(token)
    return claims.body.subject
  }
}