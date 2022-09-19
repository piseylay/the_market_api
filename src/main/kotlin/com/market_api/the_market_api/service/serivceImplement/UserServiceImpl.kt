package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.repository.RoleRepository
import com.market_api.the_market_api.repository.UserRepository
import com.market_api.the_market_api.repository.UserRoleRepository
import com.market_api.the_market_api.utils.JwtTokenUtil
import com.market_api.the_market_api.model.Role
import com.market_api.the_market_api.model.User
import com.market_api.the_market_api.model.UserRole
import com.market_api.the_market_api.service.StorageService
import com.market_api.the_market_api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*
import javax.persistence.criteria.Predicate
import javax.servlet.http.HttpServletRequest

@Service
class UserServiceImpl(private val bCryptEncoder: BCryptPasswordEncoder) : UserService {

    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var roleRepository: RoleRepository
    @Autowired
    lateinit var userRoleRepository: UserRoleRepository
    @Autowired
    lateinit var storageService: StorageService
    @Autowired
    lateinit var jwtTokenUtil: JwtTokenUtil


    private val filePath = "./resource/images/users/"

    override fun findAllList(q: String?, page: Int, size: Int): Page<User>? {
        return userRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (!q.isNullOrEmpty()) {
                predicates.add(cb.like(cb.upper(root.get("username")), "%${q.uppercase(Locale.getDefault())}%"))
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): User? = userRepository.findByIdAndStatusTrue(id)
            ?: throw CustomNotFoundException("user id $id does not exist")

    override fun addNew(t: User): User? {
        if (userRepository.existsByUsername(t.username)) {
            throw CustomNotFoundException("user ${t.username} already exist")
        }
        t.imagePath = t.imagePath
        t.password = bCryptEncoder.encode(t.password)

        return userRepository.save(t)
    }

    override fun updateObj(id: Long, t: User): User? {
        val user = findById(id)
        if (!t.password.isNullOrEmpty()) {
            user?.password = bCryptEncoder.encode(t.password)
        }
        user?.email = t.email
        user?._admin = t._admin
        user?.username = t.username
        user?.phone = t.phone
        user?.imagePath = t.imagePath
        return userRepository.save(user!!)
    }

    override fun findAll(): List<User>? = userRepository.findAllByStatusTrueOrderByIdDesc()

    override fun uploadImage(id: Long?, file: MultipartFile?): User? {
        val user = userRepository.findByIdAndStatus(id, true)
            ?: throw CustomNotFoundException("user not exist by id.")
        file?.let {
            if (user.imagePath != null) {
                val fileName: String = user.imagePath!!.substring(user.imagePath!!.lastIndexOf("/") + 1)
                storageService.removeFileImage(fileName,filePath)
            }
            user.imagePath = storageService.storeFileImage(filePath, file)?.path
            return userRepository.save(user)
        }
        throw throw CustomNotFoundException("Zero result")
    }

    override fun findByUsername(username: String): User? {
        return userRepository.findByUsernameAndStatusTrue(username)
    }

    override fun userApplyRoles(userId: Long, roleList: LongArray): List<UserRole>? {
        if (!userRepository.existsById(userId)) throw CustomNotFoundException("user id $userId does not exist")

        userRoleRepository.deleteUserRoleByUserId(userId)

        val userRoleList = ArrayList<UserRole>()

        for (role in roleList) {
            if (!roleRepository.existsById(role)) continue
            val userRole = UserRole()
            userRole.user = User(userId)
            userRole.role = Role(role)
            userRole.status = true
            userRoleList.add(userRole)
        }
        return userRoleRepository.saveAll(userRoleList)
    }

    override fun getUserInfo(http: HttpServletRequest) : User?{
        return findById(jwtTokenUtil.getJwtFromRequest(http).split("|")[0].toLong())
    }


}