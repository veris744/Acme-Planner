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
	<acme:form-textbox code="officer.duty.form.label.title" path="title" placeholder="Duty-001"/>
	<acme:form-moment code="officer.duty.form.label.startPeriod" path="startPeriod"/>
	<acme:form-moment code="officer.duty.form.label.endPeriod" path="endPeriod"/>
	<acme:form-double code="officer.duty.form.label.workload" path="workload"/>
	<acme:form-textarea code="officer.duty.form.label.description" path="description" placeholder="Lorem ipsum."/>
	<acme:form-textbox code="officer.duty.form.label.link" path="link" placeholder="https://google.es"/>
	<acme:form-checkbox code="officer.duty.form.label.isPublic" path="isPublic"/>
	

	<acme:form-submit test="${command == 'create'}" code="officer.duty.form.button.create" action="/officer/duty/create"/>
	<acme:form-submit test="${command == 'show'}" code="officer.duty.form.button.update" action="/officer/duty/update"/>
	<acme:form-submit test="${command == 'show'}" code="officer.duty.form.button.delete" action="/officer/duty/delete"/>
	<acme:form-submit test="${command == 'update'}" code="officer.duty.form.button.update" action="/officer/duty/update"/>
	<acme:form-submit test="${command == 'delete'}" code="officer.duty.form.button.delete" action="/officer/duty/delete"/>
	<acme:form-return code="officer.duty.form.button.return"/>
</acme:form>
