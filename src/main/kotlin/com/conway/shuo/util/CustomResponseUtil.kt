package com.conway.shuo.util

import com.conway.shuo.dto.json.CustomResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


/** Created by Conway */
object CustomResponseUtil {
  fun <T> ok(payload: T? = null): CustomResponse<T> {
    return CustomResponse(1, "ok", payload)
  }

  fun <T> failed(payload: T? = null): CustomResponse<T> {
    return CustomResponse(-1, "未知的錯誤", payload)
  }

  fun <T> notFoundError(payload: T? = null): CustomResponse<T> {
    return CustomResponse(-2, "不存在的資料", payload)
  }

  fun <T> dataExistsError(payload: T? = null): CustomResponse<T> {
    return CustomResponse(-3, "資料已存在", payload)
  }

  fun <T> forbiddenError(payload: T? = null): CustomResponse<T> {
    return CustomResponse(-4, "Forbidden", payload)
  }

  fun <T> toResponseEntity(customResponse: CustomResponse<T>): ResponseEntity<CustomResponse<T>> {
    val returnHttpStatus =
      when (customResponse.code) {
        1 -> {
          // ok
          HttpStatus.OK
        }
        -1 -> {
          // general error
          HttpStatus.INTERNAL_SERVER_ERROR
        }
        -2 -> {
          HttpStatus.NOT_FOUND
        }
        -3 -> {
          HttpStatus.CONFLICT
        }
        -4 -> {
          HttpStatus.FORBIDDEN
        }
        else -> HttpStatus.INTERNAL_SERVER_ERROR
      }
    return ResponseEntity.status(returnHttpStatus).body(customResponse)
  }
}