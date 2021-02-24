package com.conway.shuo.repository

import com.conway.shuo.entity.HelloModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/** Created by Conway */
@Repository
interface HelloRepository : JpaRepository<HelloModel, Long>