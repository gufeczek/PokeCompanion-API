package com.gufeczek.pokecompanion.core

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class OffsetPageRequest private constructor(
    private var limit: Int,
    private var offset: Int
) : Pageable {
    init {
        if (limit < 1) limit = DEFAULT_LIMIT
        if (offset < 0) offset = DEFAULT_OFFSET
    }

    override fun getPageNumber(): Int = offset / limit

    override fun getPageSize(): Int = limit

    override fun getOffset(): Long = offset.toLong()

    override fun getSort(): Sort = Sort.unsorted()

    override fun next(): Pageable {
        return OffsetPageRequest(
            limit = offset + pageSize,
            offset = pageSize
        )
    }

    private fun previous(): Pageable {
        return if (hasPrevious()) {
            OffsetPageRequest(
                limit = offset - pageSize,
                offset = pageSize
            )
        } else {
            first()
        }
    }

    override fun previousOrFirst(): Pageable {
        return if (hasPrevious()) {
            previous()
        } else {
            first()
        }
    }

    override fun first(): Pageable {
        return OffsetPageRequest(
            limit = pageSize,
            offset = 0
        )
    }

    override fun withPage(pageNumber: Int): Pageable {
        return OffsetPageRequest(
            limit = pageSize,
            offset = pageNumber * pageSize
        )
    }

    override fun hasPrevious(): Boolean = offset >= limit

    companion object {
        const val DEFAULT_LIMIT = 18
        const val DEFAULT_OFFSET = 0

        fun of(limit: Int, offset: Int): OffsetPageRequest {
            return OffsetPageRequest(limit, offset)
        }
    }
}