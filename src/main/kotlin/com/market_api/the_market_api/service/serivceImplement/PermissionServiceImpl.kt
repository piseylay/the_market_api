package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.responseFormat.exception.CustomNotAcceptableException
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.repository.FeatureRepository
import com.market_api.the_market_api.model.Permission
import com.market_api.the_market_api.repository.PermissionRepository
import com.market_api.the_market_api.service.PermissionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.ArrayList
import javax.persistence.criteria.Predicate

@Service
class PermissionServiceImpl: PermissionService {

    @Autowired
    lateinit var permissionRepository: PermissionRepository
    @Autowired
    lateinit var featureRepository : FeatureRepository

    override fun findAllList(q: String?, page: Int, size: Int): Page<Permission>? {
        return permissionRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (q != null) {
                predicates.add(cb.like(cb.upper(root.get("functionName")), "%${q.toUpperCase()}%"))
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): Permission? {
        return permissionRepository.findByIdAndStatusTrue(id)
                ?: throw CustomNotFoundException("Permission id $id doesn't exist")
    }

    override fun addNew(t: Permission): Permission? {
        checkPermissionExceptions(t)
        return permissionRepository.save(t)
    }

    override fun updateObj(id: Long, t: Permission): Permission? {
        val permission = findById(id)
        checkPermissionExceptions(t)
        permission?.status = t.status
        permission?.functionName = t.functionName
        permission?.functionOrder = t.functionOrder
        return permissionRepository.save(permission!!)
    }

    override fun findAll(): List<Permission>? = permissionRepository.findAllPermissions()

    private fun checkPermissionExceptions(permission: Permission) {
        permission.feature ?: throw CustomNotAcceptableException("Invalid Commune")
        permission.functionName ?: throw CustomNotAcceptableException("Invalid Permission")
        permission.functionOrder ?: throw CustomNotAcceptableException("Invalid Permission")
    }
}