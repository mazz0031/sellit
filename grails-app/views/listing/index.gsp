<%@ page import="sellit.Listing" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<g:form name="search" action="search">
    <br/>Search Listings:
    <input type="text" name="searchTerm"/>
    <button>Go</button>
    Show Completed Listings:
    <g:checkBox name="showCompleted"/>
</g:form>

<div id="list-listing" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="name" title="${message(code: 'listing.name.label', default: 'Name')}"/>
            <g:sortableColumn property="description" title="${message(code: 'listing.description.label', default: 'Description')}"/>
            <g:sortableColumn property="startDate" title="${message(code: 'listing.startDate.label', default: 'Start Date')}"/>
            <g:sortableColumn property="listingDays" title="${message(code: 'listing.listingDays.label', default: 'Listing Days')}"/>
            <g:sortableColumn property="startingPrice" title="${message(code: 'listing.startingPrice.label', default: 'Starting Price')}"/>
            <g:sortableColumn property="deliverOption" title="${message(code: 'listing.deliverOption.label', default: 'Deliver Option')}"/>
        </tr>
        </thead>
        <tbody>
        <g:each in="${listingInstanceList}" status="i" var="listingInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td><g:link action="show" id="${listingInstance.id}">${fieldValue(bean: listingInstance, field: "name")}</g:link></td>
                <td>${fieldValue(bean: listingInstance, field: "description")}</td>
                <td><g:formatDate date="${listingInstance.startDate}"/></td>
                <td>${fieldValue(bean: listingInstance, field: "listingDays")}</td>
                <td>${fieldValue(bean: listingInstance, field: "startingPrice")}</td>
                <td>${fieldValue(bean: listingInstance, field: "deliverOption")}</td>
            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${listingInstanceCount ?: 10}"/>
    </div>
</div>
</body>
</html>
