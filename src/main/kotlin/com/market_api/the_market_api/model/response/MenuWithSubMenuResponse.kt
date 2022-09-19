package com.market_api.the_market_api.model.response


data class MenuWithSubMenuResponse(var id : Long ?= 0, var title : String ?= null, var subs : List<SubMenuResponse> ?= null)