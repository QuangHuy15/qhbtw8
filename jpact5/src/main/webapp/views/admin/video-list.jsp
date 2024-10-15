<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<a href="${pageContext.request.contextPath }/admin/video/add">Add Video</a>
<table border="1" width="100%">
    <tr>
        <th>STT</th>
        <th>Video ID</th>
        <th>Videos</th>
        <th>Video Title</th>
        <th>Description</th>
        <th>Views</th>
        <th>Category</th>
        <th>Status</th>
        <th>Action</th>
    </tr>

    <c:forEach items="${listvideo}" var="video" varStatus="STT">
        <tr>    
            <td>${STT.index + 1}</td>
            <td>${video.videoid}</td>
            <td>
                <c:if test="${video.videos.substring(0, 5) != 'https' }">
                    <c:url value="/video?fname=${video.videos }" var="imgUrl"></c:url>
                </c:if>
                <c:if test="${video.videos.substring(0, 5) == 'https' }">
                    <c:url value="${video.videos}" var="imgUrl"></c:url>
                </c:if>
                <img height="150" width="200" src="${imgUrl}" />
            </td>
            <td>${video.title}</td>
            <td>${video.description}</td>
            <td>${video.views}</td>
            <td>${video.category.categoryname}</td>
            <td>
                <c:if test="${video.status == 1 }">
                    <span>Hoạt động</span>
                </c:if>
                <c:if test="${video.status != 1 }">
                    <span>Khóa</span>
                </c:if>
            </td>
            <td>
                <a href="<c:url value='/admin/video/edit?id=${video.videoid }'/>">Sửa</a>
                | <a href="<c:url value='/admin/video/delete?id=${video.videoid }'/>">Xóa</a>
            </td>
        </tr>
    </c:forEach>

</table>
