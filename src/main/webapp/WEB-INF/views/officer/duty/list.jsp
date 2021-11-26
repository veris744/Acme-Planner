<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list >
	<acme:list-column code="officer.duty.list.label.title" path="title" width="20%"/>
	<acme:list-column code="officer.duty.list.label.startPeriod" path="startPeriod" width="10%"/>
	<acme:list-column code="officer.duty.list.label.endPeriod" path="endPeriod" width="10%"/>
	<acme:list-column code="officer.duty.list.label.workload" path="workload" width="10%"/>
</acme:list>