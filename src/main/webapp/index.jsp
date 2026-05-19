<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Головна - Лабораторна 1</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        li { margin: 10px 0; font-size: 18px; }
        a { color: #007bff; text-decoration: none; }
        a:hover { text-decoration: underline; }
    </style>
</head>
<body>
<h1>Лабораторна робота №1</h1>
<p>Оберіть потрібний шлях:</p>
<ul>
    <li><a href="${pageContext.request.contextPath}/name">Завдання 1: Виведення мого прізвища</a></li>
    <li><a href="${pageContext.request.contextPath}/system-info">Завдання 2: Деталі комп'ютера (RAM/CPU)</a></li>
</ul>
</body>
</html>