package com.market_api.the_market_api.service

import com.market_api.the_market_api.base.BaseService
import com.market_api.the_market_api.model.saleModel.Uom

interface UomService : BaseService<Uom> {
    fun getUomDefault(): Uom
}