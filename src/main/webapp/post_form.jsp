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
                    Разместить объявление
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger"><c:out value="${error}"/></div>
                    </c:if>
                    <form
                            id="form"
                            <c:if test="${post != null}">
                                action="<%=request.getContextPath()%>/post.do?id=<%=request.getParameter("id")%>"
                            </c:if>
                            <c:if test="${post == null}">
                                action="<%=request.getContextPath()%>/post.do"
                            </c:if>
                            method="post"
                    >
                        <div class="form-group">
                            <textarea
                                    name="description"
                                    cols="30"
                                    rows="4"
                                    class="form-control"
                                    placeholder="О чем ваше объявление?"
                                    required
                            ><c:out value="${post.description}"/></textarea>
                        </div>
                        <div class="form-group">
                            <label>Марка</label>
                            <select name="brand" class="form-control" required>
                                <c:forEach var="brand" items="${brands}">
                                    <option
                                            value="<c:out value="${brand.id}"/>"
                                            <c:if test="${post.car != null && post.car.brandId == brand.id}">selected</c:if>
                                    >
                                        <c:out value="${brand.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <input
                                    type="text"
                                    class="form-control"
                                    name="model"
                                    placeholder="Модель"
                                    value="<c:if test="${post.car != null}"><c:out value="${post.car.model}"/></c:if>"
                                    required
                            >
                        </div>
                        <div class="form-group">
                            <label>Кузов</label>
                            <select name="bodyType" class="form-control" required>
                                <c:forEach var="bodyType" items="${bodyTypes}">
                                    <option
                                            value="<c:out value="${bodyType.id}"/>"
                                            <c:if test="${post.car != null && post.car.bodyTypeId == bodyType.id}">selected</c:if>
                                    >
                                        <c:out value="${bodyType.name}"/>
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <c:if test="${post != null}">
                            <div class="form-group">
                                <label>Продано</label>
                                <input
                                        type="checkbox"
                                        name="sold"
                                        <c:if test="${post.sold}">checked</c:if>
                                >
                            </div>
                        </c:if>
                        <button type="submit" class="btn btn-primary">
                            <c:if test="${post == null}">Разместить</c:if>
                            <c:if test="${post != null}">Редактировать</c:if>
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <c:if test="${post.car != null}">
            <div class="row pt-3 pb-5">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        Загрузить фото
                    </div>
                    <div class="card-body">
                        <form
                                action="<%=request.getContextPath()%>/upload?id=<%=request.getParameter("id")%>"
                                method="post"
                                enctype="multipart/form-data"
                        >
                            <div class="form-group">
                                <input type="file" name="file" class="form-control-file" required>
                            </div>
                            <button type="submit" class="btn btn-info">Загрузить фото</button>
                        </form>
                    </div>
                    <div class="card-footer">
                        <img src="<c:url value='/download?id=${post.id}'/>" height="100px"/>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</main>
</body>
</html>