<%@ page import="sellit.Account" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'account.label', default: 'Account')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-account" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-account" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list account">

        <g:if test="${accountInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="account.name.label" default="Name"/></span>
                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${accountInstance}" field="name"/></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.email}">
            <li class="fieldcontain">
                <span id="email-label" class="property-label"><g:message code="account.email.label" default="Email"/></span>
                <span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${accountInstance}" field="email"/></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.password}">
            <li class="fieldcontain">
                <span id="password-label" class="property-label"><g:message code="account.password.label" default="Password"/></span>
                <span class="property-value" aria-labelledby="password-label"><g:fieldValue bean="${accountInstance}" field="password"/></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.address}">
            <li class="fieldcontain">
                <span id="address-label" class="property-label"><g:message code="account.address.label" default="Address"/></span>
                <span class="property-value" aria-labelledby="address-label"><g:link controller="address" action="show" id="${accountInstance?.address?.id}">${accountInstance?.address?.addressLine1}</g:link></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.dateCreated}">
            <li class="fieldcontain">
                <span id="dateCreated-label" class="property-label"><g:message code="account.dateCreated" default="Date Created"/></span>
                <span class="property-value" aria-labelledby="dateCreated-label"><g:fieldValue bean="${accountInstance}" field="dateCreated"/></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.lastUpdated}">
            <li class="fieldcontain">
                <span id="lastUpdated-label" class="property-label"><g:message code="account.lastUpdated" default="Last Updated"/></span>
                <span class="property-value" aria-labelledby="lastUpdated-label"><g:fieldValue bean="${accountInstance}" field="lastUpdated"/></span>
            </li>
        </g:if>

        <g:if test="${accountInstance?.reviewList}">
            <li class="fieldcontain">
                <span id="ReviewList-label" class="property-label"><g:message code="account.ReviewList" default="Review List"/></span>
                <span class="property-value" aria-labelledby="ReviewList-label">
                    <table>
                        <thead>
                        <tr>
                            <th><g:message code="review.listedItem.label" default="Listed Item"/></th>
                            <g:sortableColumn property="wasSeller" title="${message(code: 'review.wasSeller.label', default: 'Was Seller')}"/>
                            <g:sortableColumn property="reviewDescription" title="${message(code: 'review.reviewDescription.label', default: 'Review Description')}"/>
                            <g:sortableColumn property="thumbsUp" title="${message(code: 'review.thumbsUp.label', default: 'Thumbs Up')}"/>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${accountInstance.reviewList}" status="i" var="reviewInstance">
                            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                                <td><g:link action="show" id="${reviewInstance.id}">${fieldValue(bean: reviewInstance, field: "listedItem.name")}</g:link></td>
                                <td><g:formatBoolean boolean="${reviewInstance.wasSeller}"/></td>
                                <td>${fieldValue(bean: reviewInstance, field: "reviewDescription")}</td>
                                <td><g:formatBoolean boolean="${reviewInstance.thumbsUp}"/></td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </span>
            </li>
        </g:if>

    </ol>
    <g:form url="[resource: accountInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${accountInstance}"><g:message code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
