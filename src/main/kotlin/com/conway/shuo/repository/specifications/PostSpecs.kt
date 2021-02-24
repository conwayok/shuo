package com.conway.shuo.repository.specifications

import com.conway.shuo.entity.PostModel
import org.springframework.data.jpa.domain.Specification

/** Created by Conway */
object PostSpecs {

  fun titleContains(keyword: String): Specification<PostModel> {
    return Specification { root, _, criteriaBuilder ->
      criteriaBuilder.like(
        root.get("title"),
        "%$keyword%"
      )
    }
  }

  fun textContentContains(keyword: String): Specification<PostModel> {
    return Specification { root, _, criteriaBuilder ->
      criteriaBuilder.like(root.get("textContent"), "%$keyword%")
    }
  }

  fun usernameContains(keyword: String): Specification<PostModel> {
    return Specification { root, _, criteriaBuilder ->
      criteriaBuilder.like(root.get("username"), "%$keyword%")
    }
  }
}