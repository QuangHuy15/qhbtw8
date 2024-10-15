<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<form action="${pageContext.request.contextPath }/admin/video/update" method="post"
    enctype="multipart/form-data">
    
    <input type="text" id="videoid" name="videoid" value="${video.videoid}" hidden="hidden"><br>
    
    <label for="title">Video Title:</label><br>
    <input type="text" id="title" name="title" value="${video.title}"><br> 
    
    <label for="description">Description:</label><br> 
    <input type="text" id="description" name="description" value="${video.description}"><br> 
    
    <label for="videos">Videos:</label><br> 
    <c:if test="${video.videos.substring(0,5) != 'https' }">
        <c:url value="/video?fname=${video.videos}" var="imgUrl"></c:url>
    </c:if>
    <c:if test="${video.videos.substring(0,5) == 'https' }">
        <c:url value="${video.videos}" var="imgUrl"></c:url>
    </c:if>
    <img id="videos" height="150" width="200" src="${imgUrl}" />
    
    <input type="file" onchange="chooseFile(this)" id="videos" name="videos" value="${video.videos}"><br>
    
    <label for="views">Views:</label><br> 
    <input type="text" id="views" name="views" value="${video.views}"><br>
    
    <label for="category">Category:</label><br> 
    <input type="text" id="category" name="category" value="${video.category.categoryname}"><br>
    
    <label for="status">Status:</label><br> 
    <input type="text" id="status" name="status" value="${video.status}"><br>
    
    <br> 
    <input type="submit" value="Update">
</form>
