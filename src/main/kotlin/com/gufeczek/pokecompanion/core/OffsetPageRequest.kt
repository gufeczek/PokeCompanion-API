package com.gufeczek.pokecompanion.core

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class OffsetPageRequest private constructor(
    private var offset: Int,
    private var limit: Int,
    private val sort: Sort
) : Pageable {
    init {
        if (offset < 0) offset = DEFAULT_OFFSET
        if (limit < 1) limit = DEFAULT_LIMIT
    }

    override fun getPageNumber(): Int = offset / limit

    override fun getPageSize(): Int = limit

    override fun getOffset(): Long = offset.toLong()

    override fun getSort(): Sort = sort

    override fun next(): Pageable {
        return OffsetPageRequest(
            offset = offset + limit,
            limit = limit,
            sort = sort
        )
    }

    private fun previous(): Pageable {
        return if (hasPrevious()) {
            OffsetPageRequest(
                offset = offset - limit,
                limit = limit,
                sort = sort
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
            offset = 0,
            limit = limit,
            sort = sort
        )
    }

    override fun withPage(pageNumber: Int): Pageable {
        return OffsetPageRequest(
            offset = pageNumber * limit,
            limit = limit,
            sort = sort
        )
    }

    override fun hasPrevious(): Boolean = offset >= limit

    companion object {
        const val DEFAULT_OFFSET = 0
        const val DEFAULT_LIMIT = 18

        fun of(
            offset: Int = DEFAULT_OFFSET,
            limit: Int = DEFAULT_LIMIT,
            sort: Sort
        ) : OffsetPageRequest {
            return OffsetPageRequest(
                offset = offset,
                limit = limit,
                sort = sort
            )
        }
    }
}