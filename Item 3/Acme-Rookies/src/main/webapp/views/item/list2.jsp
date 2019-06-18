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


<display:table pagesize="5" class="displaytag" name="items"
	requestURI="item/provider/list.do" id="row">

	<!-- Attributes -->
	
	<spring:message code="item.name" var="positionHeader" />
	<display:column property="name" title="${positionHeader}"
		sortable="false" />

	<!-- Actions -->
		
	<display:column>
		<a href="item/display.do?itemId=${row.id}"> <spring:message
				code="item.display" />
		</a>
	</display:column>	
	
	<display:column>
		<a href="actor/displayProvider.do?actorId=${row.provider.id}"> <spring:message
				code="item.display.provider" />
		</a>
	</display:column>
	
</display:table>
<br/>
