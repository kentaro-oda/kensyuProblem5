<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>おみくじ結果</title>
	</head>
	<body>
		<h2>今日の結果</h2>
		<table>
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

		<html:form action = "/FortuneRate">
			<html:hidden property="today" value = "${today}"/>
			<html:submit property = "submit" value = "過去半年と今日の運勢の割合"/>
		</html:form><br>

		<html:form action = "/ResultList">
			<html:hidden property="today" value = "${today}"/>
			<html:hidden property="sqlBirthday" value = "${birthday}"/>
			<html:submit property = "submit" value = "過去半年間のあなたの運勢"/>
		</html:form>
	</body>
</html>