package com.cerashealth.iamhome.logging.dto

import com.cerashealth.iamhome.logging.model.Log
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.sql.Timestamp

data class LoggingMessageDto(
    var data: Map<String,String>? = null,
    var performer: String? = null,
    var sourceMicroservice: String? = null,
    var type: String? = null,
    var category: String? = null,
    var action: String? = null,
    var status: String? = null,
    var message: String? = null
){
    fun getLogObject(payload: String): Log {
        val log = Log()
        log.created = Timestamp(System.currentTimeMillis()).toString()
        log.action = this.action
        log.message = this.message
        log.type = this.type
        log.category = this.category
        log.performer = this.performer
        log.sourceMicroservice = this.sourceMicroservice
        val typeRef = object : TypeReference<Map<String, String>>() { }
        val mapper = ObjectMapper()
        val dataObject:Map<String,String> = mapper.readValue(payload, typeRef)
        log.data = dataObject
        log.status = this.status
        return log
    }
}
