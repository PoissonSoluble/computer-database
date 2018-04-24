<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>

<c:set var="start" scope="page" value="${ pageNumber - 3 }" />
<c:set var="stop" scope="page" value="${ pageNumber + 3 }" />

<c:if test="${start < 1}">
	<c:set var="start" scope="page" value="${1}" />
</c:if>
<c:if test="${stop > totalPage}">
	<c:set var="stop" scope="page" value="${totalPage}" />
</c:if>

<c:if test="${pageNumber != 1}">
	<li>
		<a
		href="<tag:links linkTo="dashboard" pageNumberAtt="${pageNumber - 1}" pageSizeAtt="${pageSize }" 
		searchAtt="${search}" orderAtt="${order}"  ascendingAtt="${ascending}"/>"
		aria-label="Previous"> 
			<span aria-hidden="true">&laquo;</span>
		</a>
	</li>
</c:if>

<c:forEach var="i" begin="${ start }" end="${ stop }" step="1">
	<li>
		<a
		href="<tag:links linkTo="dashboard" pageNumberAtt="${i}" pageSizeAtt="${pageSize}" 
		searchAtt="${search}" orderAtt="${order}"  ascendingAtt="${ascending}"/>">
			<c:out value="${i}" />
		</a>
	</li>
</c:forEach>

<c:if test="${ pageNumber != totalPage }">
	<li>
		<a
		href="<tag:links linkTo="dashboard" pageNumberAtt="${pageNumber + 1}" pageSizeAtt="${pageSize }" 
		searchAtt="${search}" orderAtt="${order}"  ascendingAtt="${ascending}"/>"
		aria-label="Next"> 
			<span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</c:if>
