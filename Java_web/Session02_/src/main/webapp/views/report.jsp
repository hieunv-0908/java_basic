<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Student Report</title>
</head>
<body>

<%-- Tiêu đề báo cáo --%>
<h2>Danh sách sinh viên</h2>

<table border="1">
    <tr>
        <th>Tên</th>
        <th>Điểm</th>
        <th>Kết quả</th>
    </tr>

    <c:forEach var="student" items="${students}">
        <tr>
            <td>
                <c:out value="${student.name}" />
            </td>
            <td>
                <c:out value="${student.score}" />
            </td>
            <td>
                <c:choose>
                    <c:when test="${student.score >= 5}">
                        Đậu
                    </c:when>
                    <c:otherwise>
                        Rớt
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>