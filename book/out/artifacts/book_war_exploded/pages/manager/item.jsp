<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
</head>
<body>
<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">订单详情</span>
    <div>
        <a href="OrderServlet?action=listOrder">我的订单</a>
        <a href="UserServlet?action=logout">注销</a>
        <a href="index.jsp">返回</a>
    </div>
</div>
<div id="main">
    <table>
        <tr>
            <td>书名</td>
            <td>数量</td>
            <td>单价</td>
            <td>总价格</td>
        </tr>
        <c:forEach items="${page.items}" var="item">
            <tr>
                <td>${item.name}</td>
                <td>${item.count}</td>
                <td>${item.price}</td>
                <td>${item.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>
    <jsp:include page="/pages/common/page.jsp"></jsp:include>
</div>
<jsp:include page="/pages/common/bottom.jsp"></jsp:include>
</body>
</html>
