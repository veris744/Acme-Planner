  
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
		<acme:message code="manager.work-plan.form.suggestedPeriod"/> : <acme:print value="${inirec}"/> - <acme:print value="${finrec}"/>
	</jstl:if>
	<acme:form-textbox code="manager.work-plan.form.label.title" path="title"/>
	<acme:form-moment code="manager.work-plan.form.label.startPeriod" path="startPeriod"/>
	<p style="color:red" >
	<acme:print value="${fechainirec}"/>
	</p>
	
	<acme:form-moment code="manager.work-plan.form.label.endPeriod" path="endPeriod"/>
	<p style="color:red"> 
	<acme:print value="${fechafinrec}"/>
	</p>
	
	<acme:form-checkbox code="manager.work-plan.form.label.isPublic" path="isPublic"/>
	<jstl:if test="${command != 'create'}">
		<acme:message	code="manager.work-plan.form.label.taskList"/>
		<ul>
			<jstl:if test="${empty taskList}">
				<li><acme:message	code="manager.work-plan.form.label.empty"/></li>
			</jstl:if>
			<jstl:forEach var="task" items="${taskList}">
				<li><jstl:out value="${task.title}"/></li>
			</jstl:forEach>
		</ul>
		
		<acme:form-select code="manager.work-plan.form.label.addTask" path="taskSelected">
			<acme:form-option code="manager.work-plan.form.label.none" value="-1"/> 
			<jstl:forEach var="etask" items="${enabledTask}">
				<acme:form-option code="${etask.title}" value="${etask.id}"/> 
			</jstl:forEach>
		</acme:form-select>
	</jstl:if>
	
	<acme:form-submit test="${command == 'create'}" code="manager.work-plan.form.button.create" action="/manager/work-plan/create"/>
	<acme:form-submit test="${command == 'show'}" code="manager.work-plan.form.button.update" action="/manager/work-plan/update"/>
	<acme:form-submit test="${command == 'show'}" code="manager.work-plan.form.button.delete" action="/manager/work-plan/delete"/>
	<acme:form-submit test="${command == 'update'}" code="manager.work-plan.form.button.update" action="/manager/work-plan/update"/>
	<acme:form-submit test="${command == 'delete'}" code="manager.work-plan.form.button.delete" action="/manager/work-plan/delete"/>
	<acme:form-return code="manager.work-plan.form.button.return"/>
	</acme:form>
	