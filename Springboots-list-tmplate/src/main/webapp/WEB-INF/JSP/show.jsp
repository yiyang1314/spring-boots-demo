<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="java.util.List,com.boot.tmplate.pojo.User"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
		<head>
			<meta charset="utf-8">
			<title>JSP</title>
		</head>
		<body>

				<table>
				<tr>
					<th>姓名</th>
					<th>ID</th>
					<th>年龄</th>
					<th>邮箱</th>
				</tr>
				<c:forEach var = "user"  items ="${requestScope.list}"  varStatus = "status">
					 <tr>
							<td><c:out value ="${user.name}" /></td>
								<td><c:out value ="${user.id}" /></td>
								<td><c:out value ="${user.email}" /></td>
								<td><c:out value ="${user.age}" /></td>
							      	<td>    <c:out value ="${status.index}" /></td>
					</tr>
				  </c:forEach>
				  </table>
				  
				  <pre>
					current: 当前这次迭代的（集合中的）项
					 index: 当前这次迭代从 0 开始的迭代索引
					 count: 当前这次迭代从 1 开始的迭代计数
					 first: 用来表明当前这轮迭代是否为第一次迭代的标志
					 last: 用来表明当前这轮迭代是否为最后一次迭代的标志
					 begin: 属性值
					 end: 属性值
					 step: 属性值  
					 ${list[0].id}
				  </pre>
				a.简单循环输出数字
				  <c:forEach var = "tem" begin = "1" end = "10" step = "2" varStatus = "status">
					    <c:out value = "${status.index}" />
					        <c:out value = "${item}" />
				 </c:forEach>  
</body>
</html>