<!DOCTYPE html>
<html>

<head>
<title>AlumniNet</title>
</head>

<body>
	<h2>Login</h2>
	<form action="loginForm" method="post">
	<label for="email">email:</label><input type="email" id="email" name="email"><br>
	<label for="password">password:</label><input type="password" id="password" name="password"><br> 
	<input type="submit" value="Submit">
	</form>
	<h5>
		Not registered yet<a href="regPage">Click here</a>
	</h5>
	<c:if test="${not empty errorMsg}">
		<h4 style="color: red">${errorMsg}</h4>
	</c:if>
</body>

</html>