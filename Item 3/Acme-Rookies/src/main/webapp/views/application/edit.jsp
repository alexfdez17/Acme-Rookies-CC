
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


<form:form action="application/rookie/edit.do" modelAttribute="application">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="positionProblem" />
	<form:hidden path="creationMoment" />
	<form:hidden path="submittedMoment" />
	<form:hidden path="status" />

	<b><spring:message code="application.positionProblem.title" /></b>
	<br/>
	<jstl:out value="${application.positionProblem.title}" />
	<br />
	
	<b><spring:message code="application.positionProblem.statement" /></b>
	<br/>
	<jstl:out value="${application.positionProblem.statement}" />
	<br />

	<b><spring:message code="application.positionProblem.hint" /></b>
	<br/>
	<jstl:out value="${application.positionProblem.hint}" />
	<br />

	<b><spring:message code="application.positionProblem.attachments" /></b>
	<br/>
	<jstl:out value="${application.positionProblem.attachments}" />
	<br />

	<acme:textbox code="application.answerExplanation" path="answerExplanation" />
	<acme:textbox code="application.answerCode" path="answerCode" />

	<acme:submit name="save" code="position.save" />

	<acme:cancel url="/application/rookie/list.do" code="position.cancel" />
</form:form>