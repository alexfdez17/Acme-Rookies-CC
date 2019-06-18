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

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" name="companies"
	requestURI="${requestURI}" id="row">


	<!-- Attributes -->

	<spring:message code="company.comercialName" var="nameHeader" />
	<display:column property="comercialName" title="${nameHeader}" />

	


	<!-- Actions -->

	<display:column>
		<a href="company/display.do?companyId=${row.id}"> <spring:message
				code="company.display" />
		</a>
	</display:column>



</display:table>

<br />