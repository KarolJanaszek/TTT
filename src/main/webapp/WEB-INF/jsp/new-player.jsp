<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<title>Kółko i krzyżyk - nowy gracz</title>
</head>
<body>
	<div>
		<h1>Kółko i krzyżyk - nowy gracz</h1>
	</div>
	<div>
		<p>Podaj nick</p>
	
	<c:choose>
	<c:when test="${isSinglePlayer eq 'yes'}">
	 	<form action="${pageContext.request.contextPath}/singleplayer/player-created" method="post">
	 	<input type="text" name="nickname" />
		<input type="submit" value="Dalej"/>
		</form>
	</c:when>
	<c:otherwise>
	<form action="${pageContext.request.contextPath}/multiplayer/player-create" method="post">
	 	<input type="text" name="nickname" />
		<input type="submit" value="Dalej"/>
		</form>
	</c:otherwise>
	</c:choose>
	</div>
	<div>	
		<a href="${pageContext.request.contextPath}"><button type="button">Wróć</button></a>	
	</div>
	<p>${msg}</p>
</body>
</html>
