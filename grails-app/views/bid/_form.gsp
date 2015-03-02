<%@ page import="sellit.Bid" %>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'bidAmount', 'error')} required">
	<label for="bidAmount">
		<g:message code="bid.bidAmount.label" default="Bid Amount" />
		<span class="required-indicator">*</span>
	</label>
	<g:field id="bidAmount" type="text" name="bidAmount" value="${fieldValue(bean: bidInstance, field: 'bidAmount')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'biddingAccount', 'error')} required">
	<label for="biddingAccount">
		<g:message code="bid.biddingAccount.label" default="Bidding Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="biddingAccount" name="biddingAccount.id" from="${sellit.Account.list()}" optionKey="id" optionValue="name" required="" value="${bidInstance?.biddingAccount?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bidInstance, field: 'listedItem', 'error')} required">
	<label for="listedItem">
		<g:message code="bid.listedItem.label" default="Listed Item" />
	</label>
    %{--I don't know why I couldn't make this work without a hidden select control; it worked with just a readonly input until something (?) changed--}%
    <g:field id="listedItemName" type="text" name="listedItemName" value="${bidInstance?.listedItem?.name}" readonly="readonly"/>
	<g:select id="listedItem" name="listedItem.id" from="${sellit.Listing.list()}" hidden="" optionKey="id" optionValue="name" required="" value="${bidInstance?.listedItem?.id}" class="many-to-one"/>
</div>

