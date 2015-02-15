
<%@ page import="sellit.Review" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'review.label', default: 'Review')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-review" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-review" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="review.listedItem.label" default="Listed Item" /></th>
					
						<th><g:message code="review.reviewedAccount.label" default="Reviewed Account" /></th>
					
						<g:sortableColumn property="wasSeller" title="${message(code: 'review.wasSeller.label', default: 'Was Seller')}" />
					
						<g:sortableColumn property="reviewDescription" title="${message(code: 'review.reviewDescription.label', default: 'Review Description')}" />
					
						<g:sortableColumn property="thumbsUp" title="${message(code: 'review.thumbsUp.label', default: 'Thumbs Up')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${reviewInstanceList}" status="i" var="reviewInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${reviewInstance.id}">${fieldValue(bean: reviewInstance, field: "listedItem")}</g:link></td>
					
						<td>${fieldValue(bean: reviewInstance, field: "reviewedAccount")}</td>
					
						<td><g:formatBoolean boolean="${reviewInstance.wasSeller}" /></td>
					
						<td>${fieldValue(bean: reviewInstance, field: "reviewDescription")}</td>
					
						<td><g:formatBoolean boolean="${reviewInstance.thumbsUp}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${reviewInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
