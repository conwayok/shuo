package com.conway.shuo.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.util.UUID

/** Created by Conway */
object ProjectUtils {

  val OBJECT_MAPPER = jacksonObjectMapper()

  fun generateUUID(includeHyphens: Boolean = false): String {
    val uuid = UUID.randomUUID().toString()
    return if (!includeHyphens) uuid.replace("-", "")
    else uuid
  }
}