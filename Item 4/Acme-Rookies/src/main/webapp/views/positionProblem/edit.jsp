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

<form:form action="positionProblem/company/edit.do"
	modelAttribute="positionProblem">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<jstl:choose>
	<jstl:when test="${id == 0}">
	<form:hidden path="position" />
	</jstl:when>
	</jstl:choose>
	
	<acme:textbox code="positionProblem.title" path="title"/>
	<br />
	<acme:textarea code="positionProblem.statement" path="statement"/>
	<br />
	<acme:textarea code="positionProblem.hint" path="hint"/>
	<br />
	<acme:textarea code="positionProblem.attachments" path="attachments"/>
	<br />
	<form:label path="draftMode">
		<spring:message code="positionProblem.draftMode" />:
	</form:label>
	<INPUT TYPE="radio" name="draftMode" value="true" checked="checked" />True
	<INPUT TYPE="radio" NAME="draftMode" value="false" />False
	<br />
	
	<acme:submit name="save" code="positionProblem.save" />
	<jstl:choose>
	<jstl:when test="${id != 0}">
		<acme:submit name="delete" code="positionProblem.delete" />
	</jstl:when>
	</jstl:choose>
	
	<acme:cancel code="positionProblem.cancel" url="positionProblem/company/list.do" />
	

</form:form>