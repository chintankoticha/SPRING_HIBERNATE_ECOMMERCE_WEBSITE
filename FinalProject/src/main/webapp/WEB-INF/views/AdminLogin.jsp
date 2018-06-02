<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ADMIN WEB LOGIN</title>
</head>
<body>
	<form action="j_security_check" method="POST">
		<TABLE>
			<TR>
				<TD>User name: <INPUT type="TEXT" name="j_username">
			<TR>
				<TD>Password: <INPUT type="PASSWORD" name="j_password">
			<TR>
				<TH><INPUT type="SUBMIT" value="Log In">
		</TABLE>
	</form>
</body>
</html>