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

<form:form action="audit/auditor/edit.do"
	modelAttribute="audit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<jstl:choose>
	<jstl:when test="${id == 0}">
	<form:hidden path="auditor" />
	<form:hidden path="position" />
	</jstl:when>
	</jstl:choose>
	
	<acme:textarea code="audit.description" path="description"/>
	<br />
	<acme:textbox code="audit.score" path="score"/>
	<br />
	<form:label path="draftMode">
		<spring:message code="audit.draftMode" />:
	</form:label>
	<INPUT TYPE="radio" name="draftMode" value="true" checked="checked" />True
	<INPUT TYPE="radio" NAME="draftMode" value="false" />False
	<br />
	
	<acme:submit name="save" code="audit.save" />
	<jstl:choose>
	<jstl:when test="${id != 0}">
		<acme:submit name="delete" code="audit.delete" />
	</jstl:when>
	</jstl:choose>
	
	<acme:cancel code="audit.cancel" url="audit/auditor/list.do" />
	

</form:form>