package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.responseFormat.exception.CustomNotAcceptableException
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.repository.FeatureRepository
import com.market_api.the_market_api.service.FeatureService
import com.market_api.the_market_api.model.Feature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.Predicate

@Service
class FeatureServiceImpl : FeatureService {

    @Autowired
    lateinit var featureRepository: FeatureRepository

    override fun findAllList(q: String?, page: Int, size: Int): Page<Feature>? {
        return featureRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (q != null) {
                val featureName = cb.like(cb.upper(root.get("featureName")), "%${q.uppercase(Locale.getDefault())}%")
                predicates.add(featureName)
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): Feature? {
        return featureRepository.findByIdAndStatusTrue(id)
                ?: throw CustomNotFoundException("Feature id : $id doesn't exits")
    }

    override fun addNew(t: Feature): Feature? {
        checkMenuItemExceptions(t)
        return featureRepository.save(t)
    }

    override fun updateObj(id: Long, t: Feature): Feature? {
        val feature = findById(id)
        if(!t.status) feature?.status = false
        else {
            checkMenuItemExceptions(t)
            feature?.featureName = t.featureName
            feature?.featureOrder = t.featureOrder
        }
        return featureRepository.save(feature!!)
    }

    override fun findAll(): List<Feature>? {
        return featureRepository.findAllByStatusTrueOrderByIdDesc()
    }

    private fun checkMenuItemExceptions(feature: Feature) {
        if (feature.featureName == null) throw CustomNotAcceptableException("Invalid MenuItem")
        if (feature.featureOrder == null) throw CustomNotAcceptableException("Invalid MenuItem")
    }
}