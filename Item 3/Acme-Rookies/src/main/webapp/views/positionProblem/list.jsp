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

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" name="problems"
	requestURI="positionProblem/company/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="positionProblem.position" var="positionHeader" />
	<display:column property="position.ticker" title="${positionHeader}"
		sortable="false" />
	
	<spring:message code="positionProblem.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="false" />

	<spring:message code="positionProblem.statement" var="statementHeader" />
	<display:column property="statement" title="${statementHeader}"
		sortable="false" />
		
	<spring:message code="positionProblem.draftMode" var="draftModeHeader" />
	<display:column property="draftMode" title="${draftModeHeader}"
		sortable="false" />
		
	<!-- Actions -->
		
	<display:column>
		<a href="positionProblem/company/display.do?positionProblemId=${row.id}"> <spring:message
				code="positionProblem.display" />
		</a>
	</display:column>
	<display:column>
	<jstl:choose>
		<jstl:when test="${row.draftMode == true}">
			
				<a href="positionProblem/company/edit.do?positionProblemId=${row.id}"> <spring:message
				code="positionProblem.edit" />
				</a>
			
		</jstl:when>
	</jstl:choose>
	</display:column>
</display:table>
<br/>
