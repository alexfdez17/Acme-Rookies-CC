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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<b><spring:message code="positionProblem.title" /></b>
<jstl:out value="${problem.title}" />
<br />

<b><spring:message code="positionProblem.statement" /></b>
<jstl:out value="${problem.statement}" />
<br />

<b><spring:message code="positionProblem.hint" /></b>
<jstl:out value="${problem.hint}" />
<br />

<b><spring:message code="positionProblem.attachments" /></b>
<jstl:out value="${problem.attachments}" />
<br />

<b><spring:message code="positionProblem.draftMode" /></b>
<jstl:out value="${problem.draftMode}" />
<br />

<b><spring:message code="positionProblem.position" /></b>
<jstl:out value="${problem.position.ticker}" />
<br />

<acme:cancel code="positionProblem.cancel" url="positionProblem/company/list.do" />

