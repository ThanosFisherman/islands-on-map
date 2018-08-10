package io.github.thanosfisherman.islands.utils

import org.springframework.data.repository.CrudRepository

fun <T> CrudRepository<T, String>.findOneById(id: String): T? {
    val o = findById(id)
    return if (o.isPresent) o.get() else null
}