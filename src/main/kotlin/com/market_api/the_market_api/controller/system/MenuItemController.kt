package com.market_api.the_market_api.controller.system

import com.market_api.the_market_api.model.MenuItem
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.MenuItemService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/menu-item")
class MenuItemController {
    @Autowired
    lateinit var menuItemService: MenuItemService
    private val response = ResponseObjectMap()

    @GetMapping("/all-menu-list")
    internal fun findAllMenuItemList(): Map<String, Any> {
        return response.responseObject(menuItemService.findAllMenuItemList())
    }

    @GetMapping(AppConstant.LIST_PATH)
    internal fun findMenuItemCriteria(@RequestParam(required = false) query: String?, @RequestParam page: Int, @RequestParam size: Int): Map<String, Any> {
        val menuItemPage = menuItemService.findAllList(query, page, size)
        val menuItemList = menuItemPage?.content
        val totalElements = menuItemPage?.totalElements
        return response.responseObject(menuItemList, totalElements!!)
    }

    @GetMapping(AppConstant.ALL_PATH)
    internal fun findMenuItemAll(): Map<String, Any> {
        val menuItemList = menuItemService.findAll()
        val totalElements = menuItemList?.size?.toLong()
        return response.responseObject(menuItemList, totalElements!!)
    }

    @GetMapping("{id}")
    internal fun findMenuItemById(@PathVariable id: Long): Map<String, Any> {
        return response.responseObject(menuItemService.findById(id))
    }

    @PostMapping
    internal fun addNewMenuItem(@RequestBody menuItem: MenuItem): Map<String, Any> {
        return response.responseObject(menuItemService.addNew(menuItem))
    }

    @PutMapping("{id}")
    internal fun updateMenuItem(@PathVariable id: Long, @RequestBody menuItem: MenuItem): Map<String, Any> {
        return response.responseObject(menuItemService.updateObj(id, menuItem))
    }

    @GetMapping("/user")
    fun getMenuByUserId(http: HttpServletRequest): Map<String, Any> {
        //val auth = SecurityContextHolder.getContext().authentication.principal as UserPrincipal

        return response.responseObject(menuItemService.findParentMenuByUserId(http))
    }
}
