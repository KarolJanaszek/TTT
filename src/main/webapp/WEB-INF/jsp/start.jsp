<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Kółko i krzyżyk</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/start.css">
</head>
<body>
	<table>
		<tr>
			<th>
				<h1>Kółko i krzyżyk</h1>
			</th>
		</tr>
		<tr>
			<td>
				<img src="${pageContext.request.contextPath}/img/ttt.png"/>
			</td>
		</tr>
		<tr>
			<td>
				<a href="${pageContext.request.contextPath}/new-single-player"><button type="button">Singleplayer</button></a>
				<a href="${pageContext.request.contextPath}/new-player"><button type="button">Multiplayer</button></a>
			</td>
		</tr>
	</table>
</body>
</html>
