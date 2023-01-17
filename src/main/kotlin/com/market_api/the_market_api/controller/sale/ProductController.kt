package com.market_api.the_market_api.controller.sale

import com.market_api.the_market_api.model.saleModel.Product
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.ProductService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_PATH + "product")
class ProductController {
    @Autowired
    lateinit var productService: ProductService
    private val response = ResponseObjectMap()

    @PostMapping
    fun addUom(@RequestBody uom: Product) : MutableMap<String, Any>{
        return response.responseObject(productService.addNew(uom))
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long) : MutableMap<String, Any>{
        return response.responseObject(productService.findById(id))
    }

    @GetMapping(AppConstant.ALL_PATH)
    fun getAll() : MutableMap<String, Any>{
        return response.responseObject(productService.findAll())
    }

    @GetMapping(AppConstant.LIST_PATH)
    fun getAllList(@RequestParam(required = false) q: String?, @RequestParam page:Int, @RequestParam size:Int): MutableMap<String, Any> {
        val uomAll = productService.findAllList(q, page, size)
        val content = uomAll!!.content
        val totalElement = content.size.toLong()

        return response.responseObject(content, totalElement)
    }
}