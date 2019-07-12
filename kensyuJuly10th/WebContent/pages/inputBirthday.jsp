<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html:html>
	<head>
	<meta charset="UTF-8">
	<title>誕生日入力画面</title>
	</head>
	<body>
		<h2>誕生日占い</h2>
		<p>生年月日を入力してください(例：2019-07-11)</p>
		<html:form action = "/ResultValidater">
		<html:text property = "birthday" />
		<html:submit property = "submit" value = "結果" />
		</html:form>
	</body>
</html:html>