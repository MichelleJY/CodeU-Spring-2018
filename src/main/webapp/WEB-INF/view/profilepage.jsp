<%@ page import="codeu.model.data.User" %>
<!DOCTYPE html>
<html>
<head>
 <%@include file="navigationbar.jsp" %>
 <title>Profile Page</title>
 <link rel="stylesheet" href="/css/main.css">
 <link rel="stylesheet" href="/css/profilepage.css">
</head>
<body>
    <div id="pageContainer">
        <div id="leftContainer">
            <h1>
            <% if(request.getSession().getAttribute("user") != null){ %>
                <%= request.getSession().getAttribute("user") %>'s' 
            <% } %>
            Profile Page</h1>
            <img id="profilePicture" src="https://i.imgur.com/z4amwTY.png">
        </div>
        <div id="leftContainer">
            <form action="/profilepage">
             <label for = "aboutMe">About Me </label>
             <textarea name ="aboutMe" id="aboutMe" rows='25'></textarea>
             <input type = "submit" value = "Update">
            </form>
        </div>
        <div id="rightContainer">
            <h1>Recent Activity</h1>
            <textarea id="activity" rows='25' cols='35'></textarea>
        </div>
    </div>
</body>
</html>