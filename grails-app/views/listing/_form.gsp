<%@ page import="sellit.Listing" %>



<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="listing.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${listingInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="listing.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" required="" value="${listingInstance?.description}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="listing.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${listingInstance?.startDate}"  />

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'listingDays', 'error')} required">
	<label for="listingDays">
		<g:message code="listing.listingDays.label" default="Listing Days" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="listingDays" type="number" min="3" max="10" value="${listingInstance.listingDays}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'startingPrice', 'error')} required">
	<label for="startingPrice">
		<g:message code="listing.startingPrice.label" default="Starting Price" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="startingPrice" value="${fieldValue(bean: listingInstance, field: 'startingPrice')}" required=""/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'deliverOption', 'error')} required">
	<label for="deliverOption">
		<g:message code="listing.deliverOption.label" default="Deliver Option" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="deliverOption" from="${listingInstance.constraints.deliverOption.inList}" required="" value="${listingInstance?.deliverOption}" valueMessagePrefix="listing.deliverOption"/>

</div>

<div class="fieldcontain ${hasErrors(bean: listingInstance, field: 'sellerAccount', 'error')} required">
	<label for="sellerAccount">
		<g:message code="listing.sellerAccount.label" default="Seller Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sellerAccount" name="sellerAccount.id" from="${sellit.Account.list()}" optionKey="id" optionValue="name" required="" value="${listingInstance?.sellerAccount?.id}" class="many-to-one"/>

</div>


