package com.gufeczek.pokecompanion.core

import org.springframework.data.domain.Page

data class CuratedPage<T>(
    val count: Long,
    val nextOffset: Long?,
    val results: List<T>
)

fun <T> Page<T>.toCuratedPage(nextOffset: Int?): CuratedPage<T> {
    return CuratedPage(
        count = this.totalElements,
        nextOffset = nextOffset?.toLong(),
        results = this.content
    )
}
