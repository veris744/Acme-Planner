<%--
- form.jsp
-
- Copyright (c) 2012-2021 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-double code="administrator.spam-word.form.label.word" path="word" placeholder="sex"/>
	
	<acme:form-submit test="${command == 'create'}" code="administrator.spam-word.form.button.create" action="/administrator/spam-word/create"/>
	<acme:form-return code="administrator.spam-word.form.button.return"/>	
</acme:form>
