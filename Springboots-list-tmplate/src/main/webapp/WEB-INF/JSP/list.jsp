<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.List,com.boot.tmplate.pojo.User"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
		<head>
			<meta charset="utf-8">
			<title>JSP</title>
		</head>
		<body>
			b.循环输出数组集合等
				<table>
				<tr>
					<th>姓名</th>
					<th>ID</th>
					<th>年龄</th>
					<th>邮箱</th>
				</tr>
			<%
			 List <User> lists=( List <User>)request.getAttribute("list");

				for(User user:lists){
					out.print("<tr><td>");
					out.print("<td>"+user.getName()+"</td>");
					out.print("<td>"+user.getId()+"</td>");
					out.print("<td>"+user.getAge()+"</td>");
					out.print("<td>"+user.getEmail()+"</td></tr>");
				}
			%>
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
					 ${list}
				  </pre>
		</body>
</html>

