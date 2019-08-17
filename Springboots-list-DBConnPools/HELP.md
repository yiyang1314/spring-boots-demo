# 1. boot2整合actuator应用监控

1. 引入依赖
```java
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
```

2. 配置属性文件

   ```properties
   #之前的配置
   management.port=9001
   management.security.enabled=false
   #之后的配置
   #actuator端口 
   management.server.port=9001
   #修改访问路径  2.0之前默认是/   2.0默认是 /actuator  可以通过这个属性值修改  
   management.endpoints.web.base-path=/monitor
   #开放所有页面节点  默认只开启了health、info两个节点
   management.endpoints.web.exposure.include=*
   #显示健康具体信息  默认不会显示详细信息  
   management.endpoint.health.show-details=always
   ```

   

3. 访问路径

   > 访问地址：`http://localhost:9001/actuator/mappers`



4. tag进行筛选

> /actuator/metrics/http.server.requests?tag=uri:/actuator/metrics
> 上面的地址可以只关注uri=/actuator/metrics的指标，可以看到该接口一共被访问了多少次，最慢的情况下耗时了多久等。

还可以同时根据多个tag来进行筛选，中间用,隔开就行了。假设我只想关注这个接口的返回状态是500时的情况，可以请求这个地址

> /actuator/metrics/http.server.requests?tag=uri:/actuator/metrics,status:500
> 根据tag进行筛选时，有两个情况需要注意：

返回值中的availableTags部分列出的时当前可用的tag的key和value。如果某个接口没有返回过500而你却查询tag=500的情况，则接口会报错

[^注意]:根据多个tag进行筛选时，如果在chrome浏览器中直接请求无果的话，可以借助curl命令或postman之类的工具试试

---------------------------

#2. SpringBoot集成Prometheus

1. 添加依赖

   ```xml
   		<dependency>
   			<groupId>io.micrometer</groupId>
   			<artifactId>micrometer-registry-prometheus</artifactId>
   			<version>1.1.3</version>
   		</dependency>
   
   ```

   

2. 配置属性

   ```properties
   spring.application.name=springboot_prometheus
   management.endpoints.web.exposure.include=*
   management.metrics.tags.application=${spring.application.name}
   ```

   

3. 访问地址

   > 访问地址：http://localhost:9001/actuator/prometheus



------------------------------



#3.整合各类连接池

##1. 集成 hikari springboot默认集成，只需要简单的配置

###1.添加pom依赖文件
```xml
		<dependency>
		    <groupId>com.zaxxer</groupId>
		    <artifactId>HikariCP</artifactId>
		</dependency>
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
```

###2. 配置属性文件
```properties
			# hikari
			#spring.datasource.url=jdbc:mysql://127.0.0.1:3306/springboot2
			#spring.datasource.username=root
			#spring.datasource.password=root
			#spring.datasource.driver-class-name=com.mysql.jdbc.Driver
```

###3. java配置
```java
	@Configuration
	public class DataSourceConfiguration {
	 
	    // Hikari 连接池
	//    @Bean(name = "dataSource")
	//    public DataSource dataSource(@Autowired Environment environment) {
	//        HikariDataSource ds = new HikariDataSource();
	//        ds.setJdbcUrl(environment.getProperty("spring.datasource.url"));
	//        ds.setUsername(environment.getProperty("spring.datasource.username"));
	//        ds.setPassword(environment.getProperty("spring.datasource.password"));
	//        ds.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
	//        return ds;
	//    }
	}
```
### 4. 使用jdbcTemplate即可

-------------------------

##2. 集成 c3p0 springboot默认集成，只需要简单的配置
### 1. 添加pom依赖文件

--------------------------------

```xml
		<dependency>
    <groupId>c3p0</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.1.2</version>
	</dependency>
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-jdbc</artifactId>
	</dependency>
```

###2. 配置属性文件
```properties
			# c3p0
			#c3p0.jdbcUrl=jdbc:mysql://127.0.0.1:3306/springboot2
			#c3p0.user=root
			#c3p0.password=root
			#c3p0.driverClass=com.mysql.jdbc.Driver
			#c3p0.minPoolSize=2
			#c3p0.maxPoolSize=10
			#c3p0.maxIdleTime=1800000
			#c3p0.acquireIncrement=3
			#c3p0.maxStatements=1000
			#c3p0.initialPoolSize=3
			#c3p0.idleConnectionTestPeriod=60
			#c3p0.acquireRetryAttempts=30
			#c3p0.acquireRetryDelay=1000
			#c3p0.breakAfterAcquireFailure=false
			#c3p0.testConnectionOnCheckout=false
```

###3. java配置
```java
		import org.springframework.context.annotation.Configuration;
		 
		@Configuration
		public class DataSourceConfiguration {
		 
		    // c3p0 连接池
		//    @Bean(name = "dataSource")
		//    @Qualifier(value = "dataSource")
		//    @Primary
		//    @ConfigurationProperties(prefix = "c3p0")
		//    public DataSource dataSource(@Autowired Environment environment) {
		//        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
		//    }
		}
```
###4. 使用jdbcTemplate即可

##3. 集成 druid springboot默认集成，只需要简单的配置
###1. 添加pom依赖文件
```xml
		<dependency>
		    <groupId>com.alibaba</groupId>
		    <artifactId>druid</artifactId>
		    <version>1.0.29</version>
		</dependency>
		<dependency>
		    <groupId>log4j</groupId>
		    <artifactId>log4j</artifactId>
		    <version>1.2.16</version>
		    <scope>compile</scope>
		</dependency>
		<dependency>
		    <groupId>mysql</groupId>
		    <artifactId>mysql-connector-java</artifactId>
		</dependency>
		<dependency>
		    <groupId>org.springframework.boot</groupId>
		    <artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
```

