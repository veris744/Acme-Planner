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
	<acme:form-textbox code="authenticated.duty.form.label.title" path="title"/>
	<acme:form-moment code="authenticated.duty.form.label.startPeriod" path="startPeriod"/>
	<acme:form-moment code="authenticated.duty.form.label.endPeriod" path="endPeriod"/>
	<acme:form-double code="authenticated.duty.form.label.workload" path="workload"/>
	<acme:form-textarea code="authenticated.duty.form.label.description" path="description"/>
	<acme:form-textbox code="authenticated.duty.form.label.link" path="link"/>
	
	<acme:form-return code="authenticated.duty.form.button.return"/>
</acme:form>
