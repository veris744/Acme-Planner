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

<h2>
	<acme:message code="administrator.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm" id=dashboard>
	<caption>
		<acme:message code="administrator.dashboard.form.title.general-indicators"/>
	</caption>	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-public-tasks"/>
		</th>
		<td>
			<acme:print value="${numberOfPublicTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-private-tasks"/>
		</th>
		<td>
			<acme:print value="${numberOfPrivateTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-finished-tasks"/>
		</th>
		<td>
			<acme:print value="${numberOfFinishedTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-non-finished-tasks"/>
		</th>
		<td>
			<acme:print value="${numberOfNonFinishedTasks}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-workload"/>
		</th>
		<td>
			<acme:print value="${minimumWorkload}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-workload"/>
		</th>
		<td>
			<acme:print value="${maximumWorkload}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-workload"/>
		</th>
		<td>
			<acme:print value="${averageWorkload}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-workload"/>
		</th>
		<td>
			<acme:print value="${deviationWorkload}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-period"/>
		</th>
		<td>
			<acme:print value="${minimumPeriod}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-period"/>
		</th>
		<td>
			<acme:print value="${maximumPeriod}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-period"/>
		</th>
		<td>
			<acme:print value="${averagePeriod}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-period"/>
		</th>
		<td>
			<acme:print value="${deviationPeriod}"/>
		</td>
	</tr>
	<tr>
		<td>
		</td>
	</tr>
	<tr>
		<td>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-public-workPlans"/>
		</th>
		<td>
			<acme:print value="${numberOfPublicWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-private-workPlans"/>
		</th>
		<td>
			<acme:print value="${numberOfPrivateWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-finished-workPlans"/>
		</th>
		<td>
			<acme:print value="${numberOfFinishedWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.number-of-non-finished-workPlans"/>
		</th>
		<td>
			<acme:print value="${numberOfNonFinishedWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-workload-workPlans"/>
		</th>
		<td>
			<acme:print value="${minimumWorkloadWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-workload-workPlans"/>
		</th>
		<td>
			<acme:print value="${maximumWorkloadWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-workload-workPlans"/>
		</th>
		<td>
			<acme:print value="${averageWorkloadWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-workload-workPlans"/>
		</th>
		<td>
			<acme:print value="${deviationWorkloadWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.minimum-period-workPlans"/>
		</th>
		<td>
			<acme:print value="${minimumPeriodWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.maximum-period-workPlans"/>
		</th>
		<td>
			<acme:print value="${maximumPeriodWorkPlans}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-period-workPlans"/>
		</th>
		<td>
			<acme:print value="${averagePeriod}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-period-workPlans"/>
		</th>
		<td>
			<acme:print value="${deviationPeriodWorkPlans}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-budget-EUR"/>
		</th>
		<td>
			<acme:print value="${averageBudgetEUR}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-budget-EUR"/>
		</th>
		<td>
			<acme:print value="${deviationBudgetEUR}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.average-budget-USD"/>
		</th>
		<td>
			<acme:print value="${averageBudgetUSD}"/>
		</td>
	</tr>
	
	
	<tr>
		<th scope="row">
			<acme:message code="administrator.dashboard.form.label.deviation-budget-USD"/>
		</th>
		<td>
			<acme:print value="${deviationBudgetUSD}"/>
		</td>
	</tr>
	
	
	
</table>
<div>
	<canvas id="canvas1"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"PRIVATE", "PUBLIC"
			],
			datasets : [
				{
					data : [
								<jstl:out value="${numberOfPrivateWorkPlans}"/>, 
								<jstl:out value="${numberOfPublicWorkPlans}"/> 
					],
				backgroundColor : [
					'rgba(223, 26, 26, 1)', 'rgba(26, 132, 239, 1)'
					]
				}
			]
		};
		var total= <jstl:out value="${numberOfPublicWorkPlans} + ${numberOfPrivateWorkPlans}" />;
		var options = {
				legend : {
					display : true
				},
				
				title :{
					display :false,
					text :"Numero total de WorkPlans: " + total
				}

		};
	
		var canvas1, context;
	
		canvas1 = document.getElementById("canvas1");
		context = canvas1.getContext("2d");
		new Chart(context, {
			type : "pie",
			data : data,
			options : options
		});
	});
</script>
<br/>
<div>
	<canvas id="canvas2"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"IMPORTANT", "NON IMPORTANT"
			],
			datasets : [
				{
					data : [
								<jstl:out value="${numberImportantDeras}"/>, 
								<jstl:out value="${numberNonImportantDeras}"/> 
					],
				backgroundColor : [
					'rgba(223, 26, 26, 1)', 'rgba(26, 132, 239, 1)'
					]
				}
			]
		};
		var total= <jstl:out value="${numberImportantDeras} + ${numberNonImportantDeras}" />;
		var options = {
				legend : {
					display : true
				},
				
				title :{
					display :false,
					text :"Numero total de Deras: " + total
				}

		};
	
		var canvas2, context;
	
		canvas2 = document.getElementById("canvas2");
		context = canvas2.getContext("2d");
		new Chart(context, {
			type : "pie",
			data : data,
			options : options
		});
	});
</script>

<br/>
<div>
	<canvas id="canvas3"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
					"SHOUT WITH BUDGET 0", "OTHER SHOUTS"
			],
			datasets : [
				{
					data : [
								<jstl:out value="${numberShouts0Budget}"/>, 
								<jstl:out value="${numberShouts} - ${numberShouts0Budget}"/> 
					],
				backgroundColor : [
					'rgba(223, 26, 26, 1)', 'rgba(26, 132, 239, 1)'
					]
				}
			]
		};
		var total= <jstl:out value="${numberShouts}" />;
		var options = {
				legend : {
					display : true
				},
				
				title :{
					display :false,
					text :"Number of Shouts: " + total
				}

		};
	
		var canvas3, context;
	
		canvas3 = document.getElementById("canvas3");
		context = canvas3.getContext("2d");
		new Chart(context, {
			type : "pie",
			data : data,
			options : options
		});
	});
</script>
