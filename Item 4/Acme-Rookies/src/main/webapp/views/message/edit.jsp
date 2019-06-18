
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

<form:form action="message/create.do" modelAttribute="msg">
	
	<form:hidden path="sender"/>
	<form:hidden path="moment"/>
	
	<acme:textbox code="message.subject" path="subject"/>
	
	<acme:textarea code="message.body" path="body"/>
	
	<acme:select items="${actors}" itemLabel="userAccount.username" code="message.recipient" path="recipient"/>

	<acme:textarea code="message.tags" path="tags"/>
	
	<acme:submit name="save" code="message.save"/>
		
	<acme:cancel url="message/list.do?tag=" code="message.cancel"/>	


</form:form>