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


<b><spring:message code="curricula.personalData" /></b>

<display:table pagesize="5" class="displaytag"
	name="curricula.personalData" id="row">

	<!-- Attributes -->


	<spring:message code="curricula.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" />

	<spring:message code="curricula.middleName" var="middleNameHeader" />
	<display:column property="middleName" title="${middleNameHeader}" />

	<spring:message code="curricula.surname" var="surnameHeader" />
	<display:column property="surname" title="${surnameHeader}" />

	<spring:message code="curricula.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}" />

	<spring:message code="curricula.githubProfile"
		var="githubProfileHeader" />
	<display:column property="githubProfile"
		title="${githubProfileHeader}" />

	<spring:message code="curricula.linkedinProfile"
		var="linkedinProfileHeader" />
	<display:column property="linkedinProfile"
		title="${linkedinProfileHeader}" />

	<spring:message code="curricula.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" />
	<jstl:choose>
		<jstl:when test="${role=='rookie'}">
			<display:column>
				<a href="curricula/personalData/edit.do?personalDataId=${row.id}">
					<spring:message code="curricula.edit" />
				</a>
			</display:column>
		</jstl:when>
	</jstl:choose>




</display:table>

<b><spring:message code="curricula.educationData" /></b>

<display:table pagesize="5" class="displaytag"
	name="curricula.educationData" requestURI="curricula/display.do"
	id="row">

	<!-- Attributes -->


	<spring:message code="curricula.degree" var="degreeHeader" />
	<display:column property="degree" title="${degreeHeader}" />

	<spring:message code="curricula.institution" var="institutionHeader" />
	<display:column property="institution"
		title="${institutionHeader}" />

	<spring:message code="curricula.mark" var="markHeader" />
	<display:column property="mark" title="${markHeader}" />

	<spring:message code="curricula.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}" />

	<spring:message code="curricula.endDate" var="endDateHeader" />
	<display:column property="endDate" title="${endDateHeader}" />


	<jstl:choose>
		<jstl:when test="${role=='rookie'}">
			<display:column>
				<a href="curricula/educationData/edit.do?educationDataId=${row.id}">
					<spring:message code="curricula.edit" />
				</a>
			</display:column>

			<display:column>
				<a
					href="curricula/educationData/delete.do?educationDataId=${row.id}">
					<spring:message code="curricula.delete" />
				</a>
			</display:column>
		</jstl:when>
	</jstl:choose>

</display:table>
<jstl:choose>
	<jstl:when test="${role=='rookie'}">
		<a href="curricula/educationData/create.do?curriculaId=${curricula.id}"> <spring:message
				code="educationData.create" />
		</a>
	</jstl:when>
</jstl:choose>
<br />

<b><spring:message code="curricula.positionData" /></b>

<display:table pagesize="5" class="displaytag"
	name="curricula.positionsData" requestURI="curricula/display.do"
	id="row">

	<!-- Attributes -->


	<spring:message code="curricula.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />

	<spring:message code="curricula.description" var="descriptionHeader" />
	<display:column property="description"
		title="${descriptionHeader}" />


	<spring:message code="curricula.startDate" var="startDateHeader" />
	<display:column property="startDate" title="${startDateHeader}" />

	<spring:message code="curricula.endDate" var="endDateHeader" />
	<display:column property="endDate" title="${endDateHeader}" />


	<jstl:choose>
		<jstl:when test="${role=='rookie'}">
	<display:column>
		<a href="curricula/positionData/edit.do?positionDataId=${row.id}">
			<spring:message code="curricula.edit" />
		</a>
	</display:column>

	<display:column>
		<a href="curricula/positionData/delete.do?positionDataId=${row.id}">
			<spring:message code="curricula.delete" />
		</a>
	</display:column>
	</jstl:when>
	</jstl:choose>

</display:table>
	<jstl:choose>
		<jstl:when test="${role=='rookie'}">
<a href="curricula/positionData/create.do?curriculaId=${curricula.id}"> <spring:message
		code="positionData.create" />
</a>
</jstl:when>
</jstl:choose>
<br />

<b><spring:message code="curricula.miscellaneousData" /></b>
<jstl:choose>
	<jstl:when test="${curricula.miscellaneousData != null}">
		<b><spring:message code="curricula.miscellaneousData" /></b>

		<display:table pagesize="5" class="displaytag"
			name="curricula.miscellaneousData" id="row">

			<!-- Attributes -->


			<spring:message code="curricula.freeText" var="freeTextHeader" />
			<display:column property="freeText" title="${freeTextHeader}" />

			<spring:message code="curricula.attachments" var="attachmentsHeader" />
			<display:column property="attachments"
				title="${attachmentsHeader}" />

	<jstl:choose>
		<jstl:when test="${role=='rookie'}">
			<display:column>
				<a
					href="curricula/miscellaneousData/edit.do?miscellaneousDataId=${row.id}">
					<spring:message code="curricula.edit" />
				</a>
			</display:column>
			</jstl:when>
			</jstl:choose>
		</display:table>
	</jstl:when>
	<jstl:otherwise>
		<jstl:choose>
			<jstl:when test="${role=='rookie'}">
				<a href="curricula/miscellaneousData/create.do?curriculaId=${curricula.id}"> <spring:message
						code="miscellaneousData.create" />
				</a>
			</jstl:when>
		</jstl:choose>
		<br />
	</jstl:otherwise>
</jstl:choose>
