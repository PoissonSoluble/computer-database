<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="static/css/bootstrap.min.css" />"
	rel="stylesheet" media="screen">
<link href="<c:url value="static/css/font-awesome.css" />"
	rel="stylesheet" media="screen">
<link href="<c:url value="static/css/main.css" />" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<tag:links linkTo="dashboard"/>">
				Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<tag:addError />
					<h1><spring:message code="addComputer.title"/></h1>
					<form action="<tag:links linkTo="addComputer"/>" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message code="computer.computerName"/></label> <input
									type="text" data-validation="alphanumeric"
									data-validation-allowing="-_ " class="form-control" name="name"
									id="name" placeholder="<spring:message code="computer.computerName"/>" required>
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message code="computer.introduced"/></label> <input
									type="date" data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" class="form-control"
									name="introduced" id="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message code="computer.discontinued"/></label> <input
									type="date" type="date" data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" class="form-control"
									name="discontinued" id="discontinued"
									placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="computer.company"/></label> <select
									class="form-control" id="companyId" name="companyId">
									<option value=""><spring:message code="computer.companyNone"/></option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="computer.addButton"/>" class="btn btn-primary">
							<spring:message code="computer.or"/> 
							<a href="dashboard.html" class="btn btn-default"><spring:message code="computer.cancelButton"/></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/static/js/jquery.min.js" />"></script>
	<script src="<c:url value="/static/js/jquery.form-validator.min.js" />"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/static/js/dashboard.js" />"></script>
	<script>
		$.validate();
	</script>
</body>

</html>