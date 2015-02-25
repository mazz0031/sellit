
<%@ page import="sellit.Bid" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'bid.label', default: 'Bid')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-bid" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-bid" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list bid">
			
				<g:if test="${bidInstance?.bidAmount}">
				<li class="fieldcontain">
					<span id="bidAmount-label" class="property-label"><g:message code="bid.bidAmount.label" default="Bid Amount" /></span>
					
						<span class="property-value" aria-labelledby="bidAmount-label"><g:fieldValue bean="${bidInstance}" field="bidAmount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${bidInstance?.biddingAccount}">
				<li class="fieldcontain">
					<span id="biddingAccount-label" class="property-label"><g:message code="bid.biddingAccount.label" default="Bidding Account" /></span>
					
						<span class="property-value" aria-labelledby="biddingAccount-label"><g:link controller="account" action="show" id="${bidInstance?.biddingAccount?.id}">${bidInstance?.biddingAccount?.name}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${bidInstance?.listedItem}">
				<li class="fieldcontain">
					<span id="listedItem-label" class="property-label"><g:message code="bid.listedItem.label" default="Listed Item" /></span>
					
						<span class="property-value" aria-labelledby="listedItem-label"><g:link controller="listing" action="show" id="${bidInstance?.listedItem?.id}">${bidInstance?.listedItem?.name}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
		</div>
	</body>
</html>
