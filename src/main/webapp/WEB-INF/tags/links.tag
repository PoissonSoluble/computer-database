<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="linkTo" required="true"%>
<%@attribute name="pageNumberAtt" required="false"%>
<%@attribute name="pageSizeAtt" required="false"%>

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
			<c:when test="${ linkTo.equals('addComputer') }">
				<c:set var="tmpPath" value="${ tmpPath.concat('/cdb/addComputer') }" />
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:out value="${ tmpPath.concat('/cdb/dashboard') }" />
	</c:otherwise>
</c:choose>

<c:if test="${not empty pageNumberAtt or pageNumberAtt > 0}">
	<c:if test="${pageNumberAtt.matches('[0-9]+') }">
		<c:set var="tmpPageNb"
			value="${ emptyText.concat('?pageNumber=').concat(pageNumberAtt) }" />
	</c:if>
</c:if>

<c:if test="${ not empty pageSizeAtt or pageSizeAtt > 0}">
	<c:if test="${ pageSizeAtt.matches('[0-9]+') }">
		<c:if test="${ not empty tmpPageNb }">
			<c:set var="tmpPageSize"
				value="${ emptyText.concat('&pageSize=').concat(pageSizeAtt) }" />
		</c:if>
	</c:if>
</c:if>

<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageNb) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageSize) }" />


<c:out value="${ tmpPath }" escapeXml="false" />
<%-- --%>