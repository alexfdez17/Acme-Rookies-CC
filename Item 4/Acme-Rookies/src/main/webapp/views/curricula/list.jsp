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

<display:table pagesize="5" class="displaytag" name="curriculas"
	requestURI="${requestURI}" id="row">


	<spring:message code="curricula.statement" var="statementHeader" />
	<display:column property="personalData.statement" title="${statementHeader}" />

	<display:column>
		<a href="curricula/display.do?curriculaId=${row.id}"> <spring:message
				code="curricula.display" />
		</a>
	</display:column>
	<display:column>
		<a href="curricula/delete.do?curriculaId=${row.id}"> <spring:message
				code="curricula.delete" />
		</a>
	</display:column>

</display:table>

<a href="curricula/personalData/create.do"> <spring:message
		code="curricula.create" />
</a>
<br />

<br />