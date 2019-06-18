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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<!--  Listing grid -->


<form:form action="message/list.do" modelAttribute="tag">

<acme:textbox code="message.searchtag" path="value"/>
<acme:submit name="save" code="message.search"/>
</form:form>

<a href="message/create.do?"><spring:message code="message.create"></spring:message></a>
<br />


<display:table pagesize="5" class="displaytag" name="messages"
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

	<spring:message code="message.sender" var="senderHeader" />
	<display:column property="sender.userAccount.username"
		title="${senderHeader}" sortable="false" />

	<spring:message code="message.recipient" var="recipientHeader" />
	<display:column property="recipient.userAccount.username"
		title="${recipientHeader}" sortable="false" />
		
	<spring:message code="message.tags" var="tagsHeader" />
	<display:column property="tags"
		title="${tagsHeader}" sortable="false" />
		
	<display:column>
		<a href="message/display.do?messageId=${row.id}"><spring:message
				code="message.display"></spring:message></a>
	</display:column>
	<display:column>
		<a href="message/delete.do?messageId=${row.id}"><spring:message
				code="message.delete"></spring:message></a>
	</display:column>

</display:table>

