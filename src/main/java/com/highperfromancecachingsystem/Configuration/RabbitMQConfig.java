package com.highperfromancecachingsystem.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "queryQueue";
    public static final String EXCHANGE_NAME = "queryExchange";
    public static final String ROUTING_KEY = "query.key";

    @Bean
    public Queue queryQueue() {
        return new Queue(QUEUE_NAME, false);
    }

    @Bean
    public TopicExchange queryExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queryQueue, TopicExchange queryExchange) {
        return BindingBuilder.bind(queryQueue).to(queryExchange).with(ROUTING_KEY);
    }
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
