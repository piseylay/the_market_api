package com.market_api.the_market_api.repository.saleOrder

import com.market_api.the_market_api.model.saleModel.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository: JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
}