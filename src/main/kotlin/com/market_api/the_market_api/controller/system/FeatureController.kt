package com.market_api.the_market_api.controller.system

import com.market_api.the_market_api.model.Feature
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.FeatureService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/feature")
class FeatureController {
    @Autowired
    lateinit var featureService: FeatureService
    private val response = ResponseObjectMap()
    @PostMapping("")
    fun addNewFeature(@RequestBody feature: Feature): MutableMap<String, Any> {
        return response.responseObject(featureService.addNew(feature))
    }

    @GetMapping("{id}")
    fun findFeatureById(@PathVariable id : Long): MutableMap<String,Any>{
        return response.responseObject(featureService.findById(id))
    }

    @GetMapping(AppConstant.ALL_PATH)
    fun findAllFeatures(): MutableMap<String, Any> {
        val features = featureService.findAll()
        val size = features?.size?.toLong()
        return response.responseObject(features, size!!)
    }

    @PutMapping("{id}")
    fun updateFeature(@PathVariable id : Long, @RequestBody feature: Feature) : MutableMap<String,Any>{
        return response.responseObject(featureService.updateObj(id,feature))
    }

    @GetMapping(AppConstant.LIST_PATH)
    fun findAllFeatureCriteria(@RequestParam(required = false) query: String?, @RequestParam page: Int, @RequestParam size: Int): MutableMap<String, Any> {
        val featurePage = featureService.findAllList(query, page, size)
        val content = featurePage?.content
        val totalElement = featurePage?.totalElements
        return response.responseObject(content, totalElement!!)
    }
}