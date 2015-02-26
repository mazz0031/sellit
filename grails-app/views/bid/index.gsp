
<%@ page import="sellit.Bid" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bid.label', default: 'Bid')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-bid" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
			</ul>
		</div>
		<div id="list-bid" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="bidAmount" title="${message(code: 'bid.bidAmount.label', default: 'Bid Amount')}" />
					
						<th><g:message code="bid.biddingAccount.label" default="Bidding Account" /></th>
					
						<th><g:message code="bid.listedItem.label" default="Listed Item" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${bidInstanceList}" status="i" var="bidInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${bidInstance.id}">${fieldValue(bean: bidInstance, field: "bidAmount")}</g:link></td>

						<td>${fieldValue(bean: bidInstance, field: "biddingAccount.name")}</td>
					
						<td>${fieldValue(bean: bidInstance, field: "listedItem.name")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${bidInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
