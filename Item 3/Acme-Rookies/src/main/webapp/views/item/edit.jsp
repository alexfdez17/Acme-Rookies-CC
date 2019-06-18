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

<form:form action="item/provider/edit.do"
	modelAttribute="item">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="provider" />
	
	<acme:textbox code="item.name" path="name"/>
	<br />
	<acme:textarea code="item.description" path="description"/>
	<br />
	<acme:textarea code="item.links" path="links"/>
	<br />
	<acme:textarea code="item.pictures" path="pictures"/>
	<br />
	
	<acme:submit name="save" code="item.save" />
	
	<acme:cancel code="item.cancel" url="item/provider/list.do" />
	

</form:form>