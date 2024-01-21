package com.gufeczek.pokecompanion.core.paging

import org.springframework.data.domain.Page

data class CuratedPage<T>(
    val count: Long,
    val limit: Int,
    val offset: Long,
    val nextOffset: Long?,
    val results: List<T>
)

fun <T> Page<T>.toCuratedPage(nextOffset: Int?): CuratedPage<T> {
    return CuratedPage(
        count = this.totalElements,
        limit = this.pageable.pageSize,
        offset = this.pageable.offset,
        nextOffset = nextOffset?.toLong(),
        results = this.content
    )
}
