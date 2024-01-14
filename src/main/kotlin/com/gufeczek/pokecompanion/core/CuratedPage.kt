package com.gufeczek.pokecompanion.core

import org.springframework.data.domain.Page

data class CuratedPage<T>(
    val count: Long,
    val nextOffset: Long?,
    val results: List<T>
)

fun <T> Page<T>.toCuratedPage(): CuratedPage<T> {
    val nextOffset = if (!this.isLast) {
        this.pageable.offset + this.size
    } else {
        null
    }

    return CuratedPage(
        count = this.totalElements,
        nextOffset = nextOffset,
        results = this.content
    )
}
