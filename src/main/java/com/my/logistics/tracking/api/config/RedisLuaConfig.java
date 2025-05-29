package com.my.logistics.tracking.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class RedisLuaConfig {

    @Bean
    public DefaultRedisScript<String> trackingNumberLuaScript() {
        DefaultRedisScript<String> script = new DefaultRedisScript<>();
        script.setScriptText(
                """
                if redis.call('SISMEMBER', KEYS[1], ARGV[1]) == 0 then
                    redis.call('SADD', KEYS[1], ARGV[1])
                    return ARGV[1]
                else
                    return nil
                end
                """
        );
        script.setResultType(String.class);
        return script;
    }
}
