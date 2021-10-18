package com.example.mail.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private final List<String> kafkaServers;
    private final String mailSendConsumerGroupId;

    public KafkaConsumerConfig(@Value("${kafka.bootstrapServers}") List<String> kafkaServers) {
        this.kafkaServers = kafkaServers;
        this.mailSendConsumerGroupId = "mail-send-consumer-group";
    }

    @Bean
    public ConsumerFactory<String, MailSendDTO> consumerConfig() {
        // TODO Auto-generated method stub
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        // config.put(ConsumerConfig.GROUP_ID_CONFIG, this.mailSendConsumerGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, String.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                new ErrorHandlingDeserializer<>(new JsonDeserializer<>(MailSendDTO.class, false))
        );
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MailSendDTO>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MailSendDTO> listener = new ConcurrentKafkaListenerContainerFactory<>();
        listener.setConsumerFactory(consumerConfig());
//        listener.setRecordFilterStrategy(consumerRecord -> {
//            /**
//             * Kafka 비동기 메시지 수신에 대한 MDC(Mapped Diagnosic Context)
//             * logback, sleuth trace정보 바인딩 필터
//             */
////            for (Header item : consumerRecord.headers()) {
////                String key = item.key().toUpperCase();
////            }
//            return false;
//        });
        return listener;
    }


}
