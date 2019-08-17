#JPA的学习
JPA是Java Persistence API的简称，中文名Java持久层API。是Java EE5.0平台中Sun为了统一持久层ORM框架而制定的一套标准，注意是一套标准，而不是具体实现，不可能单独存在，需要有其实现产品。Sun挺擅长制定标准的，例如JDBC就是为了统一连接数据库而制定的标准，而我们使用的数据库驱动就是其实现产品。JPA的实现产品有HIbernate，TopLink，OpenJPA等等。值得说一下的是Hibernate的作者直接参与了JPA的制定，所以JPA中的一些东西可以与Hibernate相对比。

JPA特点：

>JPA可以使用xml和注解映射源数据，JPA推荐使用注解来开发。
>它有JPQL语言也是面向对象的查询语言，和hql比较类似。
---------------------


# boot2整合jpa
###1.添加JPA依赖
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
```
###2.添加JPA属性
```properties
#配置服务访问路径和端口号
server.port=8082
server.servlet.context-path=/boot-jpa

#配置数据库驱动，数据源信息
spring.datasource.url=jdbc:mysql://localhost:3306/test?useSSL=false&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

#配置jpa属性
spring.jpa.properties.hibernate.hbm2ddl.auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.show-sql= true

#boot2 默认数据连接池是Hikari 目前最快的连接池，可用下面的方式改变数据源
#spring.datasource.type=com.alibaba.druid.pool.DruidData
```

#2.课外学习
------
 >JPA   实际上就是  Hibernate  的封装，根据Interface 方法名，生成对应的方法，也支持Query注解的方>式。现在说说执行原生  SQL  。
---------
>注解@Query方式执行原生SQL语句：
```java
@Query(value="select user.id from user where user.id =15", nativeQuery = true)
public User queryById(){}
```
注解的方式需要增加一个“nativeQuery=true”来表示是原生  SQL  。

>EntityManager.Query 方式：
```java
Query query = entityManager.createNativeQuery("select user.id from user where user.id =15");
List list = query.getResultList()
```
>复杂原生SQL，占位式：
```java
@PersistenceContext
private EntityManager entityManager;
@Modifying
@Transactional
public StockDetails save(StockDetails o) {
    StringBuffer sb = new StringBuffer();
    sb.append("insert into b_stock_details (transaction_id, pid, price, cost_price, offer_name, game_type, ");
    sb.append(" game_name, game_id, money, game_about, purchase_date, create_time, customer_id, ");
    sb.append(" customer_name, customer_login_name,member_id, member_name, have_customer_id,status, ifrom,  ");
    sb.append("trading_status,game_face_name, receipt_data_one, receipt_data_two) ");
    sb.append(" SELECT ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?  FROM DUAL WHERE NOT EXISTS ");
    sb.append("(SELECT transaction_id FROM b_stock_details where transaction_id= ?)");
    Query query = entityManager.createNativeQuery(sb.toString());
    query.setParameter(1,o.getTransactionId());
    query.setParameter(2,o.getPid());
    query.setParameter(3,o.getPrice());
    query.setParameter(4,o.getCostPrice());
    query.setParameter(5,o.getOfferName());
    query.setParameter(6,o.getGameType());
    query.setParameter(7,o.getGameName());
    query.setParameter(8,o.getGameId());
    query.setParameter(9,o.getMoney());
    query.setParameter(10,o.getGameAbout());
    query.setParameter(11,o.getPurchaseDate());
    query.setParameter(12,o.getCreateTime());
    query.setParameter(13,o.getCustomerId());
    query.setParameter(14,o.getCustomerName());
    query.setParameter(15,o.getCustomerLoginName());
    query.setParameter(16,o.getMemberId());
    query.setParameter(17,o.getMemberName());
    query.setParameter(18,o.getHaveCustomerId());
    query.setParameter(19,o.getStatus());
    query.setParameter(20,o.getIfrom());
    query.setParameter(21,o.getTradingStatus());
    query.setParameter(22,o.getGameFaceName());
    query.setParameter(23,o.getReceiptDataOne());
    query.setParameter(24,o.getReceiptDataTwo());
    query.setParameter(25,o.getTransactionId());
    int result = query.executeUpdate();
    log.debug("插入接口：{}",result>0?"成功":"数据已经重复存在");
    return o;
}
```

如果出现 `jpa Executing an update/delete query`异常，那么是因为你没有添加事物和“@Modifying”比较，把注解加上就可以。


#jpa xml配置
>JPA规范要求在内路径下META-INF目录下放置--persistence.xml--文件，文件名称是固定的。这个文件
>在于spring整合之后就可以取消了

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.0"
    xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd">
    <persistence-unit name="jpa-1" transaction-type="RESOURCE_LOCAL">
        <!-- 
        配置使用什么 ORM 产品来作为 JPA 的实现 
        1. 实际上配置的是  javax.persistence.spi.PersistenceProvider 接口的实现类
        2. 若 JPA 项目中只有一个 JPA 的实现产品, 则也可以不配置该节点. 
        -->
        <!-- <provider>org.hibernate.ejb.HibernatePersistence</provider> -->
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
    
        <!-- 添加持久化类 (推荐配置)-->
        <class>cn.lynu.model.User</class>
        

        <properties>
            <!-- 连接数据库的基本信息 -->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql:///jpa"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="root"/>
            
            <!-- 配置 JPA 实现产品的基本属性. 配置 hibernate 的基本属性 -->
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>


        </properties>
    </persistence-unit>
</persistence>
```

