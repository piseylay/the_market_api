package com.market_api.the_market_api.controller.system

import com.market_api.the_market_api.model.Permission
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.PermissionService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/permission")
class PermissionController {
    @Autowired
    lateinit var permissionService: PermissionService
    private val response = ResponseObjectMap()
    @PostMapping
    fun addNewPermission(@RequestBody permission: Permission) : MutableMap<String,Any>{
        return response.responseObject(permissionService.addNew(permission))
    }
    @GetMapping("{id}")
    fun findPermissionById(@PathVariable id : Long): MutableMap<String,Any>{
        return response.responseObject(permissionService.findById(id))
    }
    @GetMapping(AppConstant.ALL_PATH)
    fun findAllPermissions() : MutableMap<String,Any>{
        val permission = permissionService.findAll()
        val totalElements = permission?.size?.toLong()
        return response.responseObject(permission,totalElements!!)
    }
    @PutMapping("{id}")
    fun updatePermission(@PathVariable id : Long, @RequestBody permission: Permission) : MutableMap<String,Any>{
        return response.responseObject(permissionService.updateObj(id,permission))
    }
    @GetMapping(AppConstant.LIST_PATH)
    fun findAllPermissionCriteria(@RequestParam(required = false) query: String?, @RequestParam page: Int, @RequestParam size: Int) : MutableMap<String,Any>{
        val permissionPage = permissionService.findAllList(query,page,size)
        val content = permissionPage?.content
        val totalElement = permissionPage?.totalElements
        return response.responseObject(content,totalElement!!)
    }
}
