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

<form:form action="curricula/educationData/edit.do"
	modelAttribute="educationDataForm">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="curriculaId" />


	<acme:textbox code="curricula.degree" path="degree" />
	<br />
	<acme:textarea code="curricula.institution" path="institution" />
	<br />
	<acme:textarea code="curricula.mark" path="mark"/>
	<br />
	<acme:textbox code="curricula.startDate" path="startDate" />
	<br />
	<acme:textarea code="curricula.endDate" path="endDate" />
	<br />
	<acme:submit name="save" code="curricula.save" />
	<acme:cancel code="curricula.cancel"
		url="curricula/list.do" />


</form:form>