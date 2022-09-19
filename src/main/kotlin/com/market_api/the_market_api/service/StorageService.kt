package com.market_api.the_market_api.service

import com.market_api.the_market_api.model.response.FileUploadResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

interface StorageService {
    fun storeFileImage(pathFile: String, file: MultipartFile): FileUploadResponse?
    fun removeFileImage(name: String, pathFile: String): Boolean?
    fun loadFileImage(fileName: String, pathFile: String, request: HttpServletRequest): ResponseEntity<*>?
}