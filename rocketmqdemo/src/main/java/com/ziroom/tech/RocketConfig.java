package com.ziroom.tech;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author lidm
 * @Date 2020/3/31
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("rocketmq")
public class RocketConfig
{
    private String producerGroup;
    private String consumerGroup;
    private String nsAddr;
    private String topic;

}
