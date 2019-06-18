<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="#"><img src="${bannerUrl}" alt="Acme Rookies Co., Inc." height="200px" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/displayAdmin.do"><spring:message code="master.page.administrator.display" /></a></li>
					<li><a href="actor/editAdmin.do"><spring:message code="master.page.administrator.edit" /></a></li>
					<li><a href="administrator/dashboard.do"><spring:message code="master.page.administrator.dashboard" /></a></li>
					<li><a href="administrator/management.do"><spring:message code="master.page.administrator.management" /></a></li>
					<li><a href="systemConfig/administrator/edit.do"><spring:message code="master.page.administrator.systemConfig" /></a></li>	
					<li><a href="administrator/getSpammers.do"><spring:message code="master.page.administrator.spammers" /></a></li>
					<li><a href="actor/registerAdministrator.do"><spring:message code="master.page.administrator.register" /></a></li>
					<li><a href="actor/registerAuditor.do"><spring:message code="master.page.auditor.register" /></a></li>
					<li><a href="administrator/computeAuditScore.do"><spring:message code="master.page.administrator.computeAuditScore" /></a></li>				
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ROOKIE')">
			<li><a class="fNiv"><spring:message	code="master.page.rookie" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/displayRookie.do"><spring:message code="master.page.rookie.display" /></a></li>
					<li><a href="actor/editRookie.do"><spring:message code="master.page.rookie.edit" /></a></li>	
				</ul>
			</li>
			<li><a class="fNiv" href="finder/rookie/edit.do"><spring:message code="master.page.rookie.finder" /></a></li>
			<li><a class="fNiv" href="application/rookie/list.do?Status="><spring:message code="master.page.listApplication" /></a></li>
			<li><a class="fNiv" href="curricula/list.do"><spring:message code="master.page.rookie.curricula" /></a></li>
			
			
		</security:authorize>
		
		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message	code="master.page.company" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/displayCompany.do"><spring:message code="master.page.company.display" /></a></li>			
					<li><a href="actor/editCompany.do"><spring:message code="master.page.company.edit" /></a></li>	
					<li><a href="positionProblem/company/list.do"><spring:message code="master.page.company.problem" /></a></li>
					<li><a href="position/company/list.do"><spring:message code="master.page.company.position" /></a></li>
					<li><a href="nuevaEntidadXXX/company/list.do"><spring:message code="master.page.nuevaEntidadXXX" /></a></li>
										
				</ul>
			</li>
			<li><a class="fNiv" href="application/company/list.do?Status="><spring:message code="master.page.listApplication" /></a></li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/displayAuditor.do"><spring:message code="master.page.auditor.display" /></a></li>			
					<li><a href="actor/editAuditor.do"><spring:message code="master.page.auditor.edit" /></a></li>	
					<li><a href="audit/auditor/list.do"><spring:message code="master.page.auditor.audit" /></a></li>
					<li><a href="nuevaEntidadXXX/auditor/list.do"><spring:message code="master.page.nuevaEntidadXXX" /></a></li>
										
				</ul>
			</li>
			
		</security:authorize>
		
		<security:authorize access="hasRole('PROVIDER')">
			<li><a class="fNiv"><spring:message	code="master.page.provider" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/displayProvider.do"><spring:message code="master.page.provider.display" /></a></li>			
					<li><a href="actor/editProvider.do"><spring:message code="master.page.provider.edit" /></a></li>	
				</ul>
			</li>
			<li><a class="fNiv" href="item/provider/list.do"><spring:message code="master.page.provider.item" /></a></li>
			<li><a class="fNiv" href="sponsorship/provider/list.do"><spring:message code="master.page.provider.sponsorship" /></a></li>
			
			
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href="actor/registerRookie.do"><spring:message code="master.page.registerRookie" /></a></li>
			<li><a class="fNiv" href="actor/registerCompany.do"><spring:message code="master.page.registerCompany" /></a></li>
			<li><a class="fNiv" href="actor/registerProvider.do"><spring:message code="master.page.registerProvider" /></a></li>
	</security:authorize>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="position/list.do"><spring:message code="master.page.position.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="company/list.do"><spring:message code="master.page.company.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="provider/list.do"><spring:message code="master.page.provider.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="item/list.do?providerId="><spring:message code="master.page.item.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="position/list.do"><spring:message code="master.page.position.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="company/list.do"><spring:message code="master.page.company.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="message/list.do?tag="><spring:message code="master.page.message.list" /></a></li>
		</security:authorize>
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="notification/list.do"><spring:message code="master.page.notification.list" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('COMPANY')">
					<li><a href="exportAndDeleteData/company/delete.do"><spring:message code="master.page.deleteData" /></a></li>
					<li><a href="exportAndDeleteData/company/export.do"><spring:message code="master.page.exportData" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ROOKIE')">
					<li><a href="exportAndDeleteData/rookie/delete.do"><spring:message code="master.page.deleteData" /></a></li>
					<li><a href="exportAndDeleteData/rookie/export.do"><spring:message code="master.page.exportData" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="exportAndDeleteData/administrator/delete.do"><spring:message code="master.page.deleteData" /></a></li>
					<li><a href="exportAndDeleteData/administrator/export.do"><spring:message code="master.page.exportData" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('AUDITOR')">
					<li><a href="exportAndDeleteData/auditor/delete.do"><spring:message code="master.page.deleteData" /></a></li>
					<li><a href="exportAndDeleteData/auditor/export.do"><spring:message code="master.page.exportData" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('PROVIDER')">
					<li><a href="exportAndDeleteData/provider/delete.do"><spring:message code="master.page.deleteData" /></a></li>
					<li><a href="exportAndDeleteData/provider/export.do"><spring:message code="master.page.exportData" /></a></li>
					</security:authorize>
					<li><a href="socialProfile/list.do"><spring:message code="master.page.socialProfile" /></a></li>			
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

