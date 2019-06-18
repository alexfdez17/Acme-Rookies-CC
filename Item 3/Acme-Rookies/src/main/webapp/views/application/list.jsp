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


<jstl:choose>
		<jstl:when test="${(role=='company')}">		
		<a href="application/company/list.do?Status=SUBMITTED"> <spring:message
				code="applications.display.submitted" /></a>
				
		<a href="application/company/list.do?Status=ACCEPTED"> <spring:message
				code="applications.display.accepted" /></a>
				
		<a href="application/company/list.do?Status=REJECTED"> <spring:message
				code="applications.display.rejected" /></a>
		</jstl:when>
		<jstl:otherwise>
		<a href="application/rookie/list.do?Status=PENDING"> <spring:message
				code="applications.display.pending" /></a>
				
		<a href="application/rookie/list.do?Status=SUBMITTED"> <spring:message
				code="applications.display.submitted" /></a>
				
		<a href="application/rookie/list.do?Status=ACCEPTED"> <spring:message
				code="applications.display.accepted" /></a>
				
		<a href="application/rookie/list.do?Status=REJECTED"> <spring:message
				code="applications.display.rejected" /></a>
		</jstl:otherwise>
	</jstl:choose>

<display:table pagesize="5" class="displaytag" name="applications"
	requestURI="${requestURI}" id="row">


	<!-- Attributes -->

	<spring:message code="applications.positionProblem.position.title" var="titleHeader" />
	<display:column property="positionProblem.position.title" title="${titleHeader}" />

	<spring:message code="applications.creationMoment" var="creationMomentHeader" />
	<display:column property="creationMoment" title="${creationMomentHeader}" />

	<spring:message code="applications.status" var="statusHeader" />
			<display:column property="status" title="${statusHeader}" />

	<jstl:choose>
		<jstl:when test="${(role=='company')}">		
						
			<spring:message code="applications.submittedMoment" var="submittedMomentHeader" />
			<display:column property="submittedMoment" title="${submittedMomentHeader}" />
			
		</jstl:when>
	</jstl:choose>

	<!-- Actions -->
	<display:column>
		<a href="position/display.do?positionId=${row.positionProblem.position.id}"> <spring:message
				code="position.display" />
		</a>
	</display:column>
	<display:column>
		<a href="application/display.do?ApplicationId=${row.id}"> <spring:message
				code="applications.display" />
		</a>
	</display:column>
		
	<jstl:choose>
		<jstl:when test="${(role=='company' && row.status=='SUBMITTED')}">
			
			<display:column>
				<b><spring:message code="application.accept" var="accept"/></b>
				<a href="application/company/acceptApplication.do?ApplicationId=${row.id}">${accept}</a>
			</display:column>
			<display:column>
				<b><spring:message code="application.reject" var="reject"/></b>
				<a href="application/company/rejectApplication.do?ApplicationId=${row.id}">${reject}</a>
			</display:column>
			
		</jstl:when>
		<jstl:otherwise>
			<display:column>
				<a></a>
			</display:column>
			<display:column>
				<a></a>
			</display:column>
		</jstl:otherwise>
	</jstl:choose>
		
	<jstl:choose>
		<jstl:when test="${(role=='rookie' && row.status=='PENDING')}">
			
			<display:column>
				<b><spring:message code="application.update" var="update"/></b>
				<a href="application/rookie/edit.do?applicationId=${row.id}">${update}</a>
			</display:column>
			
		</jstl:when>
		<jstl:otherwise>
			<display:column>
				<a></a>
			</display:column>
		</jstl:otherwise>
	</jstl:choose>

</display:table>

<br />