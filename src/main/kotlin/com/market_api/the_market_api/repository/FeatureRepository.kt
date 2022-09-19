package com.market_api.the_market_api.repository

import com.market_api.the_market_api.model.Feature
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface FeatureRepository : JpaRepository<Feature,Long>, JpaSpecificationExecutor<Feature>{
    fun findByIdAndStatusTrue(id : Long): Feature?
    fun findAllByStatusTrueOrderByIdDesc(): List<Feature>?
}