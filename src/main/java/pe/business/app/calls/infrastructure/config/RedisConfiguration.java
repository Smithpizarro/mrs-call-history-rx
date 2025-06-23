package pe.business.app.calls.infrastructure.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration(proxyBeanMethods = true)
public class RedisConfiguration {


    @Bean
    public ReactiveRedisOperations<String, String> reactiveRedisOperations(ReactiveRedisConnectionFactory reactiveRedisConnectionFactory) {
        Jackson2JsonRedisSerializer<String> serializer = new Jackson2JsonRedisSerializer<>(String.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, String> builder =RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, String> context = builder.value(serializer).build();
        return new ReactiveRedisTemplate<>(reactiveRedisConnectionFactory, context);
    }

    /*
    @Bean
    ReactiveRedisOperations<String, String> reactiveRedisOperations(
            ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }*/


}

