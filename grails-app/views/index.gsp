<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>
<html>
<head>
    <asset:stylesheet href="application.css"/>
    <asset:javascript src="application.js"/>
</head>

<body ng-app="app">

<script>
    <%
		def user = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
	%>
    var username = '<% user.username %>'
</script>

<h1><b>Sellit.com</b></h1>

<sec:ifLoggedIn>
    <a href="logout">logout</a><br/>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
    <a href="login">login</a><br/>
</sec:ifNotLoggedIn>

<ng-view></ng-view>

</body>
</html>