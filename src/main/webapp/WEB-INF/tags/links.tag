<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="linkTo" required="true"%>
<%@attribute name="pageNumber" required="false"%>
<%@attribute name="pageSize" required="false"%>

<c:set var="emptyText" value="" />
<c:set var="tmpPath" value="" />
<c:set var="tmpPageNb" value="" />
<c:set var="tmpPageSize" value="" />

<%-- --%>
<c:choose>
	<c:when test="${not empty linkTo}">
		<c:choose>
			<c:when test="${ linkTo.equals('dashboard') }">
				<c:set var="tmpPath" value="${ tmpPath.concat('/cdb/dashboard') }" />
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:out value="${ tmpPath.concat('/cdb/dashboard') }" />
	</c:otherwise>
</c:choose>

<c:if test="${ not empty pageNumber and pageNumber.matches('[0-9]+') }">
	<c:set var="tmpPageNb"
		value="${ emptyText.concat('?pageNumber=').concat(pageNumber) }" />
</c:if>

<c:if test="${ not empty pageSize and pageSize.matches('[0-9]+') }">
	<c:if test="${ not empty tmpPageNb }">
		<c:set var="tmpPageSize"
			value="${ emptyText.concat('&pageSize=').concat(pageSize) }" />
	</c:if>
</c:if>

<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageNb) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageSize) }" />


<c:out value="${ tmpPath }" escapeXml="false" />
<%-- --%>