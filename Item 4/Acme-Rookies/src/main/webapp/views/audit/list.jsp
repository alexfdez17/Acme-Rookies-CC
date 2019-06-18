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

<display:table pagesize="5" class="displaytag" name="audits"
	requestURI="audit/auditor/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="audit.position" var="positionHeader" />
	<display:column property="position.ticker" title="${positionHeader}"
		sortable="false" />
	
	<spring:message code="audit.score" var="scoreHeader" />
	<display:column property="score" title="${scoreHeader}"
		sortable="false" />

	<spring:message code="audit.draftMode" var="draftModeHeader" />
	<display:column property="draftMode" title="${draftModeHeader}"
		sortable="false" />
		
	<!-- Actions -->
		
	<display:column>
		<a href="audit/auditor/display.do?auditId=${row.id}"> <spring:message
				code="audit.display" />
		</a>
	</display:column>
	
	<display:column>
	<jstl:choose>
	<jstl:when test="${row.draftMode == true}">
		<a href="audit/auditor/edit.do?auditId=${row.id}"> <spring:message
				code="audit.edit" />
		</a>
	</jstl:when>
	</jstl:choose>
	</display:column>
	
</display:table>
<br/>
