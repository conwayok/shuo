package com.conway.shuo.repository

import com.conway.shuo.entity.UserModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.validation.constraints.NotNull

/** Created by Conway */
@Repository
interface UserRepository : JpaRepository<UserModel, String> {

  fun findUserModelByUsername(userName: @NotNull String): Optional<UserModel>

//  @Query(value = "SELECT u.userId from UserModel u where u.username = :username")
//  fun findUserIdWithUsername(
//    @Param("username")
//    userName: @NotNull String
//  ): String?
}