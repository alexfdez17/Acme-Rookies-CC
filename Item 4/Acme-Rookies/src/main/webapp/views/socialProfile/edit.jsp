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

<form:form action="socialProfile/edit.do"
	modelAttribute="socialProfile">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<jstl:choose>
	<jstl:when test="${id == 0}">
	<form:hidden path="actor" />
	</jstl:when>
	</jstl:choose>
	
	<acme:textbox code="socialProfile.nick" path="nick"/>
	<br />
	<acme:textbox code="socialProfile.socialNetwork" path="socialNetwork"/>
	<br />
	<acme:textbox code="socialProfile.profileLink" path="profileLink"/>
	<br />
	
	<acme:submit name="save" code="socialProfile.save" />
	<jstl:choose>
	<jstl:when test="${id != 0}">
		<acme:submit name="delete" code="socialProfile.delete" />
	</jstl:when>
	</jstl:choose>
	
	<acme:cancel code="socialProfile.cancel" url="socialProfile/list.do" />
	

</form:form>