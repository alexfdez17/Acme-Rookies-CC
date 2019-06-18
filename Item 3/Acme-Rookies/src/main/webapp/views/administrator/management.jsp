+<%--
 * management.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="actors" id="row"
	requestURI="administrator/management.do" pagesize="5"
	class="displaytag">

	<display:column titleKey="administrator.actorName">
		
		<jstl:choose>
			<jstl:when test="${fn:contains(row.userAccount.authorities, authH)}">
				<a href="actor/displayRookie.do?actorId=${row.id}">${row.name} ${row.surname}</a>
			</jstl:when>
			<jstl:when test="${fn:contains(row.userAccount.authorities, authC)}">
				<a href="actor/displayCompany.do?actorId=${row.id}">${row.name} ${row.surname}</a>
			</jstl:when>
			<jstl:otherwise>
				${row.name} ${row.surname}
			</jstl:otherwise>
		</jstl:choose>
		
		<jstl:if test="${!row.userAccount.accountNonLocked}">
			(<spring:message code="administrator.banned" />)
		</jstl:if>

	</display:column>

	<display:column property="spammer" titleKey="administrator.spammer" />

	<display:column>
		<jstl:if
			test="${(row.spammer)&& row.userAccount.accountNonLocked}">
			<a href="administrator/ban.do?actorId=${row.id}"> <spring:message
					code="administrator.ban" />
			</a>
		</jstl:if>
	</display:column>


	<display:column>
		<jstl:if test="${!row.userAccount.accountNonLocked}">
			<a href="administrator/unban.do?actorId=${row.id}"> <spring:message
					code="administrator.unban" />
			</a>
		</jstl:if>
	</display:column>

</display:table>

<jstl:if test="${showErrorBan == true}">
	<div class="error">
		<spring:message code="administrator.ban.failed" />
	</div>
</jstl:if>

<jstl:if test="${showErrorUnban == true}">
	<div class="error">
		<spring:message code="administrator.unban.failed" />
	</div>
</jstl:if>