<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>
<html>
<head>
    <asset:stylesheet href="application.css"/>

    <script>
        <% def user = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        if (user?.username) {
        %>

        var loggedInUser = {
            username: '<%=  user?.username %>'
        };

        <% } %>

    </script>

    <asset:javascript src="application.js"/>
</head>

<body ng-app="app">

<h1><b>Sellit.com</b></h1>

<sec:ifLoggedIn>
    <a href="logout">logout</a><br/>
</sec:ifLoggedIn>
<sec:ifNotLoggedIn>
    <a href="login">login</a><br/>
    <a href="#/account">create new account</a><br/>
</sec:ifNotLoggedIn>

<ng-view></ng-view>

</body>
</html>