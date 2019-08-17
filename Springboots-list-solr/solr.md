# 1、版本介绍：

jdk1.8
tomcat8
springboot 2.1.3RELEASE(这里有坑,详见下文)
solr 7.4.0 （没有选择最新的版本,是因为项目的boot版本是2.1.3,其对应的solr-solrj.jar版本是7.4.0，为避免出现不可预料不可抗拒不可解决的问题，谨慎选用与之一样版本）



### 下载

- 1.tomcat8的下载不赘述；
- 2.solr下载： 进入solr官网 ，找历史版本下载v7.4.0的压缩包，事实上 [solr archive](http://archive.apache.org/dist/lucene/solr) 本人并没有访问成功,
  如果网友和我一样访问不了，那只能说明你的脸黑，而官网应该是瓦掉了，所以本人肯定是没有问题的，下面把从网上肆虐而来的一个资源分享一下，附带了一个ik分词器的压缩包,后面有用： [百度网盘](https://pan.baidu.com/s/1UgLP-DwRzlviLO8itMFdNA) 提取码：6mhk

------



- 1.解压solr至 D:\JAVA\solr\solr-7.4.0\（下文均以solr-7.4.0代替此全路径）
- 2.解压tomcat至 D:\JAVA\solr\apache-tomcat-8.5.42（下文均以tomcat-8.5.42代替此全路径）

------



- 1.在路径D:\JAVA\solr\下新建文件夹solrhome（下文均以solrhome代替此全路径）
- 2.在solrhome下新建logs文件夹（记下此路径:D:\JAVA\solr\solrhome\logs）
- 3.复制文件夹solr-7.4.0\contrib和solr-7.4.0\dist至solrhome下 ![img](https://oscimg.oschina.net/oscnet/fccc03c86e2215bc7e99751ed49597eded7.jpg)
- 4.复制solr-7.4.0\server\solr下所有文件至solrhome下 ![img](https://oscimg.oschina.net/oscnet/bb795f0dd3502eb52e649ff06345a11c8da.jpg)
- 5.在solrhome下新建new_core文件夹，
- 6.将solr-7.4.0\server\solr\configsets_default\conf文件夹复制到new_core下
- 7.修改solrhome\new_core\conf\solrconfig.xml文件：
  ![img](https://oscimg.oschina.net/oscnet/f18797333acc8332839c5ea901170685870.jpg) ![img](https://oscimg.oschina.net/oscnet/9ba62a7ea97cb12eb2f956df8df3f1f4894.jpg)

贴出具体代码如下：

```
  <lib dir="${solr.install.dir:../}/contrib/extraction/lib" regex=".*\.jar" />
  <lib dir="${solr.install.dir:../}/dist/" regex="solr-cell-\d.*\.jar" />
  
  <lib dir="${solr.install.dir:../}/contrib/clustering/lib/" regex=".*\.jar" />
  <lib dir="${solr.install.dir:../}/dist/" regex="solr-clustering-\d.*\.jar" />
  
  <lib dir="${solr.install.dir:../}/contrib/langid/lib/" regex=".*\.jar" />
  <lib dir="${solr.install.dir:../}/dist/" regex="solr-langid-\d.*\.jar" />
  
  <lib dir="${solr.install.dir:../}/contrib/velocity/lib" regex=".*\.jar" />
  <lib dir="${solr.install.dir:../}/dist/" regex="solr-velocity-\d.*\.jar" />
  <lib dir="${solr.install.dir:../}/dist/" regex="ojdbc\d.*\.jar" />
  <lib dir="${solr.install.dir:../}/dist/" regex="solr-dataimporthandler\d.*\.jar" />
	<requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
		<lst name="defaults">
			<!-- 这个文件名配置有用 -->
			<str name="config">data-config.xml</str>
		</lst>
	</requestHandler>
```

- 7.在solrhome\new_core\conf下新建文件data-config.xml(文件名与上述xml配置保持一致),文件内容如下：

```
<?xml version="1.0" encoding="UTF-8"?> 
<dataConfig>
    <!-- 数据库基本配置，需要将对应数据库驱动放到tomcat下，见后续操作 -->
    <dataSource name="source1" 
	      type="JdbcDataSource"
              driver="com.mysql.jdbc.Driver"
              url="jdbc:mysql://localhost:3306/dbname?useSSL=true&amp;serverTimezone=UTC"
              user="username"
              password="password" />
    <document>
	<!-- entity name必须给值 query为查询语句 field-column对应数据库列 name是查询结果返回的名称，类似mybatis -->
        <entity name="test_demo" dataSource="source1" pk="id"
            query="SELECT id,name FROM demo">
            <field column='name' name='demoName' />
	    <field column='id' name='id' />
        </entity>
    </document>
</dataConfig>
```

- 8.修改conf\managed-schema ![img](https://oscimg.oschina.net/oscnet/0b135be4ac06511a6d3007ea86edde48095.jpg)

**如果配置了ik分词器可以按此修改： <field name="demoName" type="text_ik" indexed="true" stored="true"/>**

------



- 1.将solr-7.4.0\server\solr-webapp\下的webapp文件夹复制到tomcat-8.5.42\webapps 并且修改名称为solr
- 2.在tomcat-8.5.42\webapps\solr\WEB-INF\下新建classes包，将solr-7.4.0\server\resources\log4j2.xml复制到classes包下
- 3.将下图标注jar复制到tomcat-8.5.42\webapps\solr\WEB-INF\lib（数据库驱动包可以在自己的web项目里找，找到后也复制到该目录下）
  ![img](https://oscimg.oschina.net/oscnet/57d4e953ffcfddf6b1616ea556683699de3.jpg) ![img](https://oscimg.oschina.net/oscnet/d08b7d622adfa23c57d470710a7a610accd.jpg) ![img](https://oscimg.oschina.net/oscnet/e3064c93fe50a3300fb9198e88708e9b14c.jpg)

修改D:\JAVA\solr\apache-tomcat-8.5.42\webapps\solr\WEB-INF\web.xml如下：

```
<!-- 放开注释 修改solrhome地址 -->
    <env-entry>
       <env-entry-name>solr/home</env-entry-name>
       <!-- 这个solrhome地址即上述创建的solrhome文件夹路径 -->
       <env-entry-value>D:\JAVA\solr\solrhome</env-entry-value>
       <env-entry-type>java.lang.String</env-entry-type>
    </env-entry>
	
<!-- 此处省略部分配置代码 -->
	
<!-- 找到最后注释掉这一块的代码，据说是因为此配置限制了对solr资源的访问 -->
<!-- <security-constraint>
    <web-resource-collection>
      <web-resource-name>Disable TRACE</web-resource-name>
      <url-pattern>/</url-pattern>
      <http-method>TRACE</http-method>
    </web-resource-collection>
    <auth-constraint/>
  </security-constraint>
  <security-constraint>
    <web-resource-collection>
      <web-resource-name>Enable everything but TRACE</web-resource-name>
      <url-pattern>/</url-pattern>
      <http-method-omission>TRACE</http-method-omission>
    </web-resource-collection>
  </security-constraint>-->
```

修改tomcat端口号 ....conf\server.xml 为8888

```
    <Connector port="8888" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
```

修改D:\JAVA\solr\apache-tomcat-8.5.42\bin\catalina.bat(最后一行是新添加的)

```
if not "%JSSE_OPTS%" == "" goto gotJsseOpts
set JSSE_OPTS="-Djdk.tls.ephemeralDHKeySize=2048"
:gotJsseOpts
set "JAVA_OPTS=%JAVA_OPTS% %JSSE_OPTS%"
set "JAVA_OPTS=%JAVA_OPTS% -Dsolr.log.dir=D:\JAVA\solr\solrhome\logs"
```

------



将ik分词器解压出来的两个jar放到tomcat-8.5.42\webapps\solr\WEB-INF\lib下，然后配置solrhome\new_core\conf\managed-schema 在文件最后添加:

```
<schema>
<!-- 省略原有代码 -->

 <fieldType name="text_ik" class="solr.TextField" positionIncrementGap="100">    
     <analyzer type="index">
         <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory"  useSmart="false"/>    
         <filter class="solr.LowerCaseFilterFactory"/>    
     </analyzer>    
     <analyzer type="query">
         <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory"  useSmart="true"/>         
         <filter class="solr.SynonymFilterFactory" synonyms="synonyms.txt" ignoreCase="true" expand="true"/>    
         <filter class="solr.LowerCaseFilterFactory"/>    
     </analyzer>    
  </fieldType>
  
</schema>
```

------



启动tomcat，访问 http://localhost:8888/solr/index.html#/ ![img](https://oscimg.oschina.net/oscnet/5b99e7d24279b782a19e110735774958d59.jpg)

用ik解析中文语句，匹配测试如下 ![img](https://oscimg.oschina.net/oscnet/90764d9397eed9d5271cf2ac09e6b90cd9b.jpg)

导入数据库demo表的数据 ![img](https://oscimg.oschina.net/oscnet/054fd82069c408868ae49779584d1df8c82.jpg)

检索测试
![img](https://oscimg.oschina.net/oscnet/d0ff952559a2642ba30e1fc0d63ec8c3b3f.jpg)

------



### 配置pom

```
 		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-solr</artifactId>
		</dependency>
```



```
spring:
  data:
    solr:
      host: http://127.0.0.1:8888/solr
```

**注意:如果你使用的是springboot 2.1.3RELEASE，按照下述操作步骤走完，启动项目会报错，提示： Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true，据说是springboot 2.1.3RELEASE的一个bug，需要在yml中配置一下：**

```
spring:
  main:
    allow-bean-definition-overriding: true #当遇到同样名字的时候，是否允许覆盖注册
```



1.entity：

```
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection="new_core")
public class SolrDemo {

	@org.springframework.data.annotation.Id 
	private String id;
	
	@Field("demoName")
	private String name;
	
	//省略getset
}
```

2.dao:

```
import org.jeecg.modules.demo.test.entity.SolrDemo;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolrDemoRepository extends SolrCrudRepository<SolrDemo, String>{

}
```

3.service接口

```
import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.jeecg.modules.demo.test.entity.SolrDemo;

public interface ISolrService {
	
	void addDemo();
	
	void updatedemo();
	
	void deletedemo();
	
	List<SolrDemo> queryList(String keyword)  throws SolrServerException, IOException;

}
```

4.service实现类

```
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.jeecg.modules.demo.test.entity.SolrDemo;
import org.jeecg.modules.demo.test.mapper.SolrDemoRepository;
import org.jeecg.modules.demo.test.service.ISolrService;
import org.springframework.stereotype.Service;

@Service
public class SolrServiceImpl  implements ISolrService {
	
	@Resource
	private SolrDemoRepository solrDemoRepository;

	@Override
	public void addDemo() {
	//正常情况应该在方法中定义参数将实体传进来,此处demo简单写
		SolrDemo demo = new SolrDemo();
		demo.setId("001");
		demo.setName("俺们都是打酱油的哈哈破");
		solrDemoRepository.save(demo);
	}

	@Override
	public void updatedemo() {
		SolrDemo demo = new SolrDemo();
		demo.setId("001");
		demo.setName("你在搞什么扫话题嘎嘎");
		solrDemoRepository.save(demo);
		
	}

	@Override
	public void deletedemo() {
		solrDemoRepository.deleteById("001");
	}

	@Override
	public List<SolrDemo> queryList(String keyword) throws SolrServerException, IOException {
	/*
	 * 这里有个坑爹的地方，正常情况可以直接在service里直接注入SolrClient，
	 * 但是因为yml中配置的地址是http://127.0.0.1:8888/solr/，导致注入的SolrClient执行下述查询会报错，
	 * 无奈只能这么玩了，若网友有好的解决方法，请在下方留言
	 * 或是调用SolrCrudRepository中的查询方法（我这里需要根据具体字段查询且分页，欢迎留言贴出示例代码）
	 * 个人猜测应该是springboot其进行了二次封装，根据实体上的注解实例化不同的SolrClient？
	 * 
	*/
		String solrUrl = "http://127.0.0.1:8888/solr/new_core";
		SolrClient testSolrClient = new HttpSolrClient.Builder(solrUrl)
	            .withConnectionTimeout(10000)
	            .withSocketTimeout(60000)
	            .build();
		
		SolrQuery query = new SolrQuery();
		
		//设置查询条件
		query.setQuery("demoName:"+keyword);
		
		//按照时间排序
       // query.addSort("create_time", SolrQuery.ORDER.desc);
		
        //开始页
        query.setStart(0);
        //一页显示多少条
        query.setRows(50);
        
		//开启高亮
		//query.setHighlight(true);
		//设置高亮字段
		//query.addHighlightField("demoName");
		//前缀
		//query.setHighlightSimplePre("<font color='red'>");
		//后缀
		//query.setHighlightSimplePost("</font>");
		//执行查找
		QueryResponse response = testSolrClient.query(query);
		
		SolrDocumentList results = response.getResults();
		//获取查询到的数据总量
		long numFound = results.getNumFound();
		if(numFound <= 0) {
			//如果小于0，表示未查询到任何数据，返回null
			return null;
		}else {
		
			List<SolrDemo> list = new ArrayList<SolrDemo>();
			//遍历结果集
			for (SolrDocument doc : results) {
				//得到每条数据的map集合
				Map<String, Object> map = doc.getFieldValueMap();
				//添加到list
				for (String key : map.keySet()) {
					System.out.println("KEY:"+key+",VALUE:"+map.get(key).toString());
				}
				SolrDemo demo = new SolrDemo();
				demo.setId(map.get("id").toString());
				demo.setName(map.get("demoName").toString());
				list.add(demo);
			}
			return list;
		}
	}
	
	
	

}
```

controller层，就是直接写几个请求然后调用一下service即可，代码及后续测试结果就不贴出来了，请自行编写。



1.solr文件说明

| 文件夹    | 描述                                                         |
| --------- | ------------------------------------------------------------ |
| contrib   | solr的一些插件，用于扩展solr的功能                           |
| dist      | 该文件夹下包含build过程中产生的war和jar文件，以及相关的依赖文件 |
| doc       | solr的文档                                                   |
| example   | solr官方提供的一些示例程序，简单介绍下面三个目录solr、multicore、webapps |
| solr      | 该目录是一个包含了默认配置信息的Solr的Core目录               |
| multicore | 该目录包含了在Solr的multicore中设置的多个Core目录            |
| webapps   | 该目录中包括一个solr.war，该war可作为solr的运行实例工程      |

2.清除索引文件 `<delete><query>*:*</query></delete><commit/>` ![img](https://oscimg.oschina.net/oscnet/dbea660741909911ebc90c30043ff0268b2.jpg)

3.solrHome是solr运行的主目录，其下可以创建多个solrCore，solrCore单独对外提供一个搜索服务。

4.solr.install.dir表示solrCore的位置，需要根据实际jar包的位置更改jar包引用路径

5.solr.data.dir表示索引文件存储地址 默认solrCore/data，一开始没有，会自动创建，若要修改，找solrconfig.xml文件 `<dataDir>${solr.data.dir:}</dataDir>`

5.ik分词器的集成（关于忽略词，近义词等等详细配置后续添加）







# 2.Gradle和SpringBoot集成Solr7.4

2019年04月10日 15:04:35 [凡 沙](https://me.csdn.net/qq_43580052) 阅读数 60



版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。本文链接：https://blog.csdn.net/qq_43580052/article/details/89183566

之前按照公司要求，为了提高搜索的准确性、广泛性以及用户体验，研究了一下Solr技术并添加到了自己的项目中。在网上看了好多博客的文章，根据前辈的经验得到了很多指点，但是也跳了不少坑。今天回顾老代码，决定做一下笔记，自己码一篇文章，希望能够帮到奋斗在一线的开发人员。想必各位看官既然能翻到这篇文章，一定是已经对Solr有所了解，所以这里不再阐述，直接上干货，搭建Solr环境并将创建的Solr工程。

## **一、首先列出来搭建Solr服务器所需要的基本工具**

### 1、基本环境 ：JDK1.8(solr7.4默认jdk1.8+)；

2、框架 ：SpringBoot(内置集成Tomcat}；
3、开发工具：IDEA；
4、管理工程：Gradle；
5、全文搜索引擎Solr：solr7.4；
附：
（1）、Solr-7.4.0安装包的百度网盘下载地址：https://pan.baidu.com/s/1uetXcon7BDw1QAIYfurLew 提取码：u8px
（2）、Solr-7.4.0安装包的官网下载地址：http://lucene.apache.org/solr/downloads.html

```
注：在进行接下来的工作之前，一定要确保你的jdk环境以及开发配置可以正常运作。
1
```

### **二、开始搭建Solr服务器**

1、首先将下载的solr-7.4.0.zip进行解压，随便一个文件夹都可以，最好是有意义、易于自己后期查找的文件夹，此处本人就解压到了当前文件夹。最好不要是解压到中文名的文件夹，后期95%会出错。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410143347702.png)

2、在solr7.4/server/solr下创建一个core
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410143641271.png)

3、将solr7.4/dist目录下的solr-dataimporthandler-7.4.0.jar、
solr-dataimporthandler-extras-7.4.0.jar和mysql-connector-java-8.0.11.jar（这个自己弄）复制到/solr-7.4.0/server/solr-webapp/webapp/WEB-INF/lib下

4、将solr-7.4.0/server/solr/configsets/_default下的conf文件复制一份到你新建的core目录下

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410143849639.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410143929909.png)
5、在solr7.4/server/solr/zhanhong_core下创建一个data，data和你复制过来的conf同级
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410144024402.png)
6、在solr7.4/server/solr/zhanhong_core/conf/创建data-config.xml，里面写上如下代码（自己按需求更改mysql相关和document标签下的数据库对应字段）

```
<?xml version="1.0" encoding="UTF-8"?> 
<dataConfig>
    <dataSource name="source1" type="JdbcDataSource"
              driver="com.mysql.jdbc.Driver"              url="jdbc:mysql://localhost:3306/live?useSSL=true&amp;serverTimezone=UTC"
              user="root"
              password="root" />
    <document>
        <entity name="recharge_record" dataSource="source1" pk="id"
            query="SELECT * FROM recharge_record">
            <field column='id' name='id' />
            <field column='uid' name='uid' />
            <field column='tradeNo' name='tradeNo' />
            <field column='productId' name='productId' />
            <field column='money' name='money' />
            <field column='payType' name='payType' />
            <field column='payStatus' name='payStatus' />
            <field column='remark' name='remark' />
            <field column='createTime' name='createTime' />
            <field column='confirmTime' name='confirmTime' />
        </entity>
    </document>
</dataConfig>
12345678910111213141516171819202122
```

7、修改solr7.4/server/solr/zhanhong_core/conf/下的solrconfig.xml，在
标签上添加

```
<requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">
　　     <lst name="defaults">
　　        <str name="config">data-config.xml</str>
　　     </lst>
</requestHandler>
12345
```

结果如下图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019041014430444.png)
8、修改solrconfig.xml下的如图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410144350297.png)
9、在solr7.4/server/solr/zhanhong_core/conf/下的managed-schema文件中添加data-config.xml里面所添加的字段
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410144425364.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzNTgwMDUy,size_16,color_FFFFFF,t_70)

10、win+r启动cmd，进入solr7.4/bin目录下执行solr start -p 8080（执行solr start，默认是8983端口），solr stop -all停止solr运行

11、进入浏览器输入localhost:8080,就会进入solr管理界面
①：点击Core Admin
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410144544395.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzNTgwMDUy,size_16,color_FFFFFF,t_70)
②：上图所述步骤完成后点击Core Selector的一个下拉框，就可以看到红色框中的东西
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410144610857.png)
③：点击Dataimport，完成红框中操作，如果使用solrj代码实现，此操作可忽略
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410144638292.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzNTgwMDUy,size_16,color_FFFFFF,t_70)
12、使用solrj，打开idea创建gradle工程，添加solrj7.4jar

```
org.apache.solr:solr-solrj:7.4.0
1
```

**三、代码来一波**
①：controller层代码如下

```
@Controller
public class solrj {

    @Autowired
    SolrjService solrjService;

    private static final String solrUrl = "http://localhost:8080/solr/zhanhong_core";

    //创建solrClient同时指定超时时间，不指定走默认配置
    private static HttpSolrClient client = new HttpSolrClient.Builder(solrUrl)
            .withConnectionTimeout(10000)
            .withSocketTimeout(60000).build();

    @RequestMapping("/query")
    @ResponseBody
    public List<Object> querySolr() throws Exception {
        //封装查询参数
        SolrQuery query = new SolrQuery("*:*");
        //添加回显内容
        query.addField("id");
        query.addField("tradeNo");
        query.addField("productId");
        query.addField("money");
        query.addField("payType");
        query.addField("payStatus");
        query.addField("remark");
        query.addField("createTime");
        query.addField("confirmTime");
        query.setRows(20);
        //执行查询返回QueryResponse
        QueryResponse response = client.query(query);
        //获取doc文档
        SolrDocumentList documents = response.getResults();
        List<Object> list = new ArrayList<>();
        for (SolrDocument solrDocument : documents) {
           list.add(solrDocument);
        }
        client.close();
        return list;
    }

    @RequestMapping("/add")
    public void solrAdd() throws Exception {
        //创建文档doc
        List<RechargeRecord> list = solrjService.solrAdd();
        for (RechargeRecord rechargeRecord:list) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id",rechargeRecord.getId() );
            doc.addField("uid",rechargeRecord.getUid() );
            doc.addField("tradeNo",rechargeRecord.getTradeNo() );
            doc.addField("productId",rechargeRecord.getProductId());
            doc.addField("money",rechargeRecord.getMoney() );
            doc.addField("payType",(rechargeRecord.getPayType()==1?"支付宝":(rechargeRecord.getPayType()==2?"微信":(rechargeRecord.getPayType()==3?"银联":"出现问题,请稍后"))) );
            doc.addField("payStatus",(rechargeRecord.getPayStatus()==1?"未支付":(rechargeRecord.getPayStatus()==2?"支付成功":(rechargeRecord.getPayStatus()==3?"支付失败":"出现问题,请稍后"))) );
            doc.addField("remark",rechargeRecord.getRemark() );
            doc.addField("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( rechargeRecord.getCreateTime()));
            doc.addField("confirmTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rechargeRecord.getConfirmTime()));
            //添加到client
            UpdateResponse updateResponse = client.add(doc);
        }
        //索引文档必须commit
        client.commit();
        //client.close();
    }

    @RequestMapping("/delete")
    public void solrDelete()throws Exception{
        //1:通过id删除
        //client.deleteById("63");
        //2:通过文档存在id list删除
        SolrQuery query = new SolrQuery("id:*");
        query.addField("id");
        QueryResponse response = client.query(query);
        SolrDocumentList documents = response.getResults();
        List<String> ids = new ArrayList<>();
        for (SolrDocument solrDocument:documents) {
            ids.add(solrDocument.get("id").toString());
        }
        client.deleteById(ids);
        //3:通过查询信息删除
        /*client.deleteByQuery("id:63");*/
        client.commit();
        client.close();
}
}
12345678910111213141516171819202122232425262728293031323334353637383940414243444546474849505152535455565758596061626364656667686970717273747576777879808182838485
```

②：service和mapper层代码只进行简单查询就不写了。
③：点击Query，结果如下
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190410145337784.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQzNTgwMDUy,size_16,color_FFFFFF,t_70)
**四、总结**
	1、关于Solr服务器的搭建，以及导入数据、简单的查询，本文已经介绍完了。目前依然在研究这个东西，在以后的文章中会陆续更新关于Solr的技术点，也希望各位能够多多指点，共同进步。

2、Solr服务器搭建过程的难点、繁琐点：各个目录下的jar包的移动，以及各个目录下的各种xml文件的配置，最主要的是各层级目录要关联正确，大家多练习几次，相信都可以熟练地进行搭建部署！

3、关于Solr集群搭建后续这边会慢慢给大家完善！！！
案例代码：https://gitee.com/fansha/solr.git
参考：https://www.cnblogs.com/frankdeng/p/9615856.html

4. 学习地址：

   [学习地址]: https://www.w3cschool.cn/solr_doc/solr_doc-t3642fkr.html

   









# springboot整合solr

2018年04月09日 09:17:27 [游鱼丶](https://me.csdn.net/zqw997256964) 阅读数 2190



版权声明：本文为博主原创文章，遵循[ CC 4.0 by-sa ](http://creativecommons.org/licenses/by-sa/4.0/)版权协议，转载请附上原文出处链接和本声明。本文链接：https://blog.csdn.net/zqw997256964/article/details/79712373

一、本文将solr安装在linux上。首先先安装好jdk和tomcat。 
配置环境：jdk8，tomcat8.5，solr7.2.1.。

二、复制Solr文件夹中的一些文件到apache-tomcat下: 
1）将 solr 压缩包中 solr\server\solr-webapp\文件夹下有个webapp文件夹，将之复制到tomcat\webapps\目录下，文件夹名改成solr(任意) ；

```
cp -R /usr/local/tomcat/solr-7.2.1/server/solr-webapp/webapp /usr/local/tomcat/apache-tomcat-8.5.29/webapps/1
```

2）将 solr 压缩包中 solr\server\lib\ext 中的 jar 全部复制到 Tomcat\ webapps\solr\WEB-INF\lib 目录中；

```
cp -R /usr/local/tomcat/solr-7.2.1/server/solr-webapp/webapp /usr/local/tomcat/apache-tomcat-8.5.29/webapps/

123
```

3）将solr压缩包中solr/server/lib/metrics* 开头的jar全部复制到 Tomcat\ webapps\solr\WEB-INF\lib 目录中；

```
cp -R /usr/local/tomcat/solr-7.2.1/server/lib/metrics*.* /usr/local/tomcat/apache-tomcat-8.5.29/webapps/solr/WEB-INF/lib/
12
```

4）将solr压缩包中solr/server/lib/solr-dataimporthandler-* 开头的jar全部复制到 Tomcat\ webapps\solr\WEB-INF\lib 目录中；

```
cp -R /usr/local/tomcat/solr-7.2.1/server/lib/solr-dataimporthandler-* /usr/local/tomcat/apache-tomcat-8.5.29/webapps/solr/WEB-INF/lib/1
```

5）在Tomcat\ webapps\solr\WEB-INF\下建立classes目录，并将solr/server/resources/log4j.properties文件复制其中；

```
cp -R /usr/local/tomcat/solr-7.2.1/server/resources/log4j.properties  /usr/local/tomcat/apache-tomcat-8.5.29/webapps/solr/WEB-INF/classes/
12
```

6）在tomcat目录下建立solrhome目录（也可以放在其它目录中）

```
mkdir solrhome1
```

7）复制solr/server/solr/* 所有文件到tomcat/solrhome目录，用到创建solr的core时使用。

```
cp -R /usr/local/tomcat/solr-7.2.1/server/solr/* /usr/local/tomcat/apache-tomcat-8.5.29/solrhome/1
```

三、安装完成之后启动tomcat，即可运行sorl。输入路径地址：

```
http://192.168.1.130:8089/solr/index.html1
```

ip可根据实际情况填写。

四、配置solr：

编辑web.xml文件：

```
[root@localhost down]# vi /down/apache-tomcat-8.5.12/webapps/solr/WEB-INF/web.xml1
```

1）配置solr下core路径，找如下配置内容（初始状态下该内容是被注释掉的）：

```
<env-entry>
       <env-entry-name>solr/home</env-entry-name>
       <env-entry-value>/down/apache-tomcat-8.5.12/solrhome</env-entry-value> //将路径指向我们创建的solrhome目录。
       <env-entry-type>java.lang.String</env-entry-type>
    </env-entry>12345
```

2）配置访问权限：找到如下内容，并注释掉：

```
<!--
  <security-constraint>
    <web-resource-collection>
      <web-resource-name>Disable TRACE</web-resource-name>
      <url-pattern>/</url-pattern>
      <http-method>TRACE</http-method>
    </web-resource-collection>
    <auth-constraint/>
  </security-constraint>
  <security-constraint>
    <web-resource-collection>
      <web-resource-name>Enable everything but TRACE</web-resource-name>
      <url-pattern>/</url-pattern>
      <http-method-omission>TRACE</http-method-omission>
    </web-resource-collection>
  </security-constraint>
-->1234567891011121314151617
```

至此solr的配置工作完成.

五、创建core： 
1、首先在solrhome中创建mycore目录; 
2、复制solr-6.5.0\example\example-DIH\solr\solr下的所有文件到/down/apache-tomcat-8.5.12/solrhome/mycore目录下： 
3、重新启动tomcat; 
4、此时在浏览器输入http://localhost:8080/solr/index.html即可出现Solr的管理界面，即可看到我们刚才的mycore。

六、配置IKAnalyzer的中文分词： 
1、下载IKAnalyzer，解压后会有五个文件

```
ext.dic  IKAnalyzer.cfg.xml  solr-analyzer-ik-5.1.0.jar ik-analyzer-solr5-5.x.jar  stopword.dic 1
```

ext.dic为扩展字典,stopword.dic为停止词字典，IKAnalyzer.cfg.xml为配置文件，solr-analyzer-ik-5.1.0.jar ik-analyzer-solr5-5.x.jar为分词jar包。

2、将文件夹下的IKAnalyzer.cfg.xml , ext.dic和stopword.dic 三个文件 复制到/webapps/solr/WEB-INF/classes 目录下，并修改IKAnalyzer.cfg.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE properties SYSTEM "http://java.sun.com/dtd/properties.dtd">
<properties>
        <comment>IK Analyzer 扩展配置</comment>
        <!--用户可以在这里配置自己的扩展字典 -->
        <entry key="ext_dict">ext.dic;</entry>

        <!--用户可以在这里配置自己的扩展停止词字典-->
        <entry key="ext_stopwords">stopword.dic;</entry>

</properties>1234567891011
```

3、在ext.dic 里增加自己的扩展词典，例如，唯品会 聚美优品

4、复制solr-analyzer-ik-5.1.0.jar ik-analyzer-solr5-5.x.jar到/down/apache-tomcat-8.5.12/webapps/solr/WEB-INF/lib/目录下。

5、在 solrhome\mycore\conf\managed-schema 文件前增加如下配置

```
<!-- 我添加的IK分词 -->
<fieldType name="text_ik" class="solr.TextField">
        <analyzer type="index">
            <tokenizer class="org.apache.lucene.analysis.ik.IKTokenizerFactory" useSmart="true"/>
        </analyzer>
        <analyzer type="query">
            <tokenizer class="org.apache.lucene.analysis.ik.IKTokenizerFactory" useSmart="true"/>
        </analyzer>
</fieldType>123456789
```

注意: 记得将stopword.dic，ext.dic的编码方式为UTF-8 无BOM的编码方式。

七、solr与mysql集成索引：

首先在 solrconfig.xml 的 《requestHandler name=”/select” class=”solr.SearchHandler”> 之上添加

```
<requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">  
　     <lst name="defaults">  
　        <str name="config">data-config.xml</str>  
　     </lst>  
</requestHandler>12345
```

然后在conf下新建data-config.xml文件。里面内容如下：

```
<dataConfig>
    <dataSource name="source1" type="JdbcDataSource" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://数据库ip:3306/***" user="root" password="123456" />
    <document>
        <entity dataSource="source1" name="Goods" pk="id" query="select goods_name,seo_description,seo_keywords,goods_price,goods_main_photo_id from goods"
            deltaImportQuery="select * from user where id='${dih.delta.id}'"
            deltaQuery="select id from user where updateTime> '${dataimporter.last_index_time}'">
            <field column="goods_name" name="goodsName" />
            <field column="seo_description" name="seoDescription" />
            <field column="seo_keywords" name="seoKeywords" />
            <field column="goods_price" name="goodsPrice" />
            <field column="goods_main_photo_id" name="goodsMainPhotoId" />
            <field column="update_time" name="updateTime" />
        </entity>
    </document>
</dataConfig>123456789101112131415
```

dataSource是数据库数据源。Entity就是一张表对应的实体，pk是主键，query是查询语句。Field对应一个字段，column是数据库里的column名，后面的name属性对应着Solr的Filed的名字。其中solrdata是数据库名，goods是表名。 
其中deltaQuery是增量索引，原理是从数据库中根据deltaQuery指定的SQL语句查询出所有需要增量导入的数据的ID号。然后根据deltaImportQuery指定的SQL语句返回所有这些ID的数据，即为这次增量导入所要处理的数据。核心思想是：通过内置变量“dih.delta.id”和“dih.delta.id”和“{dataimporter.last_index_time}”来记录本次要索引的id和最近一次索引的时间。

然后把mysql所需的jar包和solr-6.2.0\dist下的solr-dataimporthandler-6.2.0.jar和solr-dataimporthandler-extras-6.2.0.jar都复制到项目WEB-INF\lib下。

启动Tomcat，输入http://localhost:8080/solr/index.html按如下选择， 
![solr导入索引](https://img-blog.csdn.net/20180331122125407?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3pxdzk5NzI1Njk2NA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

八、查询参数说明： 
1、常用

q - 查询字符串，这个是必须的。如果查询所有*:* ，根据指定字段查询（Name:张三 AND Address:北京）

fq - （filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，例如：q=Name:张三&fq=createDate:[2014-06-18 TO 2015-12-18],找关键字”张三”，并且CreateDate是查询2014-06-18到2015-12-18之间的数据

fl - 指定返回那些字段内容，用逗号或空格分隔多个。

start - 返回第一条记录在完整找到结果中的偏移位置，0开始，一般分页用。

rows - 指定返回结果最多有多少条记录，配合start来实现分页。

sort - 排序，格式：sort=《field name>+《desc|asc>[,《field name>+《desc|asc>]… 。示例：（score desc, price asc）表示先 “score” 降序, 再 “price” 升序，默认是相关性降序。

wt - (writer type)指定输出格式，可以有 xml, json, PHP, phps。

fl表示索引显示那些field( *表示所有field,如果想查询指定字段用逗号或空格隔开（如：Name,SKU,ShortDescription或Name SKU ShortDescription【注：字段是严格区分大小写的】）)

q.op 表示q 中 查询语句的 各条件的逻辑操作 AND(与) OR(或)

hl 是否高亮 ,如hl=true

hl.fl 高亮field ,hl.fl=Name,SKU

hl.snippets :默认是1,这里设置为3个片段

hl.simple.pre 高亮前面的格式

hl.simple.post 高亮后面的格式

facet 是否启动统计

facet.field 统计field

2、 Solr运算符

1. “:” 指定字段查指定值，如返回所有值*:*

2. “?” 表示单个任意字符的通配

3. “*” 表示多个任意字符的通配（不能在检索的项开始使用*或者?符号）

4. “~” 表示模糊检索，如检索拼写类似于”roam”的项这样写：roam~将找到形如foam和roams的单词；roam~0.8，检索返回相似度在0.8以上的记录。

5. 邻近检索，如检索相隔10个单词的”apache”和”jakarta”，”jakarta apache”~10

6. “^” 控制相关度检索，如检索jakarta apache，同时希望去让”jakarta”的相关度更加好，那么在其后加上”^”符号和增量值，即jakarta^4 apache

7. 布尔操作符AND、||

8. 布尔操作符OR、&&

9. 布尔操作符NOT、!、- （排除操作符不能单独与项使用构成查询）

10. “+” 存在操作符，要求符号”+”后的项必须在文档相应的域中存在

11. ( ) 用于构成子查询

12. [] 包含范围检索，如检索某时间段记录，包含头尾，date:[200707 TO 200710]

13. {} 不包含范围检索，如检索某时间段记录，不包含头尾 
    date:{200707 TO 200710}

14. / 转义操作符，特殊字符包括+ - && || ! ( ) { } [ ] ^ ” ~ * ? : /

    注：①“+”和”-“表示对单个查询单元的修饰，and 、or 、 not 是对两个查询单元是否做交集或者做差集还是取反的操作的符号

    　　 比如:AB:china +AB:america,表示的是AB:china忽略不计可有可无，必须满足第二个条件才是对的,而不是你所认为的必须满足这两个搜索条件

    　　 如果输入:AB:china AND AB:america,解析出来的结果是两个条件同时满足，即+AB:china AND +AB:america或+AB:china +AB:america

    　　总而言之，查询语法： 修饰符 字段名:查询关键词 AND/OR/NOT 修饰符 字段名:查询关键词

3、 Solr查询语法

1.最普通的查询，比如查询姓张的人（ Name:张）,如果是精准性搜索相当于SQL SERVER中的LIKE搜索这需要带引号（”“）,比如查询含有北京的（Address:”北京”）

2.多条件查询，注：如果是针对单个字段进行搜索的可以用（Name:搜索条件加运算符(OR、AND、NOT) Name：搜索条件）,比如模糊查询（ Name:张 OR Name:李）单个字段多条件搜索不建议这样写，一般建议是在单个字段里进行条件筛选，如（Name:张 OR 李），多个字段查询（Name:张 + Address:北京 ）

3.排序，比如根据姓名升序（Name asc）,降序（Name desc）

4.查询结果匹配

一般情况下solr默认是进行拆分匹配查询的，如：“苏小小”拆成“苏”，“小”，“小”等。但是如果要进行完全匹配 “苏小小” 可以将关键词用双引号括起来如下：

例如 ：

http://localhost:8081/solr/select/?q=name:”苏小小”&version=2.2&start=0&rows=10&indent=on&sort=cDate desc&hl=true&hl.fl=content

注意：如果在搜索的目标上有一句话中包含这个关键字，那么这段话也会被搜索到，如：“很久很久以前苏小小就是很出名了”。千万不要以为只是关键字的内容才能搜索到。

九、项目代码： 
application.yml 中加上如上配置：

```java
#配置sorl
spring:
  data:
    solr:
      host: http://192.168.1.130:8089/solr/mycore12345
package com.tj.dr.service.impl;

...
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private SolrClient solrClient;

    @Reference(version = "1.0")
    private UserInterfaces userInterfaces;

    @Reference(version = "1.0")
    private ConfigInterface configInterface;

    /**
     * 更新索引库所有索引
     */
    @Override
    public void importAllIndex() {
        try {
            List<SearchGoodsDto> searchGoodsDtos = searchMapper.selectByGoodsList();

            List<SolrInputDocument> documents = new ArrayList<>();
            for (SearchGoodsDto goods : searchGoodsDtos) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", goods.getId());
                document.addField("goodsName", goods.getGoodsName());
                String price = goods.getGoodsPrice().toString();
                document.addField("goodsPrice", price);
                String url = null;
                if (!Common.isEmpty(goods.getGoodsMainPhotoId())) {
                    RedpigmallAccessory pic = userInterfaces.getRedpigmallAccessoryById(Long.valueOf(goods.getGoodsMainPhotoId()));
                    url = configInterface.getSysConfig().getDetail().getImagewebserver() + pic.getPath() + "/"
                            + pic.getName();
                }
                document.addField("goodsMainPhotoId", url);
                documents.add(document);
            }

            solrClient.add(documents);

            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询商品
     *
     * @param name
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageUtils query(String name, int currentPage, int pageSize) throws IOException, SolrServerException {

        SolrQuery params = new SolrQuery();
        //查询条件
        params.set("q", "goodsName:" + name);
        //这里的分页和mysql分页一样
        params.set("start", (currentPage - 1) * pageSize);
        params.set("rows", pageSize);
        params.set("wt", "json");
        QueryResponse query = solrClient.query(params);
        //查询结果
        SolrDocumentList results = query.getResults();

        List<GoodsClassNameVo> list = new ArrayList<>();
        for (SolrDocument result : results) {
            GoodsClassNameVo searchGoodsDto = new GoodsClassNameVo();
            searchGoodsDto.setId(Long.valueOf(result.get("id").toString()));
            searchGoodsDto.setGoodsName(String.valueOf(result.get("goodsName")));
            BigDecimal goodsPrice = new BigDecimal(result.get("goodsPrice").toString());
            searchGoodsDto.setGoodsPrice(goodsPrice);
            if (null!=result.get("goodsMainPhotoId")){
                searchGoodsDto.setIcon(result.get("goodsMainPhotoId").toString());
            }

            list.add(searchGoodsDto);
        }
        //搜索总数
        long found = results.getNumFound();
        //搜索开始
        long start = results.getStart();

        PageUtils<GoodsClassNameVo> pageUtils = new PageUtils<>();
        pageUtils.setPageNum(currentPage);
        pageUtils.setPageSize(pageSize);
        pageUtils.setTotalNum(Integer.valueOf(String.valueOf(found)));
        pageUtils.setPageCount(Integer.valueOf(String.valueOf(found / pageSize == 0 ? found / pageSize : found / pageSize + 1)));
        pageUtils.setIsMore(found - start > pageSize ? 1 : 0);
        pageUtils.setItems(list);

        return pageUtils;
    }

    /**
     * @param
     * @author: ZhongQiuwu
     * Description:添加单个商品索引
     * @Date: 13:54 2018/3/31
     */
    @Override
    public void addGoodsIndex(SearchGoodsDto searchGoodsDto) throws IOException, SolrServerException {
        RedpigmallAccessory pic = userInterfaces.getRedpigmallAccessoryById(Long.valueOf(searchGoodsDto.getGoodsMainPhotoId()));
        String url = null;
        if (pic != null) {
            url = configInterface.getSysConfig().getDetail().getImagewebserver() + pic.getPath() + "/"
                    + pic.getName();
        }
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", searchGoodsDto.getId());
        document.addField("goodsName", searchGoodsDto.getGoodsName());
        String price = searchGoodsDto.getGoodsPrice().toString();
        document.addField("goodsPrice", price);
        document.addField("goodsMainPhotoId", url);
        solrClient.add(document);
        solrClient.commit();
    }

    /**
     * @param
     * @param ids
     * @author: ZhongQiuwu
     * Description: 根据id批量删除
     * @Date: 13:59 2018/3/31
     */
    @Override
    public void deleteGoodsIndex(String[] ids) throws IOException, SolrServerException {
        List list = new ArrayList<>();
        for (String id : ids) {
            list.add(id);
        }
        solrClient.deleteById(list);
        solrClient.commit();
    }
}
```