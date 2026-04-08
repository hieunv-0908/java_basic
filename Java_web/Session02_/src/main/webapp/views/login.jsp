<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>Login Page</h2>

<form action="login" method="post">
    Username: <input type="text" name="username" /><br/>
    Password: <input type="password" name="password" /><br/>
    <button type="submit">Login</button>
</form>

<p style="color:red;">
    ${error}
</p>

</body>
</html>