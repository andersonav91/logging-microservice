package com.cerashealth.iamhome.logging.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    @Value("\${ceras.cors.allowed}")
    lateinit var allowedOrigins:String

    override fun addCorsMappings(registry: CorsRegistry) {
        var origins = allowedOrigins.split(",").toTypedArray();
        registry.addMapping("/**").allowedOrigins(*origins)
    }
}
