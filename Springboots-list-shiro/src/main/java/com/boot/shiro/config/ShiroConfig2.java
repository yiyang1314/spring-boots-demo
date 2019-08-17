package com.boot.shiro.config;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ShiroConfig2 {
	
	@Bean
	public Realm realm() {
		log.debug(" TextConfigurationRealm");
		TextConfigurationRealm realm=new TextConfigurationRealm();
        realm.setUserDefinitions("sang=123,user\n  admin=123,admin\n user=123");
        realm.setUserDefinitions("admin=1234");
        realm.setRoleDefinitions("admin=read,write\n  user=read");
        return realm;
	}
	
	@Bean
	public ShiroFilterChainDefinition shiroFilterChainDefinition() {
		log.debug(" shiroFilterChainDefinition");
		DefaultShiroFilterChainDefinition  chain=new DefaultShiroFilterChainDefinition();
		chain.addPathDefinition("/login", "anon");
		chain.addPathDefinition("/doLogin", "anon");
		chain.addPathDefinition("/logout", "logout");
		chain.addPathDefinition("/**", "authc");
        return chain;
	}
	
	@Bean
	public ShiroDialect shiroDialect() {
        return new ShiroDialect();
	}
	
	
//	/**
//	 * FilterRegistrationBean
//	 * @return
//	 */
//	@Bean
//	public FilterRegistrationBean filterRegistrationBean() {
//		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter")); 
//        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*"); 
//        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//        return filterRegistration;
//	}
//	
//	/**
//	 * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean
//	 * @return
//	 */
//	@Bean(name = "shiroFilter")
//	public ShiroFilterFactoryBean shiroFilter(){
//		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
//		bean.setSecurityManager(securityManager());
//		bean.setLoginUrl("/login");
//		bean.setUnauthorizedUrl("/unauthor");
//		
//		Map<String, Filter>filters = Maps.newHashMap();
//		filters.put("perms", urlPermissionsFilter());
//		filters.put("anon", new AnonymousFilter());
//		bean.setFilters(filters);
//		
//		Map<String, String> chains = Maps.newHashMap();
//		chains.put("/login", "anon");
//		chains.put("/unauthor", "anon");
//		chains.put("/logout", "logout");
//		chains.put("/base/**", "anon");
//		chains.put("/css/**", "anon");
//		chains.put("/layer/**", "anon");
//		chains.put("/**", "authc,perms");
//		bean.setFilterChainDefinitionMap(chains);
//		return bean;
//	}
//	
//	/**
//	 * @see org.apache.shiro.mgt.SecurityManager
//	 * @return
//	 */
//	@Bean(name="securityManager")
//	public DefaultWebSecurityManager securityManager() {
//		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
//		manager.setRealm(userRealm());
//		manager.setCacheManager(cacheManager());
//		manager.setSessionManager(defaultWebSessionManager());
//		return manager;
//	}
//	
//	/**
//	 * @see DefaultWebSessionManager
//	 * @return
//	 */
//	@Bean(name="sessionManager")
//	public DefaultWebSessionManager defaultWebSessionManager() {
//		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		sessionManager.setCacheManager(cacheManager());
//		sessionManager.setGlobalSessionTimeout(1800000);
//		sessionManager.setDeleteInvalidSessions(true);
//		sessionManager.setSessionValidationSchedulerEnabled(true);
//		sessionManager.setDeleteInvalidSessions(true);
//		return sessionManager;
//	}
//	
//	/**
//	 * @see UserRealm--->AuthorizingRealm
//	 * @return
//	 */
//	@Bean
//	@DependsOn(value="lifecycleBeanPostProcessor")
//	public UserRealm userRealm() {
//		UserRealm userRealm = new UserRealm();
//		userRealm.setCacheManager(cacheManager());
//		return userRealm;
//	}
//	
//	@Bean
//	public URLPermissionsFilter urlPermissionsFilter() {
//		return new URLPermissionsFilter();
//	}
//	
//	@Bean
//	public EhCacheManager cacheManager() {
//		EhCacheManager cacheManager = new EhCacheManager();
//		cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//		return cacheManager;
//	}
//	
//	@Bean
//	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
//		return new LifecycleBeanPostProcessor();
//	}
}
