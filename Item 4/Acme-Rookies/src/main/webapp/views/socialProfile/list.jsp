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

<!--  Listing grid -->

<display:table pagesize="5" class="displaytag" name="socialProfiles"
	requestURI="socialProfile/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="socialProfile.nick" var="nickHeader" />
	<display:column property="nick" title="${nickHeader}"
		sortable="false" />
	
	<spring:message code="socialProfile.socialNetwork" var="socialNetworkHeader" />
	<display:column property="socialNetwork" title="${socialNetworkHeader}"
		sortable="false" />

	<spring:message code="socialProfile.profileLink" var="profileLinkHeader" />
	<display:column property="profileLink" title="${profileLinkHeader}"
		sortable="false" />
		
	<!-- Actions -->
		
	<display:column>
		<a href="socialProfile/display.do?socialProfileId=${row.id}"> <spring:message
				code="socialProfile.display" />
		</a>
	</display:column>
	
	<display:column>
		<a href="socialProfile/edit.do?socialProfileId=${row.id}"> <spring:message
				code="socialProfile.edit" />
		</a>
	</display:column>
	
</display:table>

		<a href="socialProfile/create.do"> <spring:message
				code="socialProfile.create" />
		</a>


<br/>
