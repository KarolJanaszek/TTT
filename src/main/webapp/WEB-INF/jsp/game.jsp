<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Refresh" content="1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/game.css">
<title>Kółko i krzyżyk</title>
</head>
<body>
	<div>
		<h1>Gra ${game.playerX.nick} VS ${game.playerO.nick} </h1>
		<h2>${game.playerX.nick} jako X i ${game.playerO.nick} jako O</h2>
	</div>
	
	<div>
		<p>Grasz jako: ${sessionUser.nick}</p>
	</div>
	
	<div>
	<c:choose>
		<c:when test="${game.isOver()}">
			<c:choose>
				<c:when test="${game.getWinner() eq game.playerX}">
					<p>Wygrał gracz X</p>
				</c:when>
				<c:when test="${game.getWinner() eq game.playerO}">
					<p>Wygrał gracz O</p>
				</c:when>
				<c:otherwise>
					<p>Remis</p>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${game.getLenght()%2 eq 0 and game.playerX eq sessionUser and game.isOver() eq false}">
					<p>Twój ruch (X)</p>
				</c:when>
				<c:when test="${game.getLenght()%2 eq 1 and game.playerO eq sessionUser and game.isOver() eq false}">
					<p>Twój ruch (O)</p>
				</c:when>
				<c:otherwise>
					<p>Ruch przeciwnika</p>
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	</div>
	
	<div>
	<table>
		<c:forEach var="row" items="R1,R2,R3">
			<tr>
				<c:forEach var="column" items="C1,C2,C3">
					<c:choose>
						<c:when test="${game.get(row, column) eq game.playerX}">
							<c:set var="value" value="X" />			
						</c:when>
						<c:when test="${game.get(row, column) eq game.playerO}">
							<c:set var="value" value="O" />			
						</c:when>
						<c:otherwise>
							<c:set var="value" value="" />			
						</c:otherwise>
					</c:choose>
						<td>
						<c:choose>
							<c:when test="${game.isOver() == false}">
								<c:choose>
									<c:when test="${not empty value}">
										<button type="button" disabled="disabled" class="empty"><p class="board">${value}</p></button>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${game.getLenght()%2 eq 0 and game.playerX eq sessionUser}">
												<a href="${pageContext.request.contextPath}/multiplayer/game/${gameName}/${row}/${column}">
													<button type="button" class="empty"><p class="boardN">N</p></button>
												</a>	
											</c:when>
											<c:when test="${game.getLenght()%2 eq 1 and game.playerO eq sessionUser}">
												<a href="${pageContext.request.contextPath}/multiplayer/game/${gameName}/${row}/${column}">
													<button type="button" class="empty"><p class="boardN">N</p></button>
												</a>
											</c:when>
											<c:otherwise>
												<button type="button" disabled="disabled" class="empty"><p class="boardN">N</p></button>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${not empty value}">
										<button type="button" disabled="disabled" class="empty"><p class="board">${value}</p></button>
									</c:when>
									<c:otherwise>
										<button type="button" disabled="disabled" class="empty"><p class="boardN">N</p></button>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
	</div>
	<a href="${pageContext.request.contextPath}/multiplayer/"><button type="button">Wróć</button></a>
</body>
</html>
