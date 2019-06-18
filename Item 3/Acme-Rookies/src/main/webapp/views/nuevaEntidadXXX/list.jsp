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

<display:table pagesize="5" class="displaytag" name="nuevaEntidadXXXs"
	requestURI="${requestURI}" id="row">

	<!-- Attributes -->
	<security:authorize access="hasRole('AUDITOR')">
	<spring:message code="nuevaEntidadXXX.company" var="companyHeader" />
	<display:column property="audit.position.company.name" title="${companyHeader}"
		sortable="false" />
	</security:authorize>
	
	<spring:message code="nuevaEntidadXXX.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="false" />
	
	<jstl:choose>
		<jstl:when test="${row.moment < onemonth}">
			<spring:message code="nuevaEntidadXXX.moment" var="momentHeader" />
			<display:column property="moment" title="${momentHeader}"
				sortable="false" style="background-color:#4B0082"/>
		</jstl:when>
		<jstl:when test="${row.moment > twomonths}">
			<spring:message code="nuevaEntidadXXX.moment" var="momentHeader" />
			<display:column property="moment" title="${momentHeader}"
				sortable="false" style="background-color:#FFEFD5"/>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="nuevaEntidadXXX.moment" var="momentHeader" />
			<display:column property="moment" title="${momentHeader}"
				sortable="false" style="background-color:#2F4F4F"/>
		</jstl:otherwise>
	</jstl:choose>

	<spring:message code="nuevaEntidadXXX.draftMode" var="draftModeHeader" />
	<display:column property="draftMode" title="${draftModeHeader}"
		sortable="false" />
		
	<!-- Actions -->
	
	<security:authorize access="hasRole('COMPANY')">	
	<display:column>
		<a href="nuevaEntidadXXX/company/display.do?nuevaEntidadXXXId=${row.id}"> <spring:message
				code="nuevaEntidadXXX.display" />
		</a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('AUDITOR')">	
	<display:column>
		<a href="nuevaEntidadXXX/auditor/display.do?nuevaEntidadXXXId=${row.id}"> <spring:message
				code="nuevaEntidadXXX.display" />
		</a>
	</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('COMPANY')">
	<display:column>
	<jstl:choose>
	<jstl:when test="${row.draftMode == true}">
		<a href="nuevaEntidadXXX/company/edit.do?nuevaEntidadXXXId=${row.id}"> <spring:message
				code="nuevaEntidadXXX.edit" />
		</a>
	</jstl:when>
	</jstl:choose>
	</display:column>
	</security:authorize>
	
</display:table>
<br/>
