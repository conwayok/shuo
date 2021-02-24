package com.conway.shuo.config.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/** Created by Conway */
@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {
  override fun commence(
    request: HttpServletRequest,
    response: HttpServletResponse,
    authException: AuthenticationException
  ) {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
  }
}