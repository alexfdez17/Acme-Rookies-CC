<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<b><spring:message code="message.subject"/></b>
<jstl:out value="${displayednotification.subject}"/>
<br/>

<b><spring:message code="message.body"/></b>
<jstl:out value="${displayednotification.body}"/>
<br/>


<b><spring:message code="message.moment"/></b>
<fmt:formatDate value="${displayednotification.moment}" pattern="yyyy/MM/dd"/>
<br/>

<b><spring:message code="message.tags"/></b>
<br/>
<jstl:forEach var="tag" items="${displayednotification.tags}">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<jstl:out value="${tag}"/>
	<br/>
</jstl:forEach>
<br/>

<b><spring:message code="message.sender"/></b>
<jstl:out value="${displayednotification.administrator.userAccount.username}"/>
<br/>




