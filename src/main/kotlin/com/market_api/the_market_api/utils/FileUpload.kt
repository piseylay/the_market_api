package com.market_api.the_market_api.utils

import com.market_api.the_market_api.responseFormat.exception.CustomBadRequestException
import com.market_api.the_market_api.responseFormat.exception.CustomNotAcceptableException
import com.market_api.the_market_api.responseFormat.exception.CustomNotFoundException
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import javax.servlet.http.HttpServletRequest


class FileUpload {
    /**
     * Can call class to using other where like singleton
     */
    companion object {

        fun storeImage(file:MultipartFile,filePath:String): String {
            val path = Paths.get(filePath).toAbsolutePath().normalize()
            val directory = File(path.toString())
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val extension = file.originalFilename.toString()
            val sub = extension.substring(extension.lastIndexOf("."))
            val nameFile = UUID.randomUUID().toString().plus(sub)

            try {
                Files.copy(file.inputStream, path.resolve(nameFile), StandardCopyOption.REPLACE_EXISTING)
            } catch (e: IOException) {
                return null.toString()
            }
            return nameFile
        }

        fun storeImage(file:MultipartFile,filePath:String, fileName: String): String {
            val path = Paths.get(filePath).toAbsolutePath().normalize()
            val directory = File(path.toString())
            if (!directory.exists()) {
                directory.mkdirs()
            }

            try {
                Files.copy(file.inputStream, path.resolve(fileName), StandardCopyOption.REPLACE_EXISTING)
            } catch (e: IOException) {
                return null.toString()
            }
            return fileName
        }

        fun removeImage (fileName : String,fileProperty:String): Boolean {
            val path = Paths.get(fileProperty).toAbsolutePath().normalize()
            val filePath = path.resolve(fileName).normalize()
            val file = File(filePath.toString())

            return if(file.exists()) file.delete()
            else false
        }

        fun loadFile(fileName:String,fileProperty:String,request:HttpServletRequest) : ResponseEntity<Resource>{
            try {
                val path = Paths.get(fileProperty).toAbsolutePath().normalize()
                val filePath = path.resolve(fileName).normalize()
                val resource = UrlResource(filePath.toUri())

                resource.contentLength()

                val contentType = request.servletContext.getMimeType(resource.file.absolutePath)
                        ?: throw CustomNotAcceptableException("Invalid file type.")
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .body<Resource>(resource)
            } catch (e: MalformedURLException) {
                throw CustomBadRequestException(e.message!!)
            } catch (e: IOException) {
                throw CustomNotFoundException("File not found.")
            }
        }
     }
 }