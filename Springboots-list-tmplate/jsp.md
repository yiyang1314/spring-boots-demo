# JSP 标准标签库（JSTL）

JSP标准标签库（JSTL）是一个JSP标签集合，它封装了JSP应用的通用核心功能。

JSTL支持通用的、结构化的任务，比如迭代，条件判断，XML文档操作，国际化标签，SQL标签。 除了这些，它还提供了一个框架来使用集成JSTL的自定义标签。

根据JSTL标签所提供的功能，可以将其分为5个类别。

- **核心标签**
- **格式化标签**
- **SQL 标签**
- **XML 标签**
- **JSTL 函数**

## 引入jstl标签

> 使用**核心标签库（最常用）**的taglib指令格式如下：
> <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

>
> 使用格式标签库的taglib指令格式如下：
> <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

>
> 使用SQL标签库的taglib指令格式如下：
> <%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

>
> 使用XML标签库的taglib指令格式如下：
> <%@ taglib prefix="xml" uri="http://java.sun.com/jsp/jstl/xml"%>

>
> 使用函数标签库的taglib指令格式如下：
> <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

#### 1.out输出语句：

```
<c:out value = "xxx"/>
如果要用el表达式，应该是这样子单引号:
<c:out value = '${param.name}'/>
```

#### 2.set设置:

```
a.初始化或者修改bean里的值
<c:set value = "要设置什么值" taget = "设置的目标对象" property = "子参数"/>
如下修改bean：
<c:set target = '${beanName}' property="name" value = "senninha"></c:set>
如果没有新建，如果有就修改

b.修改request,session.application attribute。
<c:set var = "要设置是attribute名称" value = "值" scope = "域"/>
<c:set var = "name" value = "senninha" scope = "request"/>
注意，并不能设置parameter的值。
```

#### 3.remove 使用和set相似。

#### 4.if

```
<c:if test = "要判断的逻辑语句 只能是el表达式子" var = "判断结果保存在这里">
    <c:out value = "逻辑判断:${tem}">
    如果true，则运行这里，否则不行
    可以在这里写html语句
    <h1>我是h1</h1>
</c:if>
```

#### 5.choose(catch)

```
<c:choose >
    <c:when test = "">
        statement
    </when>
    
    <c:otherwise test = "">
        statement
    </when>
</c:choose>
相当于多重选择语句if else
```

#### 6.forToken(StringTokenizer)

```
<c:forToken var = "处理后保存在这里" items = "被处理的对象" delims = "分隔符||多个用这个符号隔开">
</c:forToken>
```

#### 7.forEach循环

```
a.简单循环输出数字
  <c:forEach var = "tem" begin = "1" end = "10" step = "2" varStatus = "statusTem">
    <c:out value = "${statusTem.first.last.count.index"}//输出循环的次数，计数以及长度
 
b.循环输出数组集合等
  <c:forEach var = "item" items = "集合或者数组" >
  <c:out value = "${item}"/>
  </c>
```

#### 8.catch

```
<c: var = exception对象>
    可能出问题的语句
</c:var>
然后：
<c:out value = "${exception.message.cause"/>
```

#### 9.url(点击时候保持session，并且可以在跳转的时候保存request参数)

```
a.首先生成url字符串:
    <c:url var = "url" value = "se.jsp">
        //这里可以设置表单值
        <c:param name = "ss" value = ""/>
        //或者attributte
        <c：set var = "ss" value = "senn" scope = "session"/>
    </c:url>

b.然后使用即可：
    <a href = "${url}"/>
```

#### 10.import

```
1.把其他服务器网页或者自己的服务器网页引入
    <c:import url="http://163.com"></c:import>
2.把其他网页的源码引入作为输出：
    <c:import url = "http://163.com" var = "保存在这里">
    </c:import>
    
    然后就可以在网页上显示这些源码了
    <c:out value = "${tem}"/>
```

#### 11.redirect

```
能够在cookie不起作用时自动通过urlWriting保存session
<c:redirect url = "${9<c:url>生成的对象}"
```

####  12.jstl 库

```xml
	<taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/fmt</taglib-uri>
    <taglib-location>/WEB-INF/fmt.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/fmt-rt</taglib-uri>
    <taglib-location>/WEB-INF/fmt-rt.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/core</taglib-uri>
    <taglib-location>/WEB-INF/c.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/core-rt</taglib-uri>
    <taglib-location>/WEB-INF/c-rt.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/sql</taglib-uri>
    <taglib-location>/WEB-INF/sql.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/sql-rt</taglib-uri>
    <taglib-location>/WEB-INF/sql-rt.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/x</taglib-uri>
    <taglib-location>/WEB-INF/x.tld</taglib-location>
    </taglib>
    <taglib>
    <taglib-uri>http://java.sun.com/jsp/jstl/x-rt</taglib-uri>
    <taglib-location>/WEB-INF/x-rt.tld</taglib-location>
    </taglib>
```





## 核心标签

