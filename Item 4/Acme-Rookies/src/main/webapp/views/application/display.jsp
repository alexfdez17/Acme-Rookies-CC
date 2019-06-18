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

<b><spring:message code="application.creationMoment" /></b>
<jstl:out value="${application.creationMoment}" />
<br />
<b><spring:message code="application.answerExplanation" /></b>
<jstl:out value="${application.answerExplanation}" />
<br />
<b><spring:message code="application.answerCode" /></b>
<jstl:out value="${application.answerCode}" />
<br />
<b><spring:message code="application.submittedMoment" /></b>
<jstl:out value="${application.submittedMoment}" />
<br />

<b><spring:message code="application.status" /></b>
<jstl:out value="${application.status}" />
<br />

<b><spring:message code="application.positionProblem.title" /></b>
<jstl:out value="${application.positionProblem.title}" />
<br />

<b><spring:message code="application.positionProblem.statement" /></b>
<jstl:out value="${application.positionProblem.statement}" />
<br />

<b><spring:message code="application.positionProblem.hint" /></b>
<jstl:out value="${application.positionProblem.hint}" />
<br />

<b><spring:message code="application.positionProblem.attachments" /></b>
<jstl:out value="${application.positionProblem.attachments}" />
<br />

<jstl:choose>
	<jstl:when test="${(role=='rookie' && application.status=='PENDING')}">

			<b><spring:message code="application.update" var="update"/></b>
			<a href="application/rookie/edit.do?applicationId=${application.id}">${update}</a>
			
	</jstl:when>
</jstl:choose>



<jstl:choose>
	<jstl:when test="${(role=='company' && application.status=='SUBMITTED')}">

			<b><spring:message code="application.accept" var="accept"/></b>
			<a href="application/company/acceptApplication.do?ApplicationId=${application.id}">${accept}</a>
			<br />
			<b><spring:message code="application.reject" var="reject"/></b>
			<a href="application/company/rejectApplication.do?ApplicationId=${application.id}">${reject}</a>
			
	</jstl:when>
</jstl:choose>


