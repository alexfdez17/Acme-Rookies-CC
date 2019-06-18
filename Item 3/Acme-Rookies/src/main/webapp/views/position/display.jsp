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


<jstl:if test="${sponsorship != null}">
<img src="${sponsorship.banner}" height="100" width="100" alt="${sponsorship.banner}">
</jstl:if>

<br/>

<b><spring:message code="position.title" /></b>
<jstl:out value="${position.title}" />
<br />
<b><spring:message code="position.ticker" /></b>
<jstl:out value="${position.ticker}" />
<br />
<jstl:choose>
	<jstl:when test="${role2== 'owner' }">
<b><spring:message code="position.status" /></b>
<jstl:out value="${position.status}" />
<br />
</jstl:when>
</jstl:choose>
<b><spring:message code="position.company" /></b>
<a href="company/display.do?companyId=${position.company.id}">${position.company.comercialName}</a>
<br />
<b><spring:message code="position.description" /></b>
<jstl:out value="${position.description}" />
<br />

<b><spring:message code="position.deadline" /></b>
<jstl:out value="${position.deadLine}" />
<br />

<b><spring:message code="position.salary" /></b>
<jstl:out value="${position.salaryOffered}" />
<br />

<b><spring:message code="position.profileRequired" /></b>
<jstl:out value="${position.profileRequired}" />
<br />

<b><spring:message code="position.skillsRequired" /></b>
<jstl:out value="${position.skillsRequired}" />
<br />

<b><spring:message code="position.technologiesRequired" /></b>
<jstl:out value="${position.technologiesRequired}" />
<br />
<jstl:choose>
	<jstl:when test="${role2== 'owner' }">
		<jstl:choose>
			<jstl:when test="${cancelable == 'yes' }">

				<a href="position/company/cancel.do?positionId=${position.id}">
					<spring:message code="position.cancel" />
				</a>

			</jstl:when>
		</jstl:choose>
	</jstl:when>
</jstl:choose>

<jstl:choose>
	<jstl:when test="${deletable== 'yes' }">

		<a href="position/company/delete.do?positionId=${position.id}"> <spring:message
				code="position.delete" />
		</a>


	</jstl:when>
</jstl:choose>

<b><spring:message code="position.positionProblems" /></b>
		<display:table pagesize="5" class="displaytag" name="problems"
			requestURI="position/company/display.do" id="row">

			<!-- Attributes -->



			<spring:message code="positionProblem.title" var="titleHeader" />
			<display:column property="title" title="${titleHeader}"
				sortable="false" />

			<spring:message code="positionProblem.statement"
				var="statementHeader" />
			<display:column property="statement" title="${statementHeader}"
				sortable="false" />



			<!-- Actions -->

			<display:column>
				<a
					href="positionProblem/company/display.do?positionProblemId=${row.id}">
					<spring:message code="positionProblem.display" />
				</a>
			</display:column>
		</display:table>

<b><spring:message code="position.audits" /></b>
		<display:table pagesize="5" class="displaytag" name="audits"
			requestURI="audit/list.do" id="row">

			<!-- Attributes -->

			<spring:message code="audit.auditor" var="auditorHeader" />
			<display:column property="auditor.name" title="${auditorHeader}"
				sortable="false" />

			<spring:message code="audit.score" var="scoreHeader" />
			<display:column property="score" title="${scoreHeader}"
				sortable="false" />

			<spring:message code="audit.description" var="descriptionHeader" />
			<display:column property="description" title="${descriptionHeader}"
				sortable="false" />

			<!-- Actions -->

			<display:column>
				<a href="audit/display.do?auditId=${row.id}"> <spring:message
						code="audit.display" />
				</a>
			</display:column>
			
			<security:authorize access="hasRole('COMPANY')">
			<display:column>
				<a href="nuevaEntidadXXX/company/create.do?auditId=${row.id}"> <spring:message
						code="nuevaEntidadXXX.create" />
				</a>
			</display:column>
			</security:authorize>
		</display:table>

<br />

<security:authorize access="hasRole('ROOKIE')">


	<b><spring:message code="position.apply" var="apply" /></b>
	<a href="application/rookie/create.do?PositionId=${position.id}">${apply}</a>

</security:authorize>
<br />
<security:authorize access="hasRole('AUDITOR')">

	<jstl:choose>
		<jstl:when test="${canAudit == true}">
			<b><spring:message code="audit.create" var="audit" /></b>
			<a href="audit/auditor/create.do?positionId=${position.id}">${audit}</a>
		</jstl:when>
	</jstl:choose>

</security:authorize>

