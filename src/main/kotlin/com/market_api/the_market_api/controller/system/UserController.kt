package com.market_api.the_market_api.controller.system

import com.market_api.the_market_api.model.User
import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.RoleService
import com.market_api.the_market_api.service.UserService
import com.market_api.the_market_api.utils.AppConstant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(AppConstant.MAIN_PATH+"/user")
class UserController {

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var roleService: RoleService
    var responseObject = ResponseObjectMap()

    @PostMapping
    fun addUser(@RequestBody user : User): Map<String, Any> {
        return responseObject.responseObject(userService.addNew(user))
    }

    @GetMapping(AppConstant.ALL_PATH)
    fun findAllUsers(): List<User>? = userService.findAll()

    @GetMapping(AppConstant.LIST_PATH)
    fun findUserList(@RequestParam(value = "query", required = false) query: String?,
                     @RequestParam(value = "page", defaultValue = "0") page: Int,
                     @RequestParam(value = "size", defaultValue = "10") size: Int): Map<String, Any> {
        val userPage = userService.findAllList(query, page, size)
        val users = userPage!!.content
        val totalElement = userPage.totalElements
        return responseObject.responseObject(users, totalElement)
    }

    @GetMapping("/username/{username}")
    fun findUserByUsername(@PathVariable username: String): Map<String, Any> {
        return responseObject.responseObject(userService.findByUsername(username))
    }

    @GetMapping("{id}")
    fun findUserById(@PathVariable(value = "id") id: Long): Map<String, Any> {
        return responseObject.responseObject(userService.findById(id))
    }

    @PutMapping("{id}")
    fun updateUser(@PathVariable(value = "id") id: Long, @RequestBody user: User): Map<String, Any> {
        return responseObject.responseObject(userService.updateObj(id, user))
    }

    @PostMapping("/{id}/upload-image")
    fun uploadImage(@PathVariable(value = "id") id: Long?, imagePath: MultipartFile?): Map<String, Any> {
        return responseObject.responseObject(userService.uploadImage(id, imagePath))
    }

    @PutMapping("/apply-role/{userId}")
    fun userApplyRole(@PathVariable userId: Long, @RequestBody rolesId: LongArray): Map<String, Any> {
        return responseObject.responseObject(userService.userApplyRoles(userId, rolesId))
    }

    @GetMapping("/{userId}/role")
    fun findRoleByUserId(@PathVariable userId : Long) : Map<String,Any> {
        return responseObject.responseObject(roleService.findByUserId(userId))
    }

    @GetMapping("/info")
    fun getInfo(http: HttpServletRequest): Map<String, Any> {
        return responseObject.responseObject(userService.getUserInfo(http))
    }
}