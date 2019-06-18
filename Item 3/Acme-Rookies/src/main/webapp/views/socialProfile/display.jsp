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

<b><spring:message code="socialProfile.nick" /></b>
<jstl:out value="${socialProfile.nick}" />
<br />

<b><spring:message code="socialProfile.socialNetwork" /></b>
<jstl:out value="${socialProfile.socialNetwork}" />
<br />

<b><spring:message code="socialProfile.profileLink" /></b>
<jstl:out value="${socialProfile.profileLink}" />
<br />

<acme:cancel code="socialProfile.cancel" url="socialProfile/list.do" />

