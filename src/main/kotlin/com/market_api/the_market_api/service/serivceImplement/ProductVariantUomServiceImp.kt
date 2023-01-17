package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.model.saleModel.ProductVariantUom
import com.market_api.the_market_api.repository.saleOrder.ProductVariantUomRepository
import com.market_api.the_market_api.service.ProductVariantUomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

@Service
class ProductVariantUomServiceImp : ProductVariantUomService {

    @Autowired
    lateinit var productVariantUomRepository: ProductVariantUomRepository

    override fun findAllList(q: String?, page: Int, size: Int): Page<ProductVariantUom>? {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): ProductVariantUom? {
       return productVariantUomRepository.findById(id).get()
    }

    override fun addNew(t: ProductVariantUom): ProductVariantUom? {
        return productVariantUomRepository.save(t)
    }

    override fun updateObj(id: Long, t: ProductVariantUom): ProductVariantUom? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<ProductVariantUom>? {
        return productVariantUomRepository.findAll()
    }
}