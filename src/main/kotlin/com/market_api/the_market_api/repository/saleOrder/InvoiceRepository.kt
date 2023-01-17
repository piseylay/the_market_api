package com.market_api.the_market_api.repository.saleOrder

import com.market_api.the_market_api.model.saleModel.Invoice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository : JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
}