<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<security:authorize access="hasRole('ADMIN')">
	<a href="notification/create.do?"><spring:message
			code="notification.create"></spring:message></a>
	<br />
</security:authorize>

<display:table pagesize="5" class="displaytag" name="notifications"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->

	<spring:message code="message.body" var="bodyHeader" />
	<display:column property="body" title="${bodyHeader}" sortable="false" />

	<spring:message code="message.subject" var="subjectHeader" />
	<display:column property="subject" title="${subjectHeader}"
		sortable="false" />

	<spring:message code="message.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="false" format="{0,date,yyyy/MM/dd HH:mm}" />

	<spring:message code="message.tags" var="tagsHeader" />
	<display:column property="tags" title="${tagsHeader}" sortable="false" />

	<display:column>
		<a href="notification/display.do?notificationId=${row.id}"><spring:message
				code="message.display"></spring:message></a>
	</display:column>

</display:table>

