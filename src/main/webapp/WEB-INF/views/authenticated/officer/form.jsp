<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-submit test="${command == 'create'}" code="authenticated.officer.form.button.create" action="/authenticated/officer/create"/>
	<acme:form-return code="authenticated.officer.form.button.return"/>
</acme:form>