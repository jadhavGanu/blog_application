package com.project.blogging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.ResponseEntity;

@Configuration
public class RedisConfig {

    // ① Custom template only for ResponseEntity
    @Bean
    public RedisTemplate<String, ResponseEntity<?>> responseEntityTemplate(
            RedisConnectionFactory cf) {

        RedisTemplate<String, ResponseEntity<?>> tpl = new RedisTemplate<>();
        tpl.setConnectionFactory(cf);

        ResponseEntityRedisSerializer respSer = new ResponseEntityRedisSerializer();

        tpl.setDefaultSerializer(respSer);     // 👈 custom serializer
        tpl.setValueSerializer(respSer);
        tpl.setKeySerializer(new StringRedisSerializer());

        return tpl;
    }

    // ② Tell cache manager to use same serializer (so @Cacheable works)
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory cf) {
        SerializationPair<ResponseEntity<?>> pair =
            RedisSerializationContext.SerializationPair.fromSerializer(
                new ResponseEntityRedisSerializer());

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(pair);

        return RedisCacheManager.builder(cf)
                .cacheDefaults(config).build();
    }
}
