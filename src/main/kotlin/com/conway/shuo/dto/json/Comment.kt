package com.conway.shuo.dto.json

import java.time.LocalDateTime

/** Created by Conway */
data class Comment(val author: String, val createTime: LocalDateTime, val textContent:String)