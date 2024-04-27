<%--
  Created by IntelliJ IDEA.
  User: 79213
  Date: 18.04.2024
  Time: 06:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task Manager</title>
    <link rel="stylesheet" href="resources/login.css">
    <script src="resources/login.js" defer></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body onload="getRoles()">
<div class="login">
    <h1>Login</h1>
    <form method="post">
        <input type="text" name="u" placeholder="Username" required="required" />
        <input type="password" name="p" placeholder="Password" required="required" />
        <button type="submit" class="btn btn-primary btn-block btn-large">Let me in.</button>
        <p>Не зарегистрированы? <a class="to_register" href="javascript:void(0);">Регистрация</a></p>
    </form>
</div>
<div class="register">
    <h1>Register</h1>
    <form method="post">
        <input id="register_login" type="text" name="u" placeholder="Username" required="required" />
        <input id="register_pass" type="password" name="p" placeholder="Password" required="required" />
        <label for="role-select">Выберите роль:</label>
        <select name="Role" id="role-select">
            <option value="">--Ваша роль--</option>
        </select>
        <button type="button" class="btn btn-primary btn-block btn-large" onclick="doRegister()">Let me in.</button>
        <p>Зарегистрированы? <a class="to_login" href="javascript:void(0);">Вход</a></p>
    </form>
</div>
</body>
</html>
