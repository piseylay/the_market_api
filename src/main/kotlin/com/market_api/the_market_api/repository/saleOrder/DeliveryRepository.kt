package com.market_api.the_market_api.repository.saleOrder

import com.market_api.the_market_api.model.saleModel.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository : JpaRepository<Delivery, Long>, JpaSpecificationExecutor<Delivery> {
}