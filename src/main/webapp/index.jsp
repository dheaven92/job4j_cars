<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="header.jsp" />
<body>
<jsp:include page="navbar.jsp" />
<main>
    <div class="container">
        <div class="row pt-3">
            <h1>Площадка по продаже авто</h1>
        </div>
        <div class="row pt-3">
            <table id="table" class="table">
                <thead>
                <tr>
                    <th scope="col">Марка</th>
                    <th scope="col">Модель</th>
                    <th scope="col">Кузов</th>
                    <th scope="col">Фото</th>
                    <th scope="col">Продавец</th>
                    <th scope="col">Статус</th>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="post" items="${posts}">
                    <tr>
                        <td><c:out value="${post.car.brand.name}"/></td>
                        <td><c:out value="${post.car.model}"/></td>
                        <td><c:out value="${post.car.bodyType.name}"/></td>
                        <td><img src="<c:url value='/download?id=${post.id}'/>" width="200px"/></td>
                        <td><c:out value="${post.user.name}"/> <a href="mailto:<c:out value="${post.user.email}"/>"><c:out value="${post.user.email}"/></a></td>
                        <td>
                            <c:if test="${!post.sold}"><span class=" badge badge-success">Продается</span></c:if>
                            <c:if test="${post.sold}"><span class=" badge badge-secondary">Продано</span></c:if>
                        </td>
                        <c:if test="${user.id == post.user.id}">
                            <td><a href="<%=request.getContextPath()%>/post.do?id=<c:out value="${post.id}"/>" class="btn btn-primary btn-sm">Редактировать</a></td>
                            <td><a href="<%=request.getContextPath()%>/post.do?delete=true&id=<c:out value="${post.id}"/>" class="btn btn-danger btn-sm">Удалить</a></td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
</body>
</html>