<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@attribute name="linkTo" required="true"%>
<%@attribute name="pageNumberAtt" required="false"%>
<%@attribute name="pageSizeAtt" required="false"%>
<%@attribute name="computerIdAtt" required="false"%>
<%@attribute name="searchAtt" required="false"%>
<%@attribute name="orderAtt" required="false"%>
<%@attribute name="ascendingAtt" required="false"%>

<c:set var="emptyText" value="" />
<c:set var="tmpPath" value="" />
<c:set var="tmpPageNb" value="" />
<c:set var="tmpPageSize" value="" />
<c:set var="tmpComputerId" value="" />
<c:set var="tmpSearch" value="" />
<c:set var="tmpOrder" value="" />
<c:set var="tmpAscending" value="" />

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
			<c:when test="${ linkTo.equals('deleteComputer') }">
				<c:set var="tmpPath" value="${ tmpPath.concat('/cdb/deleteComputer') }" />
			</c:when>
			<c:when test="${ linkTo.equals('editComputer') }">
				<c:set var="tmpPath" value="${ tmpPath.concat('/cdb/editComputer') }" />
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


<c:if test="${not empty computerIdAtt}">
	<c:if test="${computerIdAtt.matches('[0-9]+') }">
		<c:set var="tmpComputerId"
			value="${ emptyText.concat('?computerId=').concat(computerIdAtt) }" />
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

<c:if test="${ not empty searchAtt}">
	<c:if test="${ not empty tmpPageNb }">
		<c:set var="tmpSearch"
			value="${ emptyText.concat('&search=').concat(searchAtt) }" />
	</c:if>
</c:if>

<c:if test="${ not empty orderAtt}">
	<c:if test="${ not empty tmpPageNb }">
		<c:set var="tmpOrder"
			value="${ emptyText.concat('&order=').concat(orderAtt) }" />
		<c:choose>
			<c:when test="${empty ascendingAtt}">
				<c:choose>
					<c:when test="${order.equals(orderAtt)}">
						<c:set var="tmpAscending"
							value="${ emptyText.concat('&ascending=').concat(not ascending) }" />
					</c:when>
					<c:otherwise>
						<c:set var="tmpAscending"
							value="${ emptyText.concat('&ascending=true') }" />
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<c:set var="tmpAscending"
					value="${ emptyText.concat('&ascending=').concat(ascendingAtt) }" />
			</c:otherwise>
		</c:choose>
	</c:if>
</c:if>


<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageNb) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageSize) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpComputerId) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpSearch) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpOrder) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpAscending) }" />


<c:out value="${ tmpPath }" escapeXml="false" />
<%-- --%>