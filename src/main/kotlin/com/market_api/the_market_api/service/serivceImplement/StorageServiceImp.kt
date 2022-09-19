package com.market_api.the_market_api.service.serivceImplement

import com.market_api.the_market_api.utils.FileUpload
import com.market_api.the_market_api.model.response.FileUploadResponse
import com.market_api.the_market_api.service.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@Service
class StorageServiceImp: StorageService {

    override fun storeFileImage(pathFile: String, file: MultipartFile): FileUploadResponse? {
        return  FileUploadResponse(pathFile.substring(1) + "/" + FileUpload.storeImage(file, pathFile))
    }

    override fun removeFileImage(name: String, pathFile: String): Boolean? = FileUpload.removeImage(name, pathFile)

    override fun loadFileImage(fileName: String, pathFile: String, request: HttpServletRequest): ResponseEntity<*>? {
        return try { FileUpload.loadFile(fileName, pathFile, request) } catch (ex:Exception) { null }
    }
}