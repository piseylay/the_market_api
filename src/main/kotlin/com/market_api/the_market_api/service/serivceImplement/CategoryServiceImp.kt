package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.model.saleModel.Category
import com.market_api.the_market_api.repository.saleOrder.CategoryRepository
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.Predicate

@Service
class CategoryServiceImp : CategoryService {

    @Autowired
    lateinit var categoryRepository: CategoryRepository;

    override fun findAllList(q: String?, page: Int, size: Int): Page<Category>? {
        return categoryRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (q != null) {
                val name = cb.like(cb.upper(root.get("name")), "%${q.uppercase(Locale.getDefault())}%")
                predicates.add(name)
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): Category? {
        return categoryRepository.findByIdAndStatusIsTrue(id)?: throw CustomNotFoundException("Category id : $id doesn't exits")
    }

    override fun addNew(t: Category): Category? {
        return categoryRepository.save(t)
    }

    override fun updateObj(id: Long, t: Category): Category? {
        var category = findById(id)
        if(!category!!.status) category.status = false
        else{
            category.id = t.id
            category.name = t.name
            category.description = t.description
        }
        return  categoryRepository.save(category)
    }

    override fun findAll(): List<Category>? {
        return categoryRepository.findAll();
    }

}