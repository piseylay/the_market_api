package com.market_api.the_market_api.controller.sale

import com.market_api.the_market_api.model.saleModel.Category
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.CategoryService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/category")
class CategoryController {

    @Autowired
    lateinit var categoryService: CategoryService
    private val response = ResponseObjectMap()

    @PostMapping
     fun addCategory(@RequestBody category: Category): MutableMap<String, Any> {
        return response.responseObject(categoryService.addNew((category)))
    }

    @PutMapping("{id}")
    fun updateCategory(@PathVariable id: Long, @RequestBody category: Category): MutableMap<String, Any>{
        return response.responseObject(categoryService.updateObj(id, category))
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): MutableMap<String, Any>{
        return  response.responseObject(categoryService.findById(id))
    }

    @GetMapping("all")
    fun getAll(): MutableMap<String, Any>{

        val category = categoryService.findAll()
        val size = category?.size?.toLong()

        return response.responseObject(category, size!!)

    }

    @GetMapping(AppConstant.LIST_PATH)
    fun findAllFeatureCriteria(@RequestParam(required = false) query: String?, @RequestParam page: Int, @RequestParam size: Int): MutableMap<String, Any> {
        val categoryPage = categoryService.findAllList(query, page, size)
        val content = categoryPage?.content
        val totalElement = categoryPage?.totalElements
        return response.responseObject(content, totalElement!!)
    }
}
