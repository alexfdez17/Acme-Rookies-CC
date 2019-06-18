
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


<form:form action="position/company/edit.do" modelAttribute="position">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="company" />
	<form:hidden path="ticker" />


	<acme:textbox code="position.title" path="title" />
	<acme:textbox code="position.description" path="description" />
	<acme:textbox code="position.deadline" path="deadLine" />
	<acme:textbox code="position.salary" path="salaryOffered" />
	<acme:textbox code="position.skillsRequired" path="skillsRequired" />
	<acme:textbox code="position.profileRequired" path="profileRequired" />
	<acme:textbox code="position.technologiesRequired"
		path="technologiesRequired" />
	<form:label path="status">
		<spring:message code="position.status" />:
	</form:label>
	<INPUT TYPE="radio" name="status" value="final" checked="checked" />final
	<INPUT TYPE="radio" NAME="status" value="draft" />draft
	<br />
	<br />
	<spring:message code="position.problems"/>


	<acme:submit name="save" code="position.save" />

	<acme:cancel url="/position/company/list.do" code="position.cancel" />
</form:form>