package com.cerashealth.iamhome.logging.kafka

import com.cerashealth.iamhome.logging.dto.LoggingMessageDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import com.cerashealth.iamhome.logging.repository.LogRepository
import com.fasterxml.jackson.databind.DeserializationFeature
import org.springframework.beans.factory.annotation.Autowired

class KafkaConsumer {

    @Autowired
    private lateinit var logRepository: LogRepository

    @KafkaListener(topics = ["\${ceras.kafka.consumer.logging.topic}"])
    fun receive(payload: String) {
        try {
            val loggingMessageDto = ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(payload, LoggingMessageDto::class.java)
            val log = loggingMessageDto.getLogObject(payload)
            val savedLog = logRepository.save(log)
            logger.info("{}: received payload='{}'", savedLog.logId, payload)
        } catch (e: Exception) {
            logger.error(e.toString())
        }

    }

    companion object {

        private val logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    }

}
