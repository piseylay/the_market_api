package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.responseFormat.exception.CustomNotAcceptableException
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.repository.MenuItemRepository
import com.market_api.the_market_api.service.MenuItemService
import com.market_api.the_market_api.utils.JwtTokenUtil
import com.market_api.the_market_api.model.MenuItem
import com.market_api.the_market_api.model.response.MenuWithSubMenuResponse
import com.market_api.the_market_api.model.response.SubMenuResponse
import com.market_api.the_market_api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.ArrayList
import javax.persistence.criteria.Predicate
import javax.servlet.http.HttpServletRequest

@Service
class MenuItemServiceImpl: MenuItemService {

    @Autowired
    lateinit var menuItemRepository: MenuItemRepository
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil

    override fun findAllList(q: String?, page: Int, size: Int): Page<MenuItem>? {
        return menuItemRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (q != null) {
                val title = cb.like(root.get("title"), "%$q%")
                val parentId = cb.equal(root.get<Long>("parentId"), q)

                predicates.add(cb.or(title, parentId))
            }
            predicates.add(cb.isTrue(root.get("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): MenuItem? {
        return menuItemRepository.findByIdAndStatusTrue(id)
                ?: throw CustomNotFoundException("Menu Item id $id doesn't exist")
    }

    override fun addNew(t: MenuItem): MenuItem? {
        checkMenuItemExceptions(t)

        //Set Auto MenuOrder
        if (t.menuOrder == null || t.menuOrder == 0){
            t.menuOrder = this.setAutoMenuOrder(t)
        }

        return menuItemRepository.save(t)
    }

    override fun updateObj(id: Long, t: MenuItem): MenuItem? {
        val getMenuItem = findById(id)
        if (!t.status) {
            throw CustomNotAcceptableException("Not allow to delete menu item")
        } else {
            checkMenuItemExceptions(t)
            getMenuItem?.iconBg = t.iconBg
            getMenuItem?.routing = t.routing
            getMenuItem?.icon = t.icon
            getMenuItem?.selected = t.selected
            getMenuItem?.closable = t.closable
            getMenuItem?.externalLink = t.externalLink
            getMenuItem?._active = t._active
            getMenuItem?._disable = t._disable
            getMenuItem?.menuOrder = t.menuOrder
            getMenuItem?.title = t.title
            getMenuItem?.iconColor = t.iconColor
            getMenuItem?._groupTitle = t._groupTitle
            getMenuItem?.color = t.color
            getMenuItem?.parentId = t.parentId
        }
        return menuItemRepository.save(getMenuItem!!)
    }

    override fun findAll(): List<MenuItem>? = menuItemRepository.findAllMenuItems()

    override fun findAllMenuItemList(): List<MenuWithSubMenuResponse> {
        val dataResponseList = ArrayList<MenuWithSubMenuResponse>()
        val parentMenuList = menuItemRepository.findMenuWhereParentIdIsNull()
        for (parent in parentMenuList!!) {
            val data = MenuWithSubMenuResponse()
            data.id = parent.id
            data.title = parent.title
            val childMenuList = menuItemRepository.findSubMenuItemByTheirParentId(parent.id!!)
            if (childMenuList != null) {
                val subList = ArrayList<SubMenuResponse>()
                for (child in childMenuList) {
                    val sub = SubMenuResponse()
                    sub.id = child.id
                    sub.title = child.title
                    subList.add(sub)
                }
                data.subs = subList
            }
            dataResponseList.add(data)
        }
        return dataResponseList
    }

    override fun findParentMenuByUserId(http: HttpServletRequest): List<MenuItem>? {
        val userId = jwtTokenUtil.getJwtFromRequest(http).split("|")[0].toLong()
        val user = userService.findById(userId)
        val menuItem = if (user?._admin!!) {
            menuItemRepository.findMenuWhereParentIdIsNull()
        } else {
            menuItemRepository.findParentMenuByUserId(userId)
        }
        menuItem?.forEach { mItem ->
            if (user._admin!!) {
                mItem.items = menuItemRepository.findSubMenuItemByTheirParentId(mItem.id!!)?.toMutableList()
            } else {
                mItem.items = menuItemRepository.findChildMenuByParentId(mItem.id!!, userId)?.toMutableList()
            }
            if (mItem.items!!.isNotEmpty()) {
                for (item in mItem.items!!.iterator()) {
                    mItem.id = null
                    item.id = item.menuOrder?.toLong()
                }
            } else {
                mItem.id = mItem.menuOrder?.toLong()
                mItem.items = null
            }
            mItem.items?.sortBy { it.menuOrder }
        }
        return menuItem
    }

    private fun checkMenuItemExceptions(menuItem: MenuItem) {
        if (menuItem.title == null) throw CustomNotAcceptableException("Invalid MenuItem")
    }

    private fun setAutoMenuOrder (t:MenuItem): Int? {

        return if (t.parentId != null && t.parentId != 0L){

            /**
             * @get last menu order child by given parent
             *
             * Case first child of this parent (query return null)
             *      - set default start menuOrder =1
             *
             * Else - menuOrder_child = last+1
             */
            var lastMenuOrder = 1
            menuItemRepository.findFirstByParentIdOrderByMenuOrderDesc(t.parentId!!)?.let {
                lastMenuOrder = it.menuOrder!!.plus(1)
            }
            lastMenuOrder


        }else{
            /**
             * @get last menu order of parent
             *
             * Case first Parent (query return null)
             *      - set default Start menuOder = 10
             *
             * Else - MenuOrder_parent = last + 10
             */

            var lastMenuOrderParent = 10
            menuItemRepository.findFirstByOrderByMenuOrderDesc()?.let {
                lastMenuOrderParent = it.menuOrder!!.plus(10)
            }
            lastMenuOrderParent
        }
    }
}
