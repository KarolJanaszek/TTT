<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kółko i krzyżyk - singleplayer</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
	<body>
		<h2>Singleplayer - gracz X zaczyna</h2>
		<form action="${pageContext.request.contextPath}/singleplayer/play" method="post">
		 	<p><input type="radio" name="role" value="X" /><label>Graj jako X.</label></p>
		 	<p><input type="radio" name="role" value="O" checked="checked"/><label>Graj jako O.</label></p>
		<input type="submit" value="Rozpocznij grę"/>
		</form>
	</body>
</html>