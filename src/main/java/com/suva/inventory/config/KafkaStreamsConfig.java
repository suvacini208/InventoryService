package com.suva.inventory.config;

import org.apache.kafka.streams.KafkaStreams;
import org.springframework.cloud.stream.binder.kafka.properties.KafkaBinderConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.KafkaStreamsCustomizer;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanCustomizer;
import org.springframework.stereotype.Component;
@Component
public class KafkaStreamsConfig {

    KafkaBinderConfigurationProperties kafkaBinderConfigurationProperties;

    @Bean
    public StreamsBuilderFactoryBeanCustomizer streamsBuilderFactoryBeanCustomizer() {
        return factoryBean -> {
            factoryBean.setKafkaStreamsCustomizer(new KafkaStreamsCustomizer() {
                @Override
                public void customize(KafkaStreams kafkaStreams) {
                    // CUSTOMIZATIONS
                }
            });
        };
    }

}
