package com.cerashealth.iamhome.logging.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

import java.util.HashMap

@Configuration
@EnableKafka
class KafkaConsumerConfiguration {

    @Value("\${ceras.kafka.consumer.logging.bootstrap}")
    private val bootstrapServers: String? = null

    @Value("\${ceras.kafka.consumer.logging.groupid}")
    private val kafkaGroupId: String? = null

    @Value("\${ceras.kafka.consumer.logging.concurrent.requests}")
    private val concurrentProcessing = 0

    @Bean
    fun consumerConfigs(): Map<String, Any> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers.toString()
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = kafkaGroupId.toString()
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory(consumerConfigs())
    }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()
        factory.setConcurrency(concurrentProcessing)
        factory.consumerFactory = consumerFactory()
        return factory
    }

    @Bean
    fun kafkaConsumer(): KafkaConsumer {
        return KafkaConsumer()
    }

}