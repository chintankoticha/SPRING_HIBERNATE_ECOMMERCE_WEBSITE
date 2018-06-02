<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>HomePage</title>
<style>
body {
	width: 100%;
	height: auto;
	background-size: 100%;
	background-image: url("resources/images/ecommerce.jpg");
	background-repeat: no-repeat;
	background-position: center;
	background-attachment: fixed;
}

.li li {
	float: right;
	display: inline-block;
	background-color: #EBF5FB;
	text-align: center;
	padding: 2px 16px;
	text-decoration: underline;
	font-size: 16px;
}

li a:hover {
	background-color: #E6B0AA;
}

.active li {
	background-color: #3498DB;
}
</style>
</head>
<body>
	<header>
		<nav>
			<ul class="li">
				<li><a href="home.htm">HOME</a></li>
				<li><a href="userlogin.htm">LOGIN/SIGNUP</a></li>
				<li><a href="aboutus.htm">ABOUT</a></li>
				<li><a href="contactus.htm">CONTACT US</a></li>
			</ul>
		</nav>
		<h3>Welcome to one stop shop</h3>
	</header>

	<p></p>
</body>
</html>
