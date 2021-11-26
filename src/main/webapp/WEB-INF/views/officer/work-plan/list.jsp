<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="officer.work-plan.list.label.title" path="title" width="20%"/>
	<acme:list-column code="officer.work-plan.list.label.startPeriod" path="startPeriod" width="10%"/>
	<acme:list-column code="officer.work-plan.list.label.endPeriod" path="endPeriod" width="10%"/>
	<acme:list-column code="officer.work-plan.list.label.workload" path="workload" width="10%"/>
</acme:list>