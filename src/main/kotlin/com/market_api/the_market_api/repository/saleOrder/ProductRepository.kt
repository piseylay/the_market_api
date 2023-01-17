package com.market_api.the_market_api.repository.saleOrder

import com.market_api.the_market_api.model.saleModel.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    //fun findIdAndStatusIsTrue(id: Long): Product
}