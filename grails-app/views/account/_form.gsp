<%@ page import="sellit.Account" %>



<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="account.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${accountInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="account.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${accountInstance?.email}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="account.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="password" name="password" required="" value="${accountInstance?.password}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: accountInstance, field: 'address', 'error')} required">
	<label for="address">
		<g:message code="account.address.label" default="Address" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="address" name="address.id" from="${sellit.Address.list()}" optionKey="id" required="" value="${accountInstance?.address?.id}" class="many-to-one"/>

</div>