###2. 配置属性文件
```properties
		# durid
		# 驱动配置信息
		spring.datasource.url = jdbc:mysql://127.0.0.1:3306/springboot2
		spring.datasource.username = root
		spring.datasource.password = root
		spring.datasource.driverClassName = com.mysql.jdbc.Driver
		#连接池的配置信息
		spring.datasource.initialSize=5
		spring.datasource.minIdle=5
		spring.datasource.maxActive=20
		spring.datasource.maxWait=60000
		spring.datasource.timeBetweenEvictionRunsMillis=60000
		spring.datasource.minEvictableIdleTimeMillis=300000
		spring.datasource.validationQuery=SELECT 1 FROM DUAL
		spring.datasource.testWhileIdle=true
		spring.datasource.testOnBorrow=false
		spring.datasource.testOnReturn=false
		spring.datasource.poolPreparedStatements=true
		spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
		spring.datasource.filters=stat,wall,log4j
		spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
		
		
		log4j.rootCategory=INFO, stdout
		log4j.rootLogger=info, stdout
		 
		### stdout ###
		log4j.appender.stdout=org.apache.log4j.ConsoleAppender
		log4j.appender.stdout.Target=System.out
		log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
		log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p - %m%n
		 
		### set package ###
		log4j.logger.org.springframework=info
		log4j.logger.org.apache.catalina=info
		log4j.logger.org.apache.commons.digester.Digester=info
		log4j.logger.org.apache.catalina.startup.TldConfig=info
		log4j.logger.chb.test=debug
```

###3. java配置
```java
		import com.alibaba.druid.pool.DruidDataSource;
		import org.springframework.beans.factory.annotation.Value;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.Configuration;
		import org.springframework.context.annotation.Primary;
		 
		import javax.sql.DataSource;
		import java.sql.SQLException;
		 
		@Configuration
		public class DruidDataSourceConfiguration {
		//    private Logger logger = LoggerFactory.getLogger(DruidDataSourceConfiguration.class);
		 
		    @Value("${spring.datasource.url}")
		    private String dbUrl;
		 
		    @Value("${spring.datasource.username}")
		    private String username;
		 
		    @Value("${spring.datasource.password}")
		    private String password;
		 
		    @Value("${spring.datasource.driverClassName}")
		    private String driverClassName;
		 
		    @Value("${spring.datasource.initialSize}")
		    private int initialSize;
		 
		    @Value("${spring.datasource.minIdle}")
		    private int minIdle;
		 
		    @Value("${spring.datasource.maxActive}")
		    private int maxActive;
		 
		    @Value("${spring.datasource.maxWait}")
		    private int maxWait;
		 
		    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
		    private int timeBetweenEvictionRunsMillis;
		 
		    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
		    private int minEvictableIdleTimeMillis;
		 
		    @Value("${spring.datasource.validationQuery}")
		    private String validationQuery;
		 
		    @Value("${spring.datasource.testWhileIdle}")
		    private boolean testWhileIdle;
		 
		    @Value("${spring.datasource.testOnBorrow}")
		    private boolean testOnBorrow;
		 
		    @Value("${spring.datasource.testOnReturn}")
		    private boolean testOnReturn;
		 
		    @Value("${spring.datasource.poolPreparedStatements}")
		    private boolean poolPreparedStatements;
		 
		    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
		    private int maxPoolPreparedStatementPerConnectionSize;
		 
		    @Value("${spring.datasource.filters}")
		    private String filters;
		 
		    @Value("{spring.datasource.connectionProperties}")
		    private String connectionProperties;
		 
		    @Bean     //声明其为Bean实例
		    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
		    public DataSource dataSource() {
		        DruidDataSource datasource = new DruidDataSource();
		 
		        datasource.setUrl(this.dbUrl);
		        datasource.setUsername(username);
		        datasource.setPassword(password);
		        datasource.setDriverClassName(driverClassName);
		 
		        //configuration
		        datasource.setInitialSize(initialSize);
		        datasource.setMinIdle(minIdle);
		        datasource.setMaxActive(maxActive);
		        datasource.setMaxWait(maxWait);
		        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		        datasource.setValidationQuery(validationQuery);
		        datasource.setTestWhileIdle(testWhileIdle);
		        datasource.setTestOnBorrow(testOnBorrow);
		        datasource.setTestOnReturn(testOnReturn);
		        datasource.setPoolPreparedStatements(poolPreparedStatements);
		        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		        try {
		            datasource.setFilters(filters);
		        } catch (SQLException e) {
		//            logger.error("druid configuration initialization filter", e);
		            e.printStackTrace();
		        }
		        datasource.setConnectionProperties(connectionProperties);
		 
		        return datasource;
		    }
		}
```
###4. 使用jdbcTemplate即可

##4. HikariCP-pringboot2 默认使用的连接池就是 HikariCP
--HikariCP 凭借体积小，性能高，稳定可靠的特性，已经成为目前体验最好的数据库连接池。--
```yml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/test?useSSL=false
    username: root
    password: root
    driver-class-name: com.mysql.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    hikari:
      minimum-idle: 3
      auto-commit: true
      idle-timeout: 10000
      pool-name: DatebookHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: validat
```
