package com.suva.inventory.config;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface KafkaStreamsBindings {
    public static final String UPDATE_INVENTORY = "update_inventory";
    public static final String UPDATE_ORDER = "update_order";

    @Input(UPDATE_INVENTORY)
    KStream<?, ?> updateInventory();

    @Output(UPDATE_ORDER)
    KStream<?, ?> updateOrder();
}
