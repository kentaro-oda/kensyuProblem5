<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>運勢の割合</title>
	</head>
	<body>
		<h2>各運勢の割合</h2>

		<!-- 過去半年間の各運勢の割合の表 -->
		<table border = "1">
		<tr>
			<th colspan = "2">半年間の割合</th>
		</tr>
		<c:forEach var = "i" begin = "0" end = "${loopCounter}" step = "1">
			<tr>
			<th>${fortuneName[i]}</th>
			<td>${rateHalfAYear[i]}</td>
			</tr>
		</c:forEach>
		</table>

		<!-- 当日の各運勢の割合の表 -->
		<table border = "1">
		<tr>
			<th colspan = "2">当日の割合</th>
		</tr>
		<c:forEach var = "i" begin = "0" end = "${loopCounter}" step = "1">
			<tr>
			<th>${fortuneName[i]}</th>
			<td>${rateToday[i]}</td>
			</tr>
		</c:forEach>
		</table>
	</body>
</html>