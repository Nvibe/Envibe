package com.envibe.envibe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Contains configuration beans to initialize a pool of connections to a Redis server as defined in the environment variable REDIS_URL.
 *
 * @author ARMmaster17
 */
@Configuration
public class RedisConfig {

    /**
     * Static function to return Factory object that can be used to generate Redis connections from a common pool.
     * @return Initialized factory object with all environment variables imported.
     * @throws URISyntaxException If supplied REDIS_URL contains invalid syntax or missing fields (must be FQDN with authentication credentials).
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() throws URISyntaxException {
        // Retrieve REDIS_URL, or supply a default connection string if it does not exist.
        String rawURI = System.getenv("REDIS_URL") == null ? "redis://127.0.0.1:6379" : System.getenv("REDIS_URL");
        URI redisURI = new URI(rawURI);
        // Create an empty connection pool to configure.
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // Configure the defaults as per Heroku's official documentation.
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        // Generate the factory object with the above configuration.
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        // Parse the required components from REDIS_URL as defined above.
        jedisConnectionFactory.setHostName(redisURI.getHost());
        jedisConnectionFactory.setPort(redisURI.getPort());
        jedisConnectionFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
        // Detect if we are attempting to connect using a localhost connection.
        if(redisURI.getUserInfo() != null) {
            // If we are not, then parse the password.
            jedisConnectionFactory.setPassword(redisURI.getUserInfo().split(":", 2)[1]);
        }
        // Return configured factory object.
        return jedisConnectionFactory;
    }

    /**
     * Generates a template with a connection from the global Redis pool to run queries against.
     * @return Generic RedisTemplate object that can handle any ISerializable object with a String key.
     * @throws URISyntaxException If supplied REDIS_URL contains invalid syntax or missing fields (must be FQDN with authentication credentials).
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() throws URISyntaxException {
        // Create template object from supplied pre-built from the Spring framework.
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Connect template object to the Jedis factory that manages the connection pool.
        template.setConnectionFactory(jedisConnectionFactory());
        // Return final template object.
        return template;
    }
}
