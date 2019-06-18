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

<form:form action="nuevaEntidadXXX/company/edit.do"
	modelAttribute="nuevaEntidadXXX">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<jstl:choose>
	<jstl:when test="${id == 0}">
	<form:hidden path="audit" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	</jstl:when>
	</jstl:choose>
	
	<acme:textarea code="nuevaEntidadXXX.body" path="body"/>
	<br />
	
	<acme:textbox code="nuevaEntidadXXX.picture" path="picture"/>
	<br />
	
	<form:label path="draftMode">
		<spring:message code="nuevaEntidadXXX.draftMode" />:
	</form:label>
	<INPUT TYPE="radio" name="draftMode" value="true" checked="checked" />True
	<INPUT TYPE="radio" NAME="draftMode" value="false" />False
	<br />
	
	<acme:submit name="save" code="nuevaEntidadXXX.save" />
	<jstl:choose>
	<jstl:when test="${id != 0}">
		<acme:submit name="delete" code="nuevaEntidadXXX.delete" />
	</jstl:when>
	</jstl:choose>
	
	<acme:cancel code="nuevaEntidadXXX.cancel" url="nuevaEntidadXXX/company/list.do" />
	

</form:form>