<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Orders</title>
</head>
<body>

<h2>
    Xin chào, ${sessionScope.loggedUser}!
    Vai trò: ${sessionScope.role}
</h2>

<table border="1">
    <tr>
        <th>Mã đơn</th>
        <th>Sản phẩm</th>
        <th>Tổng tiền</th>
        <th>Ngày đặt</th>
    </tr>

    <c:forEach var="o" items="${orders}">
        <tr>
            <td>${o.id}</td>
            <td>${o.name}</td>
            <td>
                <fmt:formatNumber value="${o.price}" type="currency" currencySymbol="₫"/>
            </td>
            <td>
                <fmt:formatDate value="${o.date}" pattern="dd/MM/yyyy"/>
            </td>
        </tr>
    </c:forEach>

</table>

<p>
    Tổng lượt xem đơn hàng toàn hệ thống:
    ${applicationScope.totalViewCount}
</p>

<a href="/logout">Đăng xuất</a>

</body>
</html>
