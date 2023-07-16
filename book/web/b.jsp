<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%
        out.println("myName"+request.getParameter("myName"));
        out.println("myPass"+request.getParameter("myPass"));
    %>
</body>
</html>
