<%@ page import="sellit.Review" %>



<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'listedItem', 'error')} required">
	<label for="listedItem">
		<g:message code="review.listedItem.label" default="Listed Item" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="listedItem" name="listedItem.id" from="${sellit.Listing.list()}" optionKey="id" required="" value="${reviewInstance?.listedItem?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewedAccount', 'error')} required">
	<label for="reviewedAccount">
		<g:message code="review.reviewedAccount.label" default="Reviewed Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="reviewedAccount" name="reviewedAccount.id" from="${sellit.Account.list()}" optionKey="id" required="" value="${reviewInstance?.reviewedAccount?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'wasSeller', 'error')} ">
	<label for="wasSeller">
		<g:message code="review.wasSeller.label" default="Was Seller" />
		
	</label>
	<g:checkBox name="wasSeller" value="${reviewInstance?.wasSeller}" />

</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'reviewDescription', 'error')} required">
	<label for="reviewDescription">
		<g:message code="review.reviewDescription.label" default="Review Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="reviewDescription" maxlength="50" required="" value="${reviewInstance?.reviewDescription}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: reviewInstance, field: 'thumbsUp', 'error')} ">
	<label for="thumbsUp">
		<g:message code="review.thumbsUp.label" default="Thumbs Up" />
		
	</label>
	<g:checkBox name="thumbsUp" value="${reviewInstance?.thumbsUp}" />

</div>

