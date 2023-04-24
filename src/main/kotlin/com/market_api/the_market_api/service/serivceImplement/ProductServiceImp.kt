package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.model.saleModel.Product
import com.market_api.the_market_api.model.saleModel.ProductVariantUom
import com.market_api.the_market_api.repository.saleOrder.ProductRepository
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.service.ProductService
import com.market_api.the_market_api.service.ProductVariantUomService
import com.market_api.the_market_api.service.UomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.Predicate

@Service
class ProductServiceImp : ProductService{

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var uomService: UomService

    @Autowired
    lateinit var productVariantUomService: ProductVariantUomService

    override fun findAllList(q: String?, page: Int, size: Int): Page<Product>? {
        return productRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (q != null) {
                val name = cb.like(cb.upper(root.get("name")), "%${q.uppercase(Locale.getDefault())}%")
                predicates.add(name)
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): Product? {
        return productRepository.findById(id).get() ?: throw CustomNotFoundException("product id ${id} not found")
    }

    override fun addNew(t: Product): Product? {

        t.productVariantUom!!.forEach {
            it.product = t
        }

        productRepository.save(t)

        return  t
    }

    override fun updateObj(id: Long, t: Product): Product? {
        val product = findById(id)
        if(!product?.status!!) product.status = false
        else
        {
            product.name = t.name
            product._active = t._active
            product.category = t.category
            product.code = t.code
            product.barcode = t.barcode
            product.weight = t.weight
            product.description = t.description
            product.imagePath = t.imagePath
            product.thumbnail = t.thumbnail
            product.productVariantUom = t.productVariantUom
        }
        return productRepository.save(product)
    }

    override fun findAll(): List<Product>? {
        return  productRepository.findAll()
    }
}