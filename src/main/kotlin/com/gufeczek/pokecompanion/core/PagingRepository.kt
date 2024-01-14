package com.gufeczek.pokecompanion.core

import java.io.Serializable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface PagingRepository<T, ID : Serializable> : Repository<T, ID> {
    fun findAll(pageable: Pageable): Page<T>
}