>name：用于定义持久单元名称，必须。
>transaction-type：指定JPA的事务处理策略，RESOURCE_LOCAL 是默认值，数据库级别的事务，只支持一种数据库，
>不支持分布式事务。如果要支持分布式事务，要使用JTA策略：transaction-type：“JTA”

###JPQL
类似于HQL，JPQL也是面向对象的查询语句，也可以完成CRUD操作，但是写法上有点不同，不同点在于查询上，来看一条JPQL语句：

>SELECT u from User u
再来看HQL的写法：

>from User
可以看到JPQL的查询需要将select关键字和需要得到的字段写出来（好在需要得到的字段可以是一个对象），运行这些语句的都是Query接口，只是要注意是不同的包下的。

Query接口的主要方法：

> int executeUpdate() 用于执行update或delete语句。
> List getResultList() 用于执行select语句并返回结果集实体列表。
> Object getSingleResult() 用于执行只返回单个结果实体的select语句。
> Query setFirstResult(int startPosition) 用于设置从哪个实体记录开始返回查询结果。
> Query setMaxResults(int maxResult)  用于设置返回结果实体的最大数。与setFirstResult结合使用可实现分页查询。
> setHint(String hintName, Object value)  设置与查询对象相关的特定供应商参数或提示信息。参数名及其取值需要参考特定 JPA 实现库提供商的文档。如果第二个参数无效将抛出IllegalArgumentException异常。
> setParameter(int position, Object value)  为查询语句的指定位置参数赋值。Position 指定参数序号，value 为赋给参数的值。
> setParameter(String name, Object value)  为查询语句的指定名称参数赋值。

```
#复制代码
        @Test
        public void testHelloJPQL(){
            String jpql="SELECT u from User u";
            Query query = entityManager.createQuery(jpql);
            List list = query.getResultList();  //对应Hibernate中Query接口的list()方法
            System.out.println(list.size());
        }
复制代码
```
我还记得Hibernate的占位符是从0开始的，而JDBC是从1开始的，总是记这些东西很麻烦，所以在JPA中可以指定占位符从几开始：
```java
复制代码
        @Test
        public void testHelloJPQL2(){
            String jpql="select u from User u where id>?1";   
            Query query = entityManager.createQuery(jpql);
            //占位符的索引是从 0 开始,可以指定从几开始,如?1 就是从1开始
            query.setParameter(1, 3);
            List list = query.getResultList();
            System.out.println(list.get(3));
        }
复制代码
```
还可以使用名称占位：
```java
#复制代码
        @Test
        public void testHelloJPQL3(){
            String jpql="select u from User u where id>:id";
            Query query = entityManager.createQuery(jpql);
            //使用名称占位
            query.setParameter("id", 3);
            List list = query.getResultList();
            System.out.println(list.get(3));
        }
复制代码
```

#在JPQL中还可以使用Order by
Order by子句用于对查询结果集进行排序。和SQL的用法类似，可以用 “asc“ 和 "desc“ 指定升降序。如果不显式注明，默认为升序。

>select o from Orders o order by o.id 
>select o from Orders o order by o.address.streetNumber desc 
>select o from Orders o order by o.customer asc, o.id desc
>group by子句与聚合查询

```java
复制代码
        //分组查询(查询 order 数量等于 2 的那些 User)
        @Test
        public void testGroupBy(){
            String jpql="select o.user from Order o group by o.user having count(o.id)=?1";
            List list = entityManager.createQuery(jpql).setParameter(1, 2).getResultList();
            System.out.println(list);
        }
复制代码
```
常用的聚合函数主要有 AVG、SUM、COUNT、MAX、MIN 等，它们的含义与SQL相同。
```java
Query query = entityManager.createQuery(
                    "select max(o.id) from Orders o");
Object result = query.getSingleResult();
Long max = (Long)result;
```
关联查询#
JPQL 也支持和 SQL 中类似的关联语法。如： left out join fetch，  right out join fetch  。eft out join，如left out join fetch是以符合条件的表达式的左侧为主。
```java

        /**
         * JPQL 的关联查询(left outer join fetch)同 HQL 的关联查询. 
         */
        @Test
        public void testLeftOuterJoinFetch(){
            String jpql="select u from User u left outer join fetch u.orders where u.id=?1";
            List<User> list = entityManager.createQuery(jpql).setParameter(1, 16).getResultList();
            System.out.println(list.get(0).getOrders());
        }

```

左外的右边使用的是User类中的orders属性表示另一张表，而且要加上fetch语句，才是一个真正左外连接，要不会报sql异常

