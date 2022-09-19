package com.market_api.the_market_api.service

import com.market_api.the_market_api.base.BaseService
import com.market_api.the_market_api.model.MenuItem
import com.market_api.the_market_api.model.response.MenuWithSubMenuResponse
import javax.servlet.http.HttpServletRequest

interface MenuItemService : BaseService<MenuItem> {
    fun findAllMenuItemList(): List<MenuWithSubMenuResponse>
    fun findParentMenuByUserId(http: HttpServletRequest): List<MenuItem>?
}