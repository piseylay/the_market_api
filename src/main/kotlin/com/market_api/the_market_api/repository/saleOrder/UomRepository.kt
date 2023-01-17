package com.market_api.the_market_api.repository.saleOrder

import com.market_api.the_market_api.model.saleModel.Uom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UomRepository: JpaRepository<Uom, Long>, JpaSpecificationExecutor<Uom> {
    fun findByIdAndStatusIsTrue(id: Long) : Uom
    fun findUomByIsDefaultIsTrue(): Uom
}