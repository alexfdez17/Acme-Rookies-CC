<%--
 * dashboard.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h2>
	<spring:message code="administrator.positionsPerCompany" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumppc}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumppc}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averageppc}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevppc}
</p>

<h2>
	<spring:message code="administrator.applicationsPerRookie" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumaph}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumaph}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averageaph}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevaph}
</p>

<h2>
	<spring:message code="administrator.morePositions" />
</h2>

<jstl:forEach var="company" items="${morePositions}">

${company.name} ${company.surname} ${company.comercialName}
<br>
</jstl:forEach>

<h2>
	<spring:message code="administrator.moreApplications" />
</h2>

<jstl:forEach var="rookie" items="${moreApplications}">

${rookie.name} ${rookie.surname}
<br>
</jstl:forEach>

<h2>
	<spring:message code="administrator.salariesOffered" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumso}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumso}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averageso}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevso}
</p>

<h2>
	<spring:message code="administrator.best" />
</h2>
${better.title} ${better.salaryOffered}

<h2>
	<spring:message code="administrator.worst" />
</h2>
${worst.title} ${worst.salaryOffered}

<h2>
	<spring:message code="administrator.curriculaPerRookie" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumcph}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumcph}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averagecph}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevcph}
</p>

<h2>
	<spring:message code="administrator.finderResult" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumfr}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumfr}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averagefr}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevfr}
</p>

<h2>
	<spring:message code="administrator.emptyfullRatio" />
</h2>
${empvsful}

<h2>
	<spring:message code="administrator.auditScore" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumps}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumps}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averageps}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevps}
</p>

<h2>
	<spring:message code="administrator.auditScoreCompany" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumcs}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumcs}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averagecs}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevcs}
</p>

<h2>
	<spring:message code="administrator.highScore" />
</h2>

<jstl:forEach var="company" items="${highestScore}">

${company.name} ${company.surname} ${company.comercialName} ${company.auditScore}
<br>
</jstl:forEach>

<h2>
	<spring:message code="administrator.avgSalaryTopPositions" />
</h2>
${avgSalaryTop}

<h2>
	<spring:message code="administrator.itemsPerProvider" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumipp}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumipp}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averageipp}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevipp}
</p>

<h2>
	<spring:message code="administrator.topProviders" />
</h2>

<jstl:forEach var="provider" items="${topProviders}">

${provider.name} ${provider.surname}
<br>
</jstl:forEach>

<h2>
	<spring:message code="administrator.sponsorshipsProvider" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumspp}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumspp}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averagespp}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevspp}
</p>

<h2>
	<spring:message code="administrator.sponsorshipsPosition" />
</h2>

<p>
	<b><spring:message code="administrator.maximum" /> :</b> ${maximumsppo}
</p>
<p>
	<b><spring:message code="administrator.minimum" /> :</b> ${minimumsppo}
</p>
<p>
	<b><spring:message code="administrator.average" /> :</b> ${averagesppo}
</p>
<p>
	<b><spring:message code="administrator.stdev" /> :</b> ${stdevsppo}
</p>

<h2>
	<spring:message code="administrator.sponsorshipsProviderMAVG" />
</h2>

<jstl:forEach var="provider" items="${providersMAVG}">

${provider.name} ${provider.surname}
<br>
</jstl:forEach>