package com.conway.shuo.dto.json

/** Created by Conway */
data class CustomResponse<T>(val code: Int, val message: String, var payload: T? = null)