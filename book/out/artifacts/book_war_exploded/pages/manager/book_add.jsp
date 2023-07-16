<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>添加图书</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }

        input {
            text-align: center;
        }
    </style>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
%>
<jsp:include page="header.jsp">
    <jsp:param name="msg" value="添加图书"/>
</jsp:include>

<div id="main">
    <form action="BookServlet?action=add" enctype="multipart/form-data" method="post">
        <table style="margin-top: 50px">
            <%--            <tr>--%>
            <%--                <td>名称</td>--%>
            <%--                <td>价格</td>--%>
            <%--                <td>作者</td>--%>
            <%--                <td>销量</td>--%>
            <%--                <td>库存</td>--%>
            <%--                <td colspan="2">操作</td>--%>
            <%--            </tr>--%>
            <tr>
                <td>名称</td>
                <td><input name="name" type="text"/></td>
            </tr>
            <tr>
                <td>价格</td>
                <td><input name="price" id="price" type="text"/></td>
                <td><span id="error1"></span></td>
            </tr>
            <tr>
                <td>作者</td>
                <td><input name="author" type="text"/></td>
            </tr>
            <tr>
                <td>销量</td>
                <td><input name="sales" id="sales" type="text"/></td>
                <td><span id="error2"></span></td>
            </tr>
            <tr>
                <td>库存</td>
                <td><input name="stock" id="stock" type="text"/></td>
                <td><span id="error3"></span></td>
            </tr>
            <tr>
                <td>封面</td>
                <td><input name="imgPath" type="file" id="file_input" onchange="show_image()"/>
                    <img src="" alt="" id="show_img" width="100px" height="100px" style="display: none">
                </td>
            </tr>
            <td colspan="2"><input type="submit" value="提交"/></td>
        </table>
    </form>
</div>
<%@include file="/pages/common/bottom.jsp" %>
</body>
</html>
<script>
    function show_image() {
        //抓取到上传图片的input标签的信息
        file_input = document.getElementById("file_input");
        //抓取到需要展示预览图的img标签信息
        show_img = document.getElementById("show_img");
        //回去预览图的src属性信息
        show_img.src = window.URL.createObjectURL(file_input.files[0]);
        //改变style属性中block的值
        show_img.style.display = 'block';
    }

    var price = document.getElementById("price");
    var sales = document.getElementById("sales");
    var stock = document.getElementById("stock");
    var e1 = document.getElementById("error1");
    var e2 = document.getElementById("error2");
    var e3 = document.getElementById("error3");
    var a = /^[0-9]*$/;
    var b = /^[+-]?[0-9]+(\\.[0-9]+)?$/;

    price.onblur = function () {
        if (!a.test(price.value)) {
            e1.innerHTML = "请输入正小数";
            price.onfocus = function (){
                e1.innerHTML = "";
            }
        }
    }

    sales.onblur = function () {
        if (!b.test(sales.value)) {
            e2.innerHTML = "请输入正整数";
            sales.onfocus = function (){
                e2.innerHTML = "";
            }
        }
    }

    stock.onblur = function () {
        if (!c.test(stock.value)) {
            e3.innerHTML = "请输入正整数";
            stock.onfocus = function (){
                e3.innerHTML = "";
            }
        }
    }
</script>