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

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" name="positions"
	requestURI="${requestURI}" id="row">


	<!-- Attributes -->
	<jstl:choose>
		<jstl:when test="${role=='company'}">
			<spring:message code="position.status" var="statusHeader" />
			<display:column property="status" title="${statusHeader}" />
		</jstl:when>
	</jstl:choose>

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

	<security:authorize access="hasRole('COMPANY')">
		<display:column>
			<a href="position/company/display.do?positionId=${row.id}"> <spring:message
					code="position.display" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('PROVIDER')">
		<display:column>
			<a href="sponsorship/provider/create.do?positionId=${row.id}"> <spring:message
					code="sponsorship.create" />
			</a>
		</display:column>
	</security:authorize>

	<security:authorize access="!hasRole('COMPANY')">
		<display:column>
			<a href="position/display.do?positionId=${row.id}"> <spring:message
					code="position.display" />
			</a>
		</display:column>
	</security:authorize>


	<display:column>
		<jstl:choose>
			<jstl:when test="${row.status == 'draft' }">
				<a href="position/company/edit.do?positionId=${row.id}"> <spring:message
						code="position.edit" />
				</a>
			</jstl:when>
		</jstl:choose>
	</display:column>
	<display:column>
		<jstl:choose>
			<jstl:when test="${row.status== 'draft' }">
				<a href="positionProblem/company/create.do?positionId=${row.id}">
					<spring:message code="position.problem.create" />
				</a>
			</jstl:when>
		</jstl:choose>
	</display:column>







</display:table>
<security:authorize access="hasRole('COMPANY')">
	<a href="position/company/create.do"> <spring:message
			code="position.create" />
	</a>
</security:authorize>

<br />