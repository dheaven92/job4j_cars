<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="header.jsp" />
<body>
<jsp:include page="navbar.jsp" />
<main>
    <div class="container">
        <div class="row pt-5">
            <div class="card" style="width: 100%">
                <div class="card-header">
                    Войти
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger"><c:out value="${error}"/></div>
                    </c:if>
                    <form id="form" action="<%=request.getContextPath()%>/login.do" method="post">
                        <div class="form-group">
                            <input type="email" class="form-control" name="email" placeholder="Почта" required>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="password" placeholder="Пароль" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Войти</button>
                        <a href="<%=request.getContextPath()%>/reg.do" class="btn btn-secondary">Регистрация</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
</body>
</html>