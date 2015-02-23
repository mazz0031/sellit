<%@ page import="sellit.Address" %>



<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'addressLine1', 'error')} required">
	<label for="addressLine1">
		<g:message code="address.addressLine1.label" default="Address Line 1" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="addressLine1" required="" value="${addressInstance?.addressLine1}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'addressLine2', 'error')} required">
    <label for="addressLine2">
        <g:message code="address.addressLine2.label" default="Address Line 2&emsp;" />
    </label>
    <g:textField name="addressLine2" value="${addressInstance?.addressLine2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'city', 'error')} required">
	<label for="city">
		<g:message code="address.city.label" default="City" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="city" required="" value="${addressInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'stateAbbr', 'error')} required">
	<label for="stateAbbr">
		<g:message code="address.stateAbbr.label" default="State (2-letter abbreviation)" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="stateAbbr" maxlength="2" required="" value="${addressInstance?.stateAbbr}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'postalCode', 'error')} required">
	<label for="postalCode">
		<g:message code="address.postalCode.label" default="Postal Code" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="postalCode" maxlength="10" required="" value="${addressInstance?.postalCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: addressInstance, field: 'country', 'error')} required">
	<label for="country">
		<g:message code="address.country.label" default="Country&emsp;" />
	</label>
	<g:textField name="country" value="${addressInstance?.country}"/>
</div>

