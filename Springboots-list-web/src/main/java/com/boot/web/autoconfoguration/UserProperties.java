package com.boot.web.autoconfoguration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;

import org.springframework.boot.autoconfigure.solr.SolrProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties("user")
@Data
public class UserProperties {
		private String userName;
		private int age;
		private double salary;
		//@EnableConfigurationProperties(SpringDataWebProperties.class)
//		@Configuration
//		@ConditionalOnClass({ HttpSolrClient.class, CloudSolrClient.class })
//		@EnableConfigurationProperties(SolrProperties.class)
}
