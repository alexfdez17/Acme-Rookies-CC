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

<b><spring:message code="audit.position" /></b>:
<jstl:out value="${audit.position.ticker}" />
<br />

<jstl:choose>
	<jstl:when test="${role == 'none'}">
		<b><spring:message code="audit.auditor" /></b>:
		<jstl:out value="${audit.auditor.name}" />
		<br />
	</jstl:when>
</jstl:choose>

<b><spring:message code="audit.moment" /></b>:
<jstl:out value="${audit.moment}" />
<br />

<b><spring:message code="audit.description" /></b>:
<jstl:out value="${audit.description}" />
<br />

<b><spring:message code="audit.score" /></b>:
<jstl:out value="${audit.score}" />
<br />

<b><spring:message code="audit.draftMode" /></b>:
<jstl:out value="${audit.draftMode}" />
<br />

<jstl:choose>
	<jstl:when test="${role == 'auditor'}">
		<acme:cancel code="audit.cancel" url="audit/auditor/list.do" />
	</jstl:when>
</jstl:choose>
