<%@ page contentType="text/html;charset=UTF-8" %>
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
<body onload='document.loginForm.username.focus();'>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<tag:links linkTo="dashboard"/>"> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="login.header" text="Login" /></h1>
						<c:url var="loginUrl" value="/login" />
						<form:form action="${loginUrl}" modelAttribute="user" method="post" class="form-horizontal">
                        <fieldset>
                            <div class="form-group">
                                <label for="username">Username</label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
                            </div>
                            <div class="form-group">
                                <label for="password">Password</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                            </div>              
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" name="submit" id="submit" value="<spring:message code="login.header" text="Login" />" class="btn btn-primary">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="_csrf" />
                    </form:form>
                </div>
            </div>
        </div>
    </section>
	<script src="<c:url value="/static/js/jquery.min.js" />"></script>
	<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>
</body>
</html>