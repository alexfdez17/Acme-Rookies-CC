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

<form:form action="curricula/miscellaneousData/edit.do"
	modelAttribute="miscellaneousDataForm">
	<form:hidden path="curriculaId" />

	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox code="curricula.freeText" path="freeText" />
	<br />
	<acme:textarea code="curricula.attachments" path="attachments" />
	<br />
	<acme:submit name="save" code="curricula.save" />
	<acme:cancel code="curricula.cancel"
		url="curricula/list.do" />


</form:form>