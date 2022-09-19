package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.MenuItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MenuItemRepository : JpaRepository<MenuItem, Long>, JpaSpecificationExecutor<MenuItem> {
    fun findByIdAndStatusTrue(id: Long): MenuItem?

    @Query(value = "SELECT m FROM MenuItem m WHERE m.status = true ORDER BY m.id DESC")
    fun findAllMenuItems(): List<MenuItem>?

    @Query(value = "SELECT m FROM MenuItem m WHERE m.parentId = 0 AND m.status = true ORDER BY m.menuOrder")
    fun findMenuWhereParentIdIsNull(): List<MenuItem>?

    /**
     * Find only the menu_item that not a root menu
     */
    fun findAllByParentIdIsNot(parentId:Long): List<MenuItem>?

    @Query(value = "SELECT m FROM MenuItem m WHERE m.parentId = ?1 AND m.status = true")
    fun findSubMenuItemByTheirParentId(id: Long): List<MenuItem>?

    @Query(value = "SELECT DISTINCT ON(menu_item.menu_order,menu_item.title) menu_item.* FROM menu_item " +
            "INNER JOIN role_menu ON role_menu.menu_item_id = menu_item.id " +
            "INNER JOIN user_role ON role_menu.role_id = user_role.role_id " +
            "INNER JOIN users on users.id = user_role.user_id " +
            "INNER JOIN role on role.id = user_role.role_id " +
            "WHERE users.id = ?1 and user_role.status = true and role_menu.status = true and menu_item.parent_id = 0 AND menu_item.status = true ORDER BY menu_order", nativeQuery = true)
    fun findParentMenuByUserId(userId:Long):List<MenuItem>?

    @Query(value = "SELECT DISTINCT ON(menu_item.menu_order,menu_item.title) menu_item.* FROM menu_item " +
            "INNER JOIN role_menu ON role_menu.menu_item_id = menu_item.id " +
            "INNER JOIN user_role ON role_menu.role_id = user_role.role_id " +
            "INNER JOIN users on users.id = user_role.user_id " +
            "INNER JOIN role on role.id = user_role.role_id " +
            "WHERE users.id = ?2 and role_menu.status = true AND menu_item.parent_id =?1 AND menu_item.status = true ORDER BY menu_order", nativeQuery = true)
    fun findChildMenuByParentId(id:Long, userId:Long):List<MenuItem>?


    /**
     * find the last menu order
     */
    fun findFirstByOrderByMenuOrderDesc () : MenuItem?
    fun findFirstByParentIdOrderByMenuOrderDesc(parentId: Long): MenuItem?
}