核心标签是最常用的 JSTL标签。引用核心标签库的语法如下：

```
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
```

| 标签                                                       | 描述                                                         |
| :--------------------------------------------------------- | :----------------------------------------------------------- |
| [](https://www.runoob.com/jsp/jstl-core-out-tag.html)      | 用于在JSP中显示数据，就像<%= ... >                           |
| [](https://www.runoob.com/jsp/jstl-core-set-tag.html)      | 用于保存数据                                                 |
| [](https://www.runoob.com/jsp/jstl-core-remove-tag.html)   | 用于删除数据                                                 |
| [](https://www.runoob.com/jsp/jstl-core-catch-tag.html)    | 用来处理产生错误的异常状况，并且将错误信息储存起来           |
| [](https://www.runoob.com/jsp/jstl-core-if-tag.html)       | 与我们在一般程序中用的if一样                                 |
| [](https://www.runoob.com/jsp/jstl-core-choose-tag.html)   | 本身只当做<c:when>和<c:otherwise>的父标签                    |
| [](https://www.runoob.com/jsp/jstl-core-choose-tag.html)   | <c:choose>的子标签，用来判断条件是否成立                     |
| [](https://www.runoob.com/jsp/jstl-core-choose-tag.html)   | <c:choose>的子标签，接在<c:when>标签后，当<c:when>标签判断为false时被执行 |
| [](https://www.runoob.com/jsp/jstl-core-import-tag.html)   | 检索一个绝对或相对 URL，然后将其内容暴露给页面               |
| [](https://www.runoob.com/jsp/jstl-core-foreach-tag.html)  | 基础迭代标签，接受多种集合类型                               |
| [](https://www.runoob.com/jsp/jstl-core-foreach-tag.html)  | 根据指定的分隔符来分隔内容并迭代输出                         |
| [](https://www.runoob.com/jsp/jstl-core-param-tag.html)    | 用来给包含或重定向的页面传递参数                             |
| [](https://www.runoob.com/jsp/jstl-core-redirect-tag.html) | 重定向至一个新的URL.                                         |
| [](https://www.runoob.com/jsp/jstl-core-url-tag.html)      | 使用可选的查询参数来创造一个URL                              |

------

## 格式化标签

JSTL格式化标签用来格式化并输出文本、日期、时间、数字。引用格式化标签库的语法如下：

```
<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>
```

|                             标签                             | 描述                                     |
| :----------------------------------------------------------: | :--------------------------------------- |
| [](https://www.runoob.com/jsp/jstl-format-formatnumber-tag.html) | 使用指定的格式或精度格式化数字           |
| [](https://www.runoob.com/jsp/jstl-format-parsenumber-tag.html) | 解析一个代表着数字，货币或百分比的字符串 |
| [](https://www.runoob.com/jsp/jstl-format-formatdate-tag.html) | 使用指定的风格或模式格式化日期和时间     |
| [](https://www.runoob.com/jsp/jstl-format-parsedate-tag.html) | 解析一个代表着日期或时间的字符串         |
|  [](https://www.runoob.com/jsp/jstl-format-bundle-tag.html)  | 绑定资源                                 |
| [](https://www.runoob.com/jsp/jstl-format-setlocale-tag.html) | 指定地区                                 |
| [](https://www.runoob.com/jsp/jstl-format-setbundle-tag.html) | 绑定资源                                 |
| [](https://www.runoob.com/jsp/jstl-format-timezone-tag.html) | 指定时区                                 |
| [](https://www.runoob.com/jsp/jstl-format-settimezone-tag.html) | 指定时区                                 |
| [](https://www.runoob.com/jsp/jstl-format-message-tag.html)  | 显示资源配置文件信息                     |
| [](https://www.runoob.com/jsp/jstl-format-requestencoding-tag.html) | 设置request的字符编码                    |

------

## SQL标签

JSTL SQL标签库提供了与关系型数据库（Oracle，MySQL，SQL Server等等）进行交互的标签。引用SQL标签库的语法如下：

```
<%@ taglib prefix="sql" 
           uri="http://java.sun.com/jsp/jstl/sql" %>
```

| 标签                                                         | 描述                                                         |
| :----------------------------------------------------------- | :----------------------------------------------------------- |
| [](https://www.runoob.com/jsp/jstl-sql-setdatasource-tag.html) | 指定数据源                                                   |
| [](https://www.runoob.com/jsp/jstl-sql-query-tag.html)       | 运行SQL查询语句                                              |
| [](https://www.runoob.com/jsp/jstl-sql-update-tag.html)      | 运行SQL更新语句                                              |
| [](https://www.runoob.com/jsp/jstl-sql-param-tag.html)       | 将SQL语句中的参数设为指定值                                  |
| [](https://www.runoob.com/jsp/jstl-sql-dateparam-tag.html)   | 将SQL语句中的日期参数设为指定的java.util.Date 对象值         |
| [](https://www.runoob.com/jsp/jstl-sql-transaction-tag.html) | 在共享数据库连接中提供嵌套的数据库行为元素，将所有语句以一个事务的形式来运行 |

------

## XML 标签

JSTL XML标签库提供了创建和操作XML文档的标签。引用XML标签库的语法如下：

```
<%@ taglib prefix="x" 
           uri="http://java.sun.com/jsp/jstl/xml" %>
```

在使用xml标签前，你必须将XML 和 XPath 的相关包拷贝至你的<Tomcat 安装目录>\lib下:

- XercesImpl.jar

  下载地址： http://www.apache.org/dist/xerces/j/

- xalan.jar

  下载地址： http://xml.apache.org/xalan-j/index.html

| 标签                                                       | 描述                                                      |
| :--------------------------------------------------------- | :-------------------------------------------------------- |
| [](https://www.runoob.com/jsp/jstl-xml-out-tag.html)       | 与<%= ... >,类似，不过只用于XPath表达式                   |
| [](https://www.runoob.com/jsp/jstl-xml-parse-tag.html)     | 解析 XML 数据                                             |
| [](https://www.runoob.com/jsp/jstl-xml-set-tag.html)       | 设置XPath表达式                                           |
| [](https://www.runoob.com/jsp/jstl-xml-if-tag.html)        | 判断XPath表达式，若为真，则执行本体中的内容，否则跳过本体 |
| [](https://www.runoob.com/jsp/jstl-xml-foreach-tag.html)   | 迭代XML文档中的节点                                       |
| [](https://www.runoob.com/jsp/jstl-xml-choose-tag.html)    | <x:when>和<x:otherwise>的父标签                           |
| [](https://www.runoob.com/jsp/jstl-xml-choose-tag.html)    | <x:choose>的子标签，用来进行条件判断                      |
| [](https://www.runoob.com/jsp/jstl-xml-choose-tag.html)    | <x:choose>的子标签，当<x:when>判断为false时被执行         |
| [](https://www.runoob.com/jsp/jstl-xml-transform-tag.html) | 将XSL转换应用在XML文档中                                  |
| [](https://www.runoob.com/jsp/jstl-xml-param-tag.html)     | 与<x:transform>共同使用，用于设置XSL样式表                |

------

## JSTL函数

JSTL包含一系列标准函数，大部分是通用的字符串处理函数。引用JSTL函数库的语法如下：

```
<%@ taglib prefix="fn" 
           uri="http://java.sun.com/jsp/jstl/functions" %>
```

| 函数                                                         | 描述                                                     |
| :----------------------------------------------------------- | :------------------------------------------------------- |
| [fn:contains()](https://www.runoob.com/jsp/jstl-function-contains.html) | 测试输入的字符串是否包含指定的子串                       |
| [fn:containsIgnoreCase()](https://www.runoob.com/jsp/jstl-function-containsignoreCase.html) | 测试输入的字符串是否包含指定的子串，大小写不敏感         |
| [fn:endsWith()](https://www.runoob.com/jsp/jstl-function-endswith.html) | 测试输入的字符串是否以指定的后缀结尾                     |
| [fn:escapeXml()](https://www.runoob.com/jsp/jstl-function-escapexml.html) | 跳过可以作为XML标记的字符                                |
| [fn:indexOf()](https://www.runoob.com/jsp/jstl-function-indexof.html) | 返回指定字符串在输入字符串中出现的位置                   |
| [fn:join()](https://www.runoob.com/jsp/jstl-function-join.html) | 将数组中的元素合成一个字符串然后输出                     |
| [fn:length()](https://www.runoob.com/jsp/jstl-function-length.html) | 返回字符串长度                                           |
| [fn:replace()](https://www.runoob.com/jsp/jstl-function-replace.html) | 将输入字符串中指定的位置替换为指定的字符串然后返回       |
| [fn:split()](https://www.runoob.com/jsp/jstl-function-split.html) | 将字符串用指定的分隔符分隔然后组成一个子字符串数组并返回 |
| [fn:startsWith()](https://www.runoob.com/jsp/jstl-function-startswith.html) | 测试输入字符串是否以指定的前缀开始                       |
| [fn:substring()](https://www.runoob.com/jsp/jstl-function-substring.html) | 返回字符串的子集                                         |
| [fn:substringAfter()](https://www.runoob.com/jsp/jstl-function-substringafter.html) | 返回字符串在指定子串之后的子集                           |
| [fn:substringBefore()](https://www.runoob.com/jsp/jstl-function-substringbefore.html) | 返回字符串在指定子串之前的子集                           |
| [fn:toLowerCase()](https://www.runoob.com/jsp/jstl-function-tolowercase.html) | 将字符串中的字符转为小写                                 |
| [fn:toUpperCase()](https://www.runoob.com/jsp/jstl-function-touppercase.html) | 将字符串中的字符转为大写                                 |
| [fn:trim()](https://www.runoob.com/jsp/jstl-function-trim.html) | 移除首尾的空白符                                         |

