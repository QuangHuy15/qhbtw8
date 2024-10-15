<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="${pageContext.request.contextPath }/admin/video/insert" method="post"
    enctype="multipart/form-data">
    
    <label for="videoid">Video ID:</label><br>
    <input type="text" id="videoid" name="videoid"><br>
    
    <label for="title">Video Title:</label><br>
    <input type="text" id="title" name="title"><br> 
    
    <label for="description">Description:</label><br> 
    <input type="text" id="description" name="description"><br>
    
    <label for="videos">Videos:</label><br> 
    <img id="videos" height="150" width="200" src=""/><br>
    <input type="file" onchange="chooseFile(this)" id="videos" name="videos"><br>
    
    <label for="views">Views:</label><br> 
    <input type="text" id="views" name="views"><br>
    
    <label for="category">Category:</label><br>
    <input type="text" id="category" name="category"><br>
    
    <label for="status">Status:</label><br> 
    <input type="text" id="status" name="status"><br>
    
    <br> 
    <input type="submit" value="Insert">
</form>
