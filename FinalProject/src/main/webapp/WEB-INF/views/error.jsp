<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:url value="http://localhost:8080/controller/logout.htm" var="myURL">
	</c:url>

	<form>
		<a style="float: right" href="${myURL}">LOGOUT</a>
	</form>
<h1>Error Page</h1>
<p>${errorMessage}</p>



</body>
</html>