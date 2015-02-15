
<%@ page import="sellit.Review" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'review.label', default: 'Review')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-review" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-review" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list review">
			
				<g:if test="${reviewInstance?.listedItem}">
				<li class="fieldcontain">
					<span id="listedItem-label" class="property-label"><g:message code="review.listedItem.label" default="Listed Item" /></span>
					
						<span class="property-value" aria-labelledby="listedItem-label"><g:link controller="listing" action="show" id="${reviewInstance?.listedItem?.id}">${reviewInstance?.listedItem?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reviewInstance?.reviewedAccount}">
				<li class="fieldcontain">
					<span id="reviewedAccount-label" class="property-label"><g:message code="review.reviewedAccount.label" default="Reviewed Account" /></span>
					
						<span class="property-value" aria-labelledby="reviewedAccount-label"><g:link controller="account" action="show" id="${reviewInstance?.reviewedAccount?.id}">${reviewInstance?.reviewedAccount?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${reviewInstance?.wasSeller}">
				<li class="fieldcontain">
					<span id="wasSeller-label" class="property-label"><g:message code="review.wasSeller.label" default="Was Seller" /></span>
					
						<span class="property-value" aria-labelledby="wasSeller-label"><g:formatBoolean boolean="${reviewInstance?.wasSeller}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${reviewInstance?.reviewDescription}">
				<li class="fieldcontain">
					<span id="reviewDescription-label" class="property-label"><g:message code="review.reviewDescription.label" default="Review Description" /></span>
					
						<span class="property-value" aria-labelledby="reviewDescription-label"><g:fieldValue bean="${reviewInstance}" field="reviewDescription"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${reviewInstance?.thumbsUp}">
				<li class="fieldcontain">
					<span id="thumbsUp-label" class="property-label"><g:message code="review.thumbsUp.label" default="Thumbs Up" /></span>
					
						<span class="property-value" aria-labelledby="thumbsUp-label"><g:formatBoolean boolean="${reviewInstance?.thumbsUp}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:reviewInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${reviewInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
