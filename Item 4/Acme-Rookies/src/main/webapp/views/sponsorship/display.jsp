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

<b><spring:message code="sponsorship.banner" /></b>
<jstl:out value="${sponsorship.banner}" />
<br />

<b><spring:message code="sponsorship.targetURL" /></b>
<jstl:out value="${sponsorship.targetPage}" />
<br />

<b><spring:message code="sponsorship.position" /></b>
<jstl:out value="${sponsorship.position.title}" />
<br />

<b><spring:message code="sponsorship.chargedAmount" /></b>
<jstl:out value="${sponsorship.chargedAmount}" />
<br />


