[Edit on github](https://github.com/baomidou/mybatis-plus-doc/blob/master/)

# [条件构造器](https://baomidou.gitee.io/mybatis-plus-doc/#/wrapper?id=条件构造器)

实体包装器，用于处理 sql 拼接，排序，实体参数查询等！

补充说明： 使用的是数据库字段，不是Java属性!

实体包装器 EntityWrapper 继承 Wrapper

## [简单示例](https://baomidou.gitee.io/mybatis-plus-doc/#/wrapper?id=简单示例)

- 翻页查询

```java
public Page<T> selectPage(Page<T> page, EntityWrapper<T> entityWrapper) {
  if (null != entityWrapper) {
      entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
  }
  page.setRecords(baseMapper.selectPage(page, entityWrapper));
  return page;
}
```

- 拼接 sql 方式 一

```java
@Test
public void testTSQL11() {
    /*
     * 实体带查询使用方法  输出看结果
     */
    EntityWrapper<User> ew = new EntityWrapper<User>();
    ew.setEntity(new User(1));
    ew.where("user_name={0}", "'zhangsan'").and("id=1")
            .orNew("user_status={0}", "0").or("status=1")
            .notLike("user_nickname", "notvalue")
            .andNew("new=xx").like("hhh", "ddd")
            .andNew("pwd=11").isNotNull("n1,n2").isNull("n3")
            .groupBy("x1").groupBy("x2,x3")
            .having("x1=11").having("x3=433")
            .orderBy("dd").orderBy("d1,d2");
    System.out.println(ew.getSqlSegment());
}
```

- 拼接 sql 方式 二

```java
int buyCount = selectCount(Condition.create()
                .setSqlSelect("sum(quantity)")
                .isNull("order_id")
                .eq("user_id", 1)
                .eq("type", 1)
                .in("status", new Integer[]{0, 1})
                .eq("product_id", 1)
                .between("created_time", startDate, currentDate)
                .eq("weal", 1));
```

- 自定义 SQL 方法如何使用 Wrapper

mapper java 接口方法

```java
List<User> selectMyPage(RowBounds rowBounds, @Param("ew") Wrapper<T> wrapper);
```

mapper xml 定义

```xml
<select id="selectMyPage" resultType="User">
  SELECT * FROM user 
  <where>
  ${ew.sqlSegment}
  </where>
</select>
```

关于 ${ew.sqlSegment} 使用了 $ 不要误以为就会被 sql 注入，请放心使用 mp 内部对 wrapper 进行了字符转义处理！

## [条件参数说明](https://baomidou.gitee.io/mybatis-plus-doc/#/wrapper?id=条件参数说明)

| 查询方式     | 说明                              |
| ------------ | --------------------------------- |
| setSqlSelect | 设置 SELECT 查询字段              |
| where        | WHERE 语句，拼接 + `WHERE 条件`   |
| and          | AND 语句，拼接 + `AND 字段=值`    |
| andNew       | AND 语句，拼接 + `AND (字段=值)`  |
| or           | OR 语句，拼接 + `OR 字段=值`      |
| orNew        | OR 语句，拼接 + `OR (字段=值)`    |
| eq           | 等于=                             |
| allEq        | 基于 map 内容等于=                |
| ne           | 不等于<>                          |
| gt           | 大于>                             |
| ge           | 大于等于>=                        |
| lt           | 小于<                             |
| le           | 小于等于<=                        |
| like         | 模糊查询 LIKE                     |
| notLike      | 模糊查询 NOT LIKE                 |
| in           | IN 查询                           |
| notIn        | NOT IN 查询                       |
| isNull       | NULL 值查询                       |
| isNotNull    | IS NOT NULL                       |
| groupBy      | 分组 GROUP BY                     |
| having       | HAVING 关键词                     |
| orderBy      | 排序 ORDER BY                     |
| orderAsc     | ASC 排序 ORDER BY                 |
| orderDesc    | DESC 排序 ORDER BY                |
| exists       | EXISTS 条件语句                   |
| notExists    | NOT EXISTS 条件语句               |
| between      | BETWEEN 条件语句                  |
| notBetween   | NOT BETWEEN 条件语句              |
| addFilter    | 自由拼接 SQL                      |
| last         | 拼接在最后，例如：last("LIMIT 1") |

注意！ xxNew 都是另起 `( ... )` 括号包裹。



## QueryWrapper

> 说明:
>
> 继承自 AbstractWrapper ,自身的内部属性 entity 也用于生成 where 条件
> 及 LambdaQueryWrapper, 可以通过 new QueryWrapper().lambda() 方法获取

### [#](https://mybatis.plus/guide/wrapper.html#select)select





```java
select(String... sqlSelect)
select(Predicate<TableFieldInfo> predicate)
select(Class<T> entityClass, Predicate<TableFieldInfo> predicate)
```

- 设置查询字段

说明:

以上方分法为两类.
第二类方法为:过滤查询字段(主键除外),入参不包含 class 的调用前需要`wrapper`内的`entity`属性有值! 这两类方法重复调用以最后一次为准

- 例: `select("id", "name", "age")`
- 例: `select(i -> i.getProperty().startsWith("test"))`

### [#](https://mybatis.plus/guide/wrapper.html#excludecolumns)excludeColumns

- 排除查询字段

已从`3.0.5`版本上移除此方法!

## [#](https://mybatis.plus/guide/wrapper.html#updatewrapper)UpdateWrapper

说明:

继承自 `AbstractWrapper` ,自身的内部属性 `entity` 也用于生成 where 条件
及 `LambdaUpdateWrapper`, 可以通过 `new UpdateWrapper().lambda()` 方法获取!

### [#](https://mybatis.plus/guide/wrapper.html#set)set



 



```java
set(String column, Object val)
set(boolean condition, String column, Object val)
```

- SQL SET 字段
- 例: `set("name", "老李头")`
- 例: `set("name", "")`--->数据库字段值变为**空字符串**
- 例: `set("name", null)`--->数据库字段值变为`null`

### [#](https://mybatis.plus/guide/wrapper.html#setsql)setSql

```java
setSql(String sql)
```

- 设置 SET 部分 SQL
- 例: `setSql("name = '老李头')`

### [#](https://mybatis.plus/guide/wrapper.html#lambda)lambda

- 获取 `LambdaWrapper`
  在`QueryWrapper`中是获取`LambdaQueryWrapper`
  在`UpdateWrapper`中是获取`LambdaUpdateWrapper`

## [#](https://mybatis.plus/guide/wrapper.html#使用-wrapper-自定义sql)使用 Wrapper 自定义SQL

需求来源:

在使用了`mybatis-plus`之后, 自定义SQL的同时也想使用`Wrapper`的便利应该怎么办？ 在`mybatis-plus`版本`3.0.7`得到了完美解决 版本需要大于或等于`3.0.7`, 以下两种方案取其一即可

### [#](https://mybatis.plus/guide/wrapper.html#service-java)Service.java

```java
mysqlMapper.getAll(Wrappers.<MysqlData>lambdaQuery().eq(MysqlData::getGroup, 1));
```

### [#](https://mybatis.plus/guide/wrapper.html#方案一-注解方式-mapper-java)方案一 注解方式 Mapper.java

```java
@Select("select * from mysql_data ${ew.customSqlSegment}")
List<MysqlData> getAll(@Param(Constants.WRAPPER) Wrapper wrapper);
```

### [#](https://mybatis.plus/guide/wrapper.html#方案二-xml形式-mapper-xml)方案二 XML形式 Mapper.xml

```xml
<select id="getAll" resultType="MysqlData">
	SELECT * FROM mysql_data ${ew.customSqlSegment}
</select>
```





# MP功能集合演示项目

该项目演示以下功能：

1. 逻辑删除
2. 自动填充
3. 自定义全局方法：insert/insertBatch

关于自定义全局方法攻略：

1.定义SQL: 参考DeleteAll, MysqlInsertAllBatch

```
public class MysqlInsertAllBatch extends AbstractMethod {
    @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            return null;//TODO: 自定义SQL
        }
}
```

2.注册： 参考MyLogicSqlInjector, 注册自定义方法

```
public class MyLogicSqlInjector extends LogicSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList() {
        List<AbstractMethod> methodList = super.getMethodList();
        methodList.add(new DeleteAll());
        methodList.add(new MyInsertAll());
        methodList.add(new MysqlInsertAllBatch());
        return methodList;
    }
}
```

3.把方法定义到BaseMapper，参考MyBaseMapper

```
public interface MyBaseMapper<T> extends BaseMapper<T> {
    
    Integer deleteAll();
    
    int myInsertAll(T entity);
    
    int mysqlInsertAllBatch(@Param("list") List<T> batchList);
}
```

> 注意： baseMapper的方法名称必须和自定义全局方法里的id一致

```
public class MysqlInsertAllBatch extends AbstractMethod {

    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        String sql = "xxxxxx";
        //第三个参数必须和baseMapper的自定义方法名一致
        return this.addInsertMappedStatement(mapperClass, modelClass, "mysqlInsertAllBatch", sqlSource, new NoKeyGenerator(), null, null);
    }
```

其中insertBatch: 参考MysqlInsertAllBatch

演示批量保存使用mysql特有语法:

```
insert into user(id, name, age) values (1, "a", 17), (2,"b", 18)
```

> 坑点：

- 在演示自定义批量和自动填充功能时，需要在mapper方法的参数上定义@Param(),
- 而mp默认仅支持 list, collection, array 3个命名，不然无法自动填充