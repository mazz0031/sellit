
<%@ page import="sellit.Listing" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'listing.label', default: 'Listing')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-listing" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-listing" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list listing">
			
				<g:if test="${listingInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="listing.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${listingInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="listing.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${listingInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.startDate}">
				<li class="fieldcontain">
					<span id="startDate-label" class="property-label"><g:message code="listing.startDate.label" default="Start Date" /></span>
					
						<span class="property-value" aria-labelledby="startDate-label"><g:formatDate date="${listingInstance?.startDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.listingDays}">
				<li class="fieldcontain">
					<span id="listingDays-label" class="property-label"><g:message code="listing.listingDays.label" default="Listing Days" /></span>
					
						<span class="property-value" aria-labelledby="listingDays-label"><g:fieldValue bean="${listingInstance}" field="listingDays"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.startingPrice}">
				<li class="fieldcontain">
					<span id="startingPrice-label" class="property-label"><g:message code="listing.startingPrice.label" default="Starting Price" /></span>
					
						<span class="property-value" aria-labelledby="startingPrice-label"><g:fieldValue bean="${listingInstance}" field="startingPrice"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.deliverOption}">
				<li class="fieldcontain">
					<span id="deliverOption-label" class="property-label"><g:message code="listing.deliverOption.label" default="Deliver Option" /></span>
					
						<span class="property-value" aria-labelledby="deliverOption-label"><g:fieldValue bean="${listingInstance}" field="deliverOption"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${listingInstance?.sellerAccount}">
				<li class="fieldcontain">
					<span id="sellerAccount-label" class="property-label"><g:message code="listing.sellerAccount.label" default="Seller Account" /></span>
					
						<span class="property-value" aria-labelledby="sellerAccount-label"><g:link controller="account" action="show" id="${listingInstance?.sellerAccount?.id}">${listingInstance?.sellerAccount?.name}</g:link></span>
					
				</li>
				</g:if>

                <g:if test="${listingInstance?.currentHighBid}">
                    <li class="fieldcontain">
                        <span id="bidAmount-label" class="property-label"><g:message code="bid.bidAmount.label" default="Current High Bid Amount" /></span>

                        <span class="property-value" aria-labelledby="bidAmount-label"><g:fieldValue bean="${listingInstance}" field="currentHighBid"/></span>

                    </li>
                </g:if>
			
			</ol>
			<g:form url="[resource:listingInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${listingInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
                    <% /*  ToDo: add a button for create a new bid */ %>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
