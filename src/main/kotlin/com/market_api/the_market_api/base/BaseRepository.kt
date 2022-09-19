package com.market_api.the_market_api.base

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import th.co.geniustree.springdata.jpa.repository.JpaSpecificationExecutorWithProjection


//@Repository
@NoRepositoryBean
interface BaseRepository <T: BaseEntity> : JpaRepository<T,Long>, JpaSpecificationExecutor<T>, JpaSpecificationExecutorWithProjection<T,Long> {
    fun findAllByIdInAndStatus(ids:List<Long>, status:Boolean) : MutableList<T>
    override fun <R : Any?> findAll(p0: Specification<T>?, p1: Class<R>?, p2: Pageable?): Page<R>
}
