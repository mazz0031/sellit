<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<!DOCTYPE html>
<html>
<head>
    <script>
        <%
			def user = SecurityContextHolder.getContext().getAuthentication().getPrincipal()
		%>

        var username = '<% user.username %>'
        %{--var id = '<% user.id %>'--}%
    </script>
    <asset:stylesheet href="application.css"/>
    <asset:javascript src="application.js"/>
</head>

<body ng-app="app">

index page

<ng-view></ng-view>

</body>
</html>