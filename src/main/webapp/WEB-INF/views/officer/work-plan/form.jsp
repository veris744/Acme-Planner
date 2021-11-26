  
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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<acme:form>
	<jstl:if test="${!empty inirec or !empty finrec}">
		<acme:message code="officer.work-plan.form.suggestedPeriod"/> : <acme:print value="${inirec}"/> - <acme:print value="${finrec}"/>
	</jstl:if>
	<acme:form-textbox code="officer.work-plan.form.label.title" path="title" placeholder="WorkPlan-001"/>
	<acme:form-moment code="officer.work-plan.form.label.startPeriod" path="startPeriod"/>
	<p style="color:red" >
	<acme:print value="${fechainirec}"/>
	</p>
	
	<acme:form-moment code="officer.work-plan.form.label.endPeriod" path="endPeriod"/>
	<p style="color:red"> 
	<acme:print value="${fechafinrec}"/>
	</p>
	
	<acme:form-checkbox code="officer.work-plan.form.label.isPublic" path="isPublic"/>
	<jstl:if test="${command != 'create'}">
		<acme:message	code="officer.work-plan.form.label.dutyList"/>
		<ul>
			<jstl:if test="${empty dutyList}">
				<li><acme:message	code="officer.work-plan.form.label.empty"/></li>
			</jstl:if>
			<jstl:forEach var="duty" items="${dutyList}">
				<li><jstl:out value="${duty.title}"/></li>
			</jstl:forEach>
		</ul>
		
		<acme:form-select code="officer.work-plan.form.label.addDuty" path="dutySelected">
			<acme:form-option code="officer.work-plan.form.label.none" value="-1"/> 
			<jstl:forEach var="eduty" items="${enabledDuty}">
				<acme:form-option code="${eduty.title}" value="${eduty.id}"/> 
			</jstl:forEach>
		</acme:form-select>
		
		<acme:form-select code="officer.work-plan.form.label.deleteDuty" path="dutyDeleteSelected">
			<acme:form-option code="officer.work-plan.form.label.none" value="-1"/> 
			<jstl:forEach var="dduty" items="${dutyList}">
				<acme:form-option code="${dduty.title}" value="${dduty.id}"/> 
			</jstl:forEach>
		</acme:form-select>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}" code="officer.work-plan.form.button.create" action="/officer/work-plan/create"/>
	<acme:form-submit test="${command == 'show'}" code="officer.work-plan.form.button.update" action="/officer/work-plan/update"/>
	<acme:form-submit test="${command == 'show'}" code="officer.work-plan.form.button.delete" action="/officer/work-plan/delete"/>
	<acme:form-submit test="${command == 'update'}" code="officer.work-plan.form.button.update" action="/officer/work-plan/update"/>
	<acme:form-submit test="${command == 'delete'}" code="officer.work-plan.form.button.delete" action="/officer/work-plan/delete"/>
	<acme:form-return code="officer.work-plan.form.button.return"/>
	</acme:form>
	