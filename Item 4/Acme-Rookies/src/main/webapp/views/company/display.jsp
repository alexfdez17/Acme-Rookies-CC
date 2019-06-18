<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<b><spring:message code="company.comercialName" /></b>
<jstl:out value="${company.comercialName}" />
<br />
<b><spring:message code="company.phone" /></b>
<jstl:out value="${company.phone}" />
<br />

<b><spring:message code="company.email" /></b>
<jstl:out value="${company.email}" />
<br />

<b><spring:message code="company.address" /></b>
<jstl:out value="${company.address}" />
<br />
<b><spring:message code="actor.score" /></b>
<jstl:out value="${company.auditScore}" />
<br />

<display:table pagesize="5" class="displaytag" name="positions"
	requestURI="${requestURI}" id="row">


	<!-- Attributes -->

	<spring:message code="position.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<spring:message code="position.title" var="positionHeader" />
	<display:column property="title" title="${positionHeader}" />

	<spring:message code="position.deadline" var="deadlineHeader" />
	<display:column property="deadLine" title="${deadlineHeader}" />

	<spring:message code="position.salary" var="salaryHeader" />
	<display:column property="salaryOffered" title="${salaryHeader}" />

	<spring:message code="position.company" var="companyHeader" />
	<display:column property="company.comercialName"
		title="${companyHeader}" />


	<!-- Actions -->

	<display:column>
		<a href="position/display.do?positionId=${row.id}"> <spring:message
				code="position.display" />
		</a>
	</display:column>
</display:table>




