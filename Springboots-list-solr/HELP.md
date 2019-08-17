## springboot中使用solr



```xml
 		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-solr</artifactId>
		</dependency>
```



```yml
spring:
  data:
    solr:
      host: http://127.0.0.1:8888/solr/core
```

**注意:如果你使用的是springboot 2.1.3RELEASE，按照下述操作步骤走完，启动项目会报错，提示： Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true，据说是springboot 2.1.3RELEASE的一个bug，需要在yml中配置一下：**

```yml
spring:
  main:
    allow-bean-definition-overriding: true #当遇到同样名字的时候，是否允许覆盖注册
```

-----------------





##简单使用1



1.entity：

```java
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

```java
import org.jeecg.modules.demo.test.entity.SolrDemo;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolrDemoRepository extends SolrCrudRepository<SolrDemo, String>{

}
```



## solr实时更新操作2
```java
@SpringBootApplication
@EnableScheduling
public class SpringbootSolrApplication {

private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private RestTemplateBuilder builder;
    
    @Autowired
    private RestTemplate restTemplate;
    
    // 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSolrApplication.class, args);
    }
    
    //每五秒执行一次
    @Scheduled(cron = "0/5 * * * * *")
    public void updateSolr() {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("command", "full-import");
        postParameters.add("verbose", "false");
        postParameters.add("clean", "true");
        postParameters.add("commit", "true");
        postParameters.add("core", "ik_core");
        postParameters.add("name", "dataimport");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity <MultiValueMap <String, Object>> r = new HttpEntity<>(postParameters, headers);
        String time = String.valueOf(new Date().getTime());
        String url = "http://192.168.0.197:8983/solr/ik_core/dataimport?_=" + time + "&indent=on&wt=json";
        String responseMessage = restTemplate.postForObject(url, r, String.class);
    
        logger.info("更新solr索引：返回值：{}", responseMessage);
    }
}
```


测试查询（增加了高亮显示）
```java
@Test
    public void test() throws IOException, SolrServerException {

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("q", "title:*");
        solrQuery.set("start", 0);
        solrQuery.set("rows", 20);
    
        //======高亮设置===
        //开启高亮
        solrQuery.setHighlight(true);
        //高亮域
        solrQuery.addHighlightField("title");
        //前缀
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        //后缀
        solrQuery.setHighlightSimplePost("</span>");
    
        QueryResponse response = solrClient.query(solrQuery);
        SolrDocumentList results = response.getResults();
    
        System.out.println("查询内容:" + solrQuery);
        System.out.println("文档数量：" + results.getNumFound());
        System.out.println("查询花费时间:" + response.getQTime());
    
        //获取高亮信息
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
    
        for (SolrDocument solrDocument :results) {
            System.out.println(solrDocument);
            System.out.println(solrDocument.getFieldValue("cover"));
            System.out.println(solrDocument.getFieldValue("service_area"));
    
            //输出高亮
            Map<String, List<String>> map = highlighting.get(solrDocument.get("id"));
            List<String> list = map.get("title");
            if(list != null && list.size() > 0){
                System.out.println(list.get(0));
            }
        }
    
    }
```
 ———————————————— 
版权声明：本文为CSDN博主「Radom7」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_26641781/article/details/81774011