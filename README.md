# tracking-number-api
🔐 Redis &amp; Concurrency Handling Redis is used with EVAL Lua scripting for atomic uniqueness validation.  Redis ensures only one thread across the cluster inserts a given tracking number.  Java’s Spring Boot app uses RedisTemplate to execute the Lua script.
