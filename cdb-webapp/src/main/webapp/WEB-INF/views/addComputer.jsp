<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<spring:url value="/static/css/bootstrap.min.css" />"
	rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/font-awesome.css" />"
	rel="stylesheet" media="screen">
<link href="<spring:url value="/static/css/main.css" />" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<tag:links linkTo="dashboard"/>"> Application - Computer Database </a>
			<c:url var="logoutUrl" value="/logout" />
			<form:form action="${logoutUrl}" modelAttribute="user" method="post" class="form-horizontal">
				<input type="submit" value="Logout" style="float: right; margin-top: 8px;" class="btn btn-primary" />
			</form:form>
        </div>
	</header>


	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<tag:addError />
					<h1><spring:message code="addComputer.title"/></h1>					
					<form:form action="/cdb/computer/add" method="POST"
						modelAttribute="computerDTO" id="createComputerForm"
						name="createComputerForm">
						<fieldset>
							<div class="form-group">
								<form:label for="name" path="name"><spring:message code="computer.computerName"/></form:label>
								<spring:message code="computer.computerName" var="computerName"/>
								<form:input type="text" path="name"
									name="name" id="name" data-validation="alphanumeric"
									placeholder="${computerName}"
									data-validation-allowing="-_ " class="form-control"
									required="true" />
							</div>
							<div class="form-group">
								<form:label for="introduced" path="introduced"><spring:message code="computer.introduced"/></form:label>
								<form:input type="date" class="form-control" path="introduced"
									name="introduced" data-validation-optional="true"
									data-validation="date" data-validation-format="yyyy-mm-dd"
									id="introduced" placeholder="Introduced date" />
							</div>
							<div class="form-group">
								<form:label for="discontinued" path="discontinued"><spring:message code="computer.discontinued"/></form:label>
								<form:input type="date" class="form-control" path="discontinued"
									name="discontinued" id="discontinued" data-validation="date"
									data-validation-optional="true"
									data-validation-format="yyyy-mm-dd"
									placeholder="Discontinued date" />
							</div>
							<div class="form-group">
								<form:label for="companyId" path="company"><spring:message code="computer.company"/></form:label>
								<form:select class="form-control" name="companyId"
									id="companyId" path="company.id">
									<spring:message code="computer.companyNone" var="companyNone"/>
									<form:option value="0" label="${companyNone}" />
									<form:options items="${companies}" itemValue="id"/>
								</form:select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message code="computer.addButton"/>" class="btn btn-primary">
							<spring:message code="computer.or"/> 
							<a href="dashboard.html" class="btn btn-default"><spring:message code="computer.cancelButton"/></a>
						</div>
					</form:form>
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