<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Refresh" content="5">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/multiplayer.css">
<title>Kółko i krzyżyk</title>
</head>

<body>
	<h1>Cześć ${sessionUser.nick}</h1>

	<div>
		<h2>Lista graczy</h2>
		
		<table>
		  <tr>
		    <th>Gracz</th>
		  </tr>
		  <c:forEach items="${users}" var="user">
			  <c:if test="${user.key ne sessionUser.nick and not empty user.value.nick}">
			   <tr>
				    <td>
				    	<a href="${pageContext.request.contextPath}/multiplayer/call/${user.value.nick}">
				        ${user.key}
				        </a>
				   	</td>
			   </tr>
			   </c:if>
		   </c:forEach>
		</table>
	</div>

	<div>
		<h2>Wyzwania</h2>
		
		<table>
		  <tr>
		    <th>Wyzywający</th>
		    <th>Opcje</th>
		  </tr>
		  <c:forEach items="${calls}" var="call">
		   <tr>
		   		<c:if test="${call.value.playerO eq sessionUser}">
				    <td>
				        ${call.value.playerX.nick}
				   	</td>
				   	<td class="center">
				   		<a href="${pageContext.request.contextPath}/multiplayer/ready/${call.value.playerX.nick}"><button type="button">Przyjmij wyzwanie</button></a>
				   	</td>
			   	</c:if>
		   	</tr>
		   </c:forEach>
		</table>
	</div>

	<div>
		<h2>Moje gry</h2>
		
		<table>
		  <tr>
		    <th>Gracz X</th>
		    <th>Gracz O</th>
		    <th>Opcje</th>
		  </tr>
		  
		  <c:forEach items="${calls}" var="game">
		  	<tr>
		  		<c:if test="${game.value.isOver() == false}">
		   			<c:if test="${game.value.playerX eq sessionUser}">
						<td>
					        <p>${game.value.playerX.nick}</p>
					   	</td>
					    <td>
					        <p>${game.value.playerO.nick}</p>
					   	</td>
					   	<td class="center">
			   				<a href="${pageContext.request.contextPath}/multiplayer/game/${game.value.playerX.nick}+${game.value.playerO.nick}">
			   					<button type="button" class="option">Wejdz do gry</button>
			   				</a>
					   	</td>
				   	</c:if>
			   	</c:if>
		   	</tr>
		   </c:forEach>
		  
		  <c:forEach items="${games}" var="game">
		  	<tr>
		  		<c:if test="${game.value.isOver() == false}">
		   			<c:if test="${game.value.playerX eq sessionUser or game.value.playerO eq sessionUser}">
						<td>
					        <p>${game.value.playerX.nick}</p>
					   	</td>
					    <td>
					        <p>${game.value.playerO.nick}</p>
					   	</td>
					   	<td class="center">
			   				<a href="${pageContext.request.contextPath}/multiplayer/game/${game.value.playerX.nick}+${game.value.playerO.nick}">
			   					<button type="button" class="option">Wejdz do gry</button>
			   				</a>
					   	</td>
				   	</c:if>
			   	</c:if>
		   	</tr>
		   </c:forEach>
		</table>
	</div>
	
	<div>
		<h2>Historia</h2>
		<table>
		  <tr>
		    <th>Gracz X</th>
		    <th>Gracz O</th>
		    <th>Wynik</th>
		    <th>Opcje</th>
		  </tr>
			  <c:forEach items="${games}" var="game">
			  	<c:if test="${game.value.isOver()}">
			  		<c:if test="${game.value.playerX eq sessionUser or game.value.playerO eq sessionUser}">
					  <td>
					  	<p>${game.value.playerX.nick}</p>
					  </td>
					  <td>
					  	<p>${game.value.playerO.nick}</p>
					  </td>
					  <td class="center">
					  	<c:choose>
						  	<c:when test="${game.value.getWinner() eq game.value.playerX}">
		        				<p>Wygrał X</p>
		        			</c:when>
		        			<c:when test="${game.value.getWinner() eq game.value.playerO}">
		        				<p>Wygrał O</p>
						   	</c:when>
					   	</c:choose>
					  </td>
					  <td class="center">
					  	<c:choose>
					   		<c:when test="${game.value.isOver() and game.value.playerX eq sessionUser}">
				   				<a href="${pageContext.request.contextPath}/multiplayer/game/${game.value.playerX.nick}+${game.value.playerO.nick}/delete">
				   					<button type="button" class="option">Usuń grę</button>
				   				</a>
					   		</c:when>
					   		<c:when test="${game.value.isOver() and game.value.playerO eq sessionUser}">
				   					<button type="button" class="option" disabled="disabled">Usuń grę (tylko gracz X)</button>
					   		</c:when>
						</c:choose>
					  </td>
					  </c:if>
					</c:if>
			 </c:forEach>
		</table>
 	</div>
	<div>
		<a href="${pageContext.request.contextPath}/multiplayer/logout">
			<button type="button" class="menu">Wyloguj</button>
		</a>
	</div>
</body>
</html>
