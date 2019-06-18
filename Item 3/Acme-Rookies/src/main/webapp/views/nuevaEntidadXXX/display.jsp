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

<b><spring:message code="nuevaEntidadXXX.audit" /></b>:
<jstl:out value="${nuevaEntidadXXX.audit.description}" />
<br />

<security:authorize access="hasRole('AUDITOR')">
<b><spring:message code="nuevaEntidadXXX.company" /></b>:
<jstl:out value="${nuevaEntidadXXX.audit.position.company.name}" />
<br />
</security:authorize>

<b><spring:message code="nuevaEntidadXXX.moment" /></b>:
<jstl:out value="${nuevaEntidadXXX.moment}" />
<br />

<b><spring:message code="nuevaEntidadXXX.body" /></b>:
<jstl:out value="${nuevaEntidadXXX.body}" />
<br />

<jstl:choose>
<jstl:when test="${role != ''}">
<b><spring:message code="nuevaEntidadXXX.picture" /></b>:
<jstl:out value="${nuevaEntidadXXX.picture}" />
<br />
</jstl:when>
</jstl:choose>

<b><spring:message code="nuevaEntidadXXX.draftMode" /></b>:
<jstl:out value="${nuevaEntidadXXX.draftMode}" />
<br />


<security:authorize access="hasRole('AUDITOR')">
<acme:cancel code="nuevaEntidadXXX.cancel" url="nuevaEntidadXXX/auditor/list.do" />
</security:authorize>

<security:authorize access="hasRole('COMPANY')">
<acme:cancel code="nuevaEntidadXXX.cancel" url="nuevaEntidadXXX/company/list.do" />
</security:authorize>