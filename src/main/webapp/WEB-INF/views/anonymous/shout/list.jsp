<%--
- list.jsp
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

<acme:list readonly="true">
	<acme:list-column code="anonymous.shout.list.label.moment" path="moment" />
	<acme:list-column code="anonymous.shout.list.label.author" path="author" />
	<acme:list-column code="anonymous.shout.list.label.text" path="text" />
	<acme:list-column code="anonymous.shout.list.label.info" path="info" />
	<acme:list-column code="anonymous.shout.list.label.shoutInfo.moment2" path="shoutInfo.moment2"/>
	<acme:list-column code="anonymous.shout.list.label.shoutInfo.date" path="shoutInfo.date"/>
	<acme:list-column code="anonymous.shout.list.label.shoutInfo.money" path="shoutInfo.money"/>
	<acme:list-column code="anonymous.shout.list.label.shoutInfo.bool" path="shoutInfo.bool"/>
</acme:list>
