<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>おみくじ結果</title>
	<link rel = "stylesheet" href = "./style/stylesheet.css"/>
	</head>
	<body>
		<h2>今日の結果</h2>
		<table class = "omikuji">
			<tr>
			<td colspan = "2">今日の運勢は${omikuji[0]}です。</td>
			</tr>
			<tr>
			<th>願い事：</th>
			<td>${omikuji[1]}</td>
			</tr>
			<tr>
			<th>商い：</th>
			<td>${omikuji[2]}</td>
			</tr>
			<tr>
			<th>学問：</th>
			<td>${omikuji[3]}</td>
			</tr>
		</table>

		<html:form action = "/getFortuneRate">
			<html:hidden property="today" value = "${today}"/>
			<input type = "submit" value = "過去半年と今日の運勢の割合"/>
		</html:form><br>

		<html:form action = "/getResultList">
			<html:hidden property="today" value = "${today}"/>
			<html:hidden property="sqlBirthday" value = "${sqlBirthday}"/>
			<input type = "submit" value = "過去半年間のあなたの運勢"/>
		</html:form><br />

		<html:link forward = "top">トップへ戻る</html:link>
	</body>
</html>