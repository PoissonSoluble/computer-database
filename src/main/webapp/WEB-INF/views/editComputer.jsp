<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
            <a class="navbar-brand" href="<tag:links linkTo="dashboard"/>"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${computer.id}
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="<tag:links linkTo="editComputer"/>" method="POST">
                        <input type="hidden" value="${computer.id}" name="id" id="id"/>
                        <fieldset>
                            <div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" data-validation="alphanumeric"
									data-validation-allowing="-_ " class="form-control" name="name"
									id="name" value="${computer.name}" required>
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" class="form-control"
									name="introduced" id="introduced" value="${computer.introduced}">
							</div>
							<div class="form-group">
						
	<script src="<c:url value="/static/js/jquery.min.js" />"></script>
	<script src="<c:url value="/static/js/jquery.form-validator.min.js" />"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
	<script src="<c:url value="/static/js/dashboard.js" />"></script>
	<script>
		$.validate();
	</script>		<label for="discontinued">Discontinued date</label> <input
									type="date" type="date" data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" class="form-control"
									name="discontinued" id="discontinued"
									value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="">None</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
                                        <c:if test="${company.id == computer.company.id}">
                                            <c:out value="selected"/>
                                        </c:if>>
                                        ${company.name}</option>
									</c:forEach>
								</select>
							</div>          
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard.html" class="btn btn-default">Cancel</a>
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