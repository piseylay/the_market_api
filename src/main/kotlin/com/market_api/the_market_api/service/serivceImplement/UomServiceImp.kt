package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.model.saleModel.Uom
import com.market_api.the_market_api.repository.saleOrder.UomRepository
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import com.market_api.the_market_api.service.UomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.Predicate

@Service
class UomServiceImp : UomService {

    @Autowired
    lateinit var uomRepository: UomRepository

    override fun getUomDefault(): Uom {
        return uomRepository.findUomByIsDefaultIsTrue()
    }

    override fun findAllList(q: String?, page: Int, size: Int): Page<Uom>? {
        return uomRepository.findAll({ root, _, cb ->
            val predicates = ArrayList<Predicate>()
            if (q != null) {
                val name = cb.like(cb.upper(root.get("name")), "%${q.uppercase(Locale.getDefault())}%")
                predicates.add(name)
            }
            predicates.add(cb.isTrue(root.get<Boolean>("status")))
            cb.and(*predicates.toTypedArray())
        }, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")))
    }

    override fun findById(id: Long): Uom? {
        return uomRepository.findByIdAndStatusIsTrue(id) ?: throw CustomNotFoundException("Category id : $id doesn't exits")
    }

    override fun addNew(t: Uom): Uom? {


        if(t.isDefault) {
            var listuom = uomRepository.findAll()
            updateUomDefaultFalse(listuom)
        }
        return uomRepository.save(t);
    }

    override fun updateObj(id: Long, t: Uom): Uom? {
        var uom = findById(id)
        if(!uom!!.status) uom.status = false
        else
        {
            if(t.isDefault) {

                var listUom = uomRepository.findAll()

                listUom.remove(uom)

                updateUomDefaultFalse(listUom)
            }

            uom.name = t.name
            uom.abbr = t.abbr
            uom.description = t.description
            uom.isDefault = t.isDefault
        }
        return uomRepository.save(uom)
    }

    override fun findAll(): List<Uom>? {
        return  uomRepository.findAll()
    }

    fun updateUomDefaultFalse(listUom: MutableList<Uom>){
        listUom.forEach {
            it.isDefault = false
        }
        uomRepository.saveAll(listUom)
    }
}