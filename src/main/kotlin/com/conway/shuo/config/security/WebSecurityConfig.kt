package com.conway.shuo.config.security

import com.conway.shuo.controller.v1.V1Path
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class WebSecurityConfig @Autowired constructor(
  @Qualifier("shuoUserDetailsServiceImpl")
  private val userDetailsService: UserDetailsService,
  private val authenticationEntryPoint: AuthenticationEntryPoint,
  private val jwtFilter: JwtFilter
) : WebSecurityConfigurerAdapter() {

  override fun configure(http: HttpSecurity) {
    http
      .csrf()
      .disable()
      .authorizeRequests()
      .mvcMatchers(
        HttpMethod.GET,
        "/hello",
        "/error",
        "${V1Path.PATH}/posts/**",
        "/index.html",
        "/js/forum.js"
      )
      .permitAll()
      .mvcMatchers(HttpMethod.POST, "${V1Path.PATH}/users", "${V1Path.PATH}/authenticate")
      .permitAll()
      // swagger
      .mvcMatchers(
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/v2/**",
        "/webjars/**",
        "/swagger-resources/**"
      )
      .permitAll()
      .mvcMatchers("/**")
      .authenticated()
      .and()
      .exceptionHandling()
      .authenticationEntryPoint(authenticationEntryPoint)
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
  }

  override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
  }

  @Bean
  override fun authenticationManagerBean(): AuthenticationManager {
    return super.authenticationManagerBean()
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }
}