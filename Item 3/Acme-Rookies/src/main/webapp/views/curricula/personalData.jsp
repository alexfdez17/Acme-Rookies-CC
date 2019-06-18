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

<form:form action="curricula/personalData/edit.do"
	modelAttribute="personalData">

	<form:hidden path="id" />
	<form:hidden path="version" />


	<acme:textbox code="curricula.name" path="name" />
	<br />
	<acme:textarea code="curricula.middleName" path="middleName" />
	<br />
	<acme:textarea code="curricula.surname" path="surname"/>
	<br />
	<acme:textbox code="curricula.statement" path="statement" />
	<br />
	<acme:textarea code="curricula.githubProfile" path="githubProfile" />
	<br />
	<acme:textarea code="curricula.linkedinProfile" path="linkedinProfile"/>
	<br />
	<acme:textarea code="curricula.phone" path="phone"/>
	<br />
	<acme:submit name="save" code="curricula.save" />
	<acme:cancel code="curricula.cancel"
		url="curricula/list.do" />


</form:form>