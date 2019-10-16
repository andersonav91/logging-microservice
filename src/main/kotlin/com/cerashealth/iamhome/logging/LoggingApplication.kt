package com.cerashealth.iamhome.logging

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
class LoggingApplication

fun main(args: Array<String>) {
	runApplication<LoggingApplication>(*args)
}
