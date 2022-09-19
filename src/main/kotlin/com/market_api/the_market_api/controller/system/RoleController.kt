package com.market_api.the_market_api.controller.system


import com.market_api.the_market_api.model.Role
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.RoleService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/role")
class RoleController {
    @Autowired
    lateinit var roleService: RoleService
    private val response = ResponseObjectMap()

    @PostMapping
    fun addNewRole(@RequestBody role: Role) : MutableMap<String,Any> = response.responseObject(roleService.addNew(role))

    @GetMapping("{id}")
    fun findRoleById(@PathVariable id : Long) : MutableMap<String,Any> {
        println(response.responseObject(roleService.findById(id)))
        return response.responseObject(roleService.findById(id))
    }

    @GetMapping(AppConstant.ALL_PATH)
    fun findAllRoles() : MutableMap<String,Any> {
        val roleList = roleService.findAll()
        val totalElements = roleList?.size!!.toLong()
        return response.responseObject(roleList,totalElements)
    }
    @GetMapping(AppConstant.LIST_PATH)
    fun findRoleList(@RequestParam(required = false) query: String?, @RequestParam page: Int, @RequestParam size: Int): MutableMap<String,Any>{
        val rolePage = roleService.findAllList(query,page,size)
        val content = rolePage?.content
        val totalElements = rolePage?.totalElements
        return response.responseObject(content,totalElements!!)
    }

    @PutMapping("{id}")
    fun updateRoleById(@PathVariable id:Long, @RequestBody role: Role) : MutableMap<String,Any> {
        return response.responseObject(roleService.updateObj(id,role))
    }

    @GetMapping("/role-menu/{id}")
    fun findRoleMenuByRoleId(@PathVariable id: Long): List<Long> ?{
        return roleService.findRoleMenuByRoleId(id)
    }
    @GetMapping("/role-permission/{id}")
    fun findRolePermissionByRoleId(@PathVariable id: Long): List<Long> ?{
        return roleService.findRolePermissionByRoleId(id)
    }
    @PutMapping("/apply-menu/{roleId}")
    fun roleApplyMenus(@PathVariable roleId: Long, @RequestBody menusId: LongArray): Map<String, Any> {
        return response.responseObject(roleService.roleApplyMenus(roleId, menusId))
    }
    @PutMapping("/apply-permission/{roleId}")
    fun roleApplyPermissions(@PathVariable roleId: Long, @RequestBody permissionId: LongArray): Map<String, Any> {
        return response.responseObject(roleService.roleApplyPermissions(roleId, permissionId))
    }
}