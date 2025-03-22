<!DOCTYPE html>
<html>

<head>
<title>AlumniNet</title>
</head>

<body>
	<h2>Register here</h2>
	<form action="regForm" method="post">
		<label for="name">Full name:</label><input type="text" id="name" name="name"><br> 
		<label for="college">college:</label><input type="text" id="college" name="college"><br> 
		<label for="email">email:</label><input type="email" id="email" name="email"><br>
		<label for="password">password:</label><input type="password" id="password" name="password"><br> 
		<input type="submit" value="Submit">
	</form>
	<h5>
		Already registered<a href="loginPage">Click here</a>
	</h5>

	<c:if test="${not empty successMsg}">
		<h4 style="color: green">${successMsg}</h4>
	</c:if>
	<c:if test="${not empty errorMsg}">
		<h4 style="color: red">${errorMsg}</h4>
	</c:if>
</body>

</html>