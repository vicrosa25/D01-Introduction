<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" 	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" 	uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" 	uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" 	tagdir="/WEB-INF/tags" %>

<form:form action="${actionURI}" modelAttribute="request">

	<%-- Hidden properties --%>
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="member" />
	
	<security:authorize access="hasRole('MEMBER')">
	    <form:hidden path="status" />
	    <form:hidden path="reason" />
	    <form:hidden path="assignedRow" />
	    <form:hidden path="assignedColumn" />
	</security:authorize>
    
    <security:authorize access="hasRole('BROTHERHOOD')">
        <form:hidden path="procession" />
    </security:authorize>
		
		
	<%-- MEMBER --%>
	<security:authorize access="hasRole('MEMBER')">
	    <!-- Select Procession -->
		<acme:select items="${ processions }" itemLabel="title" code="request.procession" path="procession"/>
	</security:authorize>
	<br>
	<br>

	
	<%-- BROTHERHOOD --%>
    <security:authorize access="hasRole('BROTHERHOOD')">
        <!-- Select Status -->
         <form:label path="status">
             <spring:message code="request.status"/>
             </form:label>
         <form:select id="status" path="status">
             <option value="${'APPROVED'}">
                    <spring:message code="request.status.approved"/>
                </option>
                <option value="${'REJECTED'}">
                    <spring:message code="request.status.rejected"/>
                </option>
         </form:select>
         <form:errors Class="error" path="status"/>
         <br/>

        <%-- assignedRow--%>
        <acme:textbox code="request.assignedRow" path="assignedRow" />
        <br>

        <%-- assignedColumn--%>
        <acme:textbox code="request.assignedColumn" path="assignedColumn" />
        <br>

        <%-- reason --%>
        <acme:textbox code="request.reason" path="reason" />
        <br>
    </security:authorize>

	
	
	<%-- Buttons --%>
	<input type="submit" name="save" value="<spring:message code="request.save"/>"/>
	<acme:cancel code="request.cancel" url="/" />
	
	
</form:form>









