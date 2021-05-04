  
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
	<acme:form-textbox code="manager.work-plan.form.label.title" path="title"/>
	<acme:form-moment code="manager.work-plan.form.label.startPeriod" path="startPeriod"/>
	<acme:form-moment code="manager.work-plan.form.label.endPeriod" path="endPeriod"/>
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
	</jstl:if>
	<acme:form-select code="manager.workplan.form.select.addTask" path="taskSelected">
		<jstl:forEach var="etask" items="${enabledTask}">
				<acme:form-option code="${etask.title}" value="${etask.id}"/> 
			</jstl:forEach>
	</acme:form-select>
	
	<acme:form-submit test="${command == 'create'}" code="manager.work-plan.form.button.create" action="/manager/work-plan/create"/>
	<acme:form-submit test="${command == 'show'}" code="manager.work-plan.form.button.update" action="/manager/work-plan/update"/>
	<acme:form-submit test="${command == 'show'}" code="manager.work-plan.form.button.delete" action="/manager/work-plan/delete"/>
	<acme:form-submit test="${command == 'update'}" code="manager.work-plan.form.button.update" action="/manager/work-plan/update"/>
	<acme:form-submit test="${command == 'delete'}" code="manager.work-plan.form.button.delete" action="/manager/work-plan/delete"/>
	<acme:form-return code="manager.work-plan.form.button.return"/>
	</acme:form>
	
	<!-- LO DE IVAAAAAAAAAAAAN -->
	
	
	<%-- 	<jstl:if test="${command=='show'|| command =='update' || Adderrors}">    
		   <div class="table-responsive">
		   <table class="table table-striped table-condensed table-hover nowrap w-100">
		   	  <caption><acme:message code="manager.workplan.form.label.tasks"/></caption>
			  <thead>
				    <tr>
				      <th scope="col"><acme:message code="manager.workplan.form.label.tasks.id"/></th>
				      <th scope="col"><acme:message code="manager.workplan.form.label.tasks.title"/></th>
				      <th scope="col"><acme:message code="manager.workplan.form.label.tasks.public"/></th>
				      <th scope="col"></th>
				    </tr>
				  </thead>
				  <tbody>
				  	<c:forEach items="${tasks}" var="task">
					  	<tr class="table-light">
					      <td>${task.id}</td>
					      <td>${task.title}</td>
					      <td><acme:message code="manager.workplan.form.label.tasks.public.${task.isPublic}"/></td>
					      <td>
					      <jstl:if test="${Mywp}">
					      	<acme:form>
					      		<input type="hidden" name="taskId" value="${task.id}">
					      		<input type="hidden" name="workplanId" value="${id}">
					      		<acme:form-submit code="manager.workplan.form.button.removeTask" action="/manager/work-plan/remove_task"/>   
					      	</acme:form>
					      </jstl:if>
					      </td>
					    </tr>
					</c:forEach>
				    </tbody>
			   </table>
		    </div>
    </jstl:if> --%>
    
	
	
	
	
	
	
	
	

	
	
	
	
	

	
	
