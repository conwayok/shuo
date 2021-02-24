package com.conway.shuo.config.security

import com.conway.shuo.util.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/** Created by Conway */
@Component
class JwtFilter @Autowired constructor(
  @Qualifier("shuoUserDetailsServiceImpl")
  private val userDetailsService: UserDetailsService,
  private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    try {
      val authorizationHeader = request.getHeader("Authorization")

      if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
        val jwtToken = authorizationHeader.replace(TOKEN_PREFIX, "")
        val username = jwtUtil.getUsernameFromJwt(jwtToken)
        val userDetails = userDetailsService.loadUserByUsername(username)
        val usernamePasswordAuthenticationToken =
          UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
      }
    } catch (e: Exception) {
      logger.error(e)
    }
    filterChain.doFilter(request, response)
  }

  companion object {
    private const val TOKEN_PREFIX = "Bearer "
  }
}