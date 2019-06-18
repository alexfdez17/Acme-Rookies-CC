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




<b><spring:message code="actor.name" /></b>
<jstl:out value="${actor.name}" />
<br />

<b><spring:message code="actor.surname" /></b>
<jstl:out value="${actor.surname}" />
<br />

<b><spring:message code="actor.vat" /></b>
<jstl:out value="${actor.vat}" />
<br />

<b><spring:message code="actor.photo" /></b>
<jstl:out value="${actor.photo}" />
<br />

<b><spring:message code="actor.email" /></b>
<jstl:out value="${actor.email}" />
<br />

<b><spring:message code="actor.phone" /></b>
<jstl:out value="${actor.phone}" />
<br />

<b><spring:message code="actor.address" /></b>
<jstl:out value="${actor.address}" />
<br />

<b><spring:message code="actor.holder" /></b>
<jstl:out value="${actor.creditCard.holder}" />
<br />

<b><spring:message code="actor.make" /></b>
<jstl:out value="${actor.creditCard.make}" />
<br />

<b><spring:message code="actor.number" /></b>
<jstl:out value="${actor.creditCard.number}" />
<br />


<security:authorize access="hasRole('ADMIN')">
	<jstl:if test="${actor.spammer=='true'}">
		<spring:message code="actor.spammer" />
	</jstl:if>
	<jstl:if test="${actor.spammer!='true'}">
		<spring:message code="actor.notspammer" />
	</jstl:if>
	<br />
</security:authorize>

<br />

