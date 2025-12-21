package ru.skillbox.socialnetwork.account.config.kafka;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import ru.skillbox.socialnetwork.account.dto.kafka.UserRegisteredEvent;
import ru.skillbox.socialnetwork.account.dto.kafka.UserUpdateEmailEvent;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;

    private Map<String, Object> commonConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "ru.skillbox.socialnetwork.*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }

    @Bean
    public ConsumerFactory<String, UserRegisteredEvent> userRegisteredConsumerFactory() {
        Map<String, Object> props = commonConsumerConfigs();

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(UserRegisteredEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> userRegisteredKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserRegisteredEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userRegisteredConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, UserUpdateEmailEvent> userUpdateEmailEventConsumerFactory() {
        Map<String, Object> props = commonConsumerConfigs();

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(UserUpdateEmailEvent.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserUpdateEmailEvent> userUpdateEmailEventConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, UserUpdateEmailEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userUpdateEmailEventConsumerFactory());
        return factory;
    }
}