子查询#
JPQL也支持子查询，在 where 或 having 子句中可以包含另一个查询。当子查询返回多于 1 个结果集时，它常出现在 any、all、exists表达式中用于集合匹配查询。它们的用法与SQL语句基本相同。
```java

        /**
         * JPQL子查询
         */
        @Test
        public void testSubQuery(){
            //查询所有 User 的 userName 为 赵六 的 Order
            String jpql="select o from Order o where o.user=(select u from User u where u.userName=?1)";
            List list = entityManager.createQuery(jpql).setParameter(1, "赵六").getResultList();
            System.out.println(list);
        }

```

#JPQL函数
>JPQL提供了以下一些内建函数，包括字符串处理函数、算术函数和日期函数。如字符串处理函数：
```prop
substring(String s, int start, int length)：取字串函数。
trim( [leading|trailing|both,char c,String s)：从字符串中去掉首/尾指定的字符或空格。
lower(String s)：将字符串转换成小写形式。
upper(String s)：将字符串转换成大写形式。
length(String s)：求字符串的长度。
locate(String s1, String s2[, int start])：从第一个字符串中查找第二个字符串(子串)出现的位置。若未找到则返回0。
```
--------------------------

```java

        //使用 jpql 内建的函数
        @Test
        public void testJpqlFunction(){
            String jpql="select upper(o.orderName) from Order o";
            List list = entityManager.createQuery(jpql).getResultList();
            System.out.println(list);
        }

```
算术函数主要有 abs、mod、sqrt、size 等。Size 用于求集合的元素个数。

日期函数主要为三个，即 current_date、current_time、current_timestamp，它们不需要参数，返回服务器上的当前日期、时间和时戳。

查询缓存#
刚才说了一个查询缓存的问题，是因为使用Query接口查询的结果并不会放入一级缓存中，因为其未与EntityManager关联，HIbernate中也如此，所以需要配置那个查询缓存，注意：查询缓存需要依赖于二级缓存。

需要在需要查询缓存的时候使用：   query.setHint(QueryHints.HINT_CACHEABLE, true);
```java

        //使用 hibernate 的查询缓存.(query接口查询(hql)的结果不会放到缓存中,需要配置查询缓存) 
        @Test
        public void testQueryCache(){
            String jpql="SELECT u from User u";
            Query query = entityManager.createQuery(jpql);
            //查询缓存(org.hibernate.jpa.QueryHints;)
            query.setHint(QueryHints.HINT_CACHEABLE, true);
            List list = query.getResultList();
            System.out.println(list.size());
            
            //没有配置的时候多次相同的sql查询会发多条相同的sql
            //配置查询缓存之后,相同查询就发一条sql
            //(配置文件中要配置hibernate.cache.use_query_cache)
            
            //query = entityManager.createQuery(jpql);
            //查询缓存(只要给query使用jpql就需要setHint)
            //query.setHint(QueryHints.HINT_CACHEABLE, true);
            list = query.getResultList();
            System.out.println(list.size());
            
        }

```
整合Spring#
Spring整合JPA之后，就不需要persistence.xml文件了，需要在applicationContext.xml文件中配置EntityManagerFactory和JPA的事务：

```xml

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">    

    <!-- 配置 C3P0 数据源 -->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
      <property name="user" value="${jdbc.user}"></property>
      <property name="password" value="${jdbc.password}"></property>
      <property name="driverClass" value="${jdbc.driverClass}"></property>
      <property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
    </bean>

    
    <!-- 配置 EntityManagerFactory -->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <!-- 配置数据源 -->
        <property name="dataSource" ref="dataSource"></property>
        <!-- 配置 JPA 提供商的适配器. 可以通过内部 bean 的方式来配置 -->
            <property name="jpaVendorAdapter">
                <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
            </property>    
            <!-- 配置实体类所在的包 -->
            <property name="packagesToScan" value="cn.lynu.model"></property>
            <!-- 配置 JPA 的基本属性. 例如 JPA 实现产品的属性 -->
            <property name="jpaProperties">
                <props>
                    <prop key="hibernate.show_sql">true</prop>
                    <prop key="hibernate.format_sql">true</prop>
                    <prop key="hibernate.hbm2ddl.auto">update</prop>
                </props>
            </property>
    </bean>

    
    <!-- 配置 JPA 使用的事务管理器 -->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
      <property name="entityManagerFactory" ref="entityManagerFactory"></property>
    </bean>
    
    <!-- 配置支持基于注解是事务配置 -->
    <tx:annotation-driven transaction-manager="transactionManager"/>
    
    <!-- 配置自动扫描的包(组件扫描) -->
    <context:component-scan base-package="cn.lynu"></context:component-scan>

</beans>

```

作者：OverZeal

出处：https://www.cnblogs.com/lz2017/p/7748670.html

本站使用「署名 4.0 国际」创作共享协议，转载请在文章明显位置注明作者及出处。

>其他学习博文：https://www.cnblogs.com/crawl/p/7703679.html，https://www.cnblogs.com/a8457013/p/7753575.html
>Spring Data JPA中的@Query注解使用方式:https://blog.csdn.net/CoderTnT/article/details/81202496
