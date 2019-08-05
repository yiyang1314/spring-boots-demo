package com.boot.web.autoconfoguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({ UserProperties.class })
@EnableConfigurationProperties(UserProperties.class)
public class UserAutoConfiguration {
	private UserProperties userProperties;
	@Bean
	public UserProperties userProperties(UserProperties userProperties) {
		return userProperties;
	}
}
