
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

<form:form action="finder/rookie/edit.do" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="finderDate" />

	<acme:textbox code="finder.keyword" path="keyword" />
	<acme:textbox code="finder.minimumSalary" path="minimumSalary" />

	<form:label path="maximumDeadLine">
		<spring:message code="finder.maximumDeadline" />:
	</form:label>
	<form:input type="date" path="maximumDeadLine"
		format="{0,date,dd/MM/yyyy}" />
	<br />

	<acme:submit name="save" code="finder.save" />
	<br />

	<a href="finder/rookie/clean.do"><spring:message
			code="finder.clean" /></a>

	<!--  Listing grid -->

	<display:table pagesize="10" class="displaytag" name="positions"
		requestURI="${requestURI}" id="row">

		<!-- Attributes -->

		<spring:message code="position.ticker" var="tickerHeader" />
		<display:column property="ticker" title="${tickerHeader}"
			sortable="false" />

		<spring:message code="position.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}"
			sortable="true" />

		<spring:message code="position.deadline" var="deadLineHeader" />
		<display:column property="deadLine" title="${deadLineHeader}"
			sortable="true" />

		<display:column>
			<a href="position/display.do?positionId=${row.id}"> <spring:message
					code="position.display" />
			</a>
		</display:column>
	</display:table>

</form:form>