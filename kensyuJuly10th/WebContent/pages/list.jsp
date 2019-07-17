<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>過去半年間の入力誕生日の結果</title>
	<link rel = "stylesheet" href = "./style/stylesheet.css"/>
	</head>
	<body>
		<h2>過去半年間の入力誕生日の結果</h2>
		<table class = "list" border = "1">
		<tr>
			<th>占い日</th>
			<th>運勢</th>
			<th>願い事</th>
			<th>商い</th>
			<th>学問</th>
		</tr>

		<!-- マップの行数だけ繰り返し表示させる -->
		<c:forEach var = "i" begin = "0" end = "${loopCounter}" step = "1">

		<tr>
			<td>${fortuneDay[i]}</td>

			<!-- キーの日付からString[]のオブジェクトを取得し、各要素を取り出す -->
			<c:forEach var = "omikuji" items = "${omikujiMap[fortuneDay[i]]}">
			<td>${omikuji}</td>
			</c:forEach>

		</tr>
		</c:forEach>

		</table>
		<html:form action = "/backOmikuji">
			<html:hidden property="omikuji" value = "${omikuji}"/>
			<input type = "submit" value = "結果画面へ戻る" class = "omikuji_button"/>
		</html:form>

		<html:link forward = "top">トップへ戻る</html:link>
	</body>
</html>