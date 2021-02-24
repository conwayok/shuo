package com.conway.shuo.entity.converter

import com.conway.shuo.util.ProjectUtils
import com.fasterxml.jackson.core.type.TypeReference
import javax.persistence.AttributeConverter

/** Created by Conway */
class StringListConverter : AttributeConverter<List<String>?, String?> {
  override fun convertToDatabaseColumn(attribute: List<String>?): String? {
    return if (attribute != null) {
      ProjectUtils.OBJECT_MAPPER.writeValueAsString(attribute)
    } else null
  }

  override fun convertToEntityAttribute(dbData: String?): List<String>? {
    return if (dbData != null) ProjectUtils.OBJECT_MAPPER.readValue(
      dbData,
      object : TypeReference<List<String>?>() {})
    else null
  }
}