package com.market_api.the_market_api.controller.system

import com.market_api.the_market_api.responseFormat.response.ResponseObjectMap
import com.market_api.the_market_api.service.StorageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@RestController
class StorageController {
    @Autowired
    lateinit var storageService: StorageService
    var response = ResponseObjectMap()
    var resource: String = "./resources/images/"

    @PostMapping("upload-image-{type}")
    fun uploadImage( @PathVariable type: String, file: MultipartFile): MutableMap<String, Any> {
        println("ok")
        return  response.responseObject(storageService.storeFileImage("$resource$type",file))
    }

    @GetMapping("resources/images/{type}/{fileName:.+}")
    fun loadImage(@PathVariable fileName: String, @PathVariable type: String, request: HttpServletRequest): ResponseEntity<*>?  {
        return storageService.loadFileImage(fileName, "$resource$type",request)
    }
}
