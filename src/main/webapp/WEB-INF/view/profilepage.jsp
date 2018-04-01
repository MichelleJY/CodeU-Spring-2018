<%@ page import="codeu.model.data.User" %>

<!DOCTYPE html>
<html>
<head>
 <title>Profile Page</title>
 <link rel="stylesheet" href="/css/main.css">
 <link rel="stylesheet" href="/css/profilepage.css">
 <style>
   label {
     display: inline-block;
     width: 100px;
   }
 </style>
</head>
<body>
    <nav>
        <a id="navTitle" href="/">CodeU Chat App</a>
        <a href="/conversations">Conversations</a>
        <% if(request.getSession().getAttribute("user") != null){ %>
            <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
            <a href="/profilepage.jsp">Profile Page</a>
        <% } else{ %>
            <a href="/login">Login</a>
            <a href="/register">Register</a>
        <% } %>
        <a href="/about.jsp">About</a>
    </nav>
    <div id="pageContainer">
        <div id="leftContainer">
            <h1>
            <% if(request.getSession().getAttribute("user") != null){ %>
                <%= request.getSession().getAttribute("user") %>!'s' 
            <% } %>
            Profile Page</h1>
            <img id="profilePicture" src="https://i.imgur.com/z4amwTY.png">
        </div>
        <div id="leftContainer">
            <h1>About Me</h1>
            <textarea id="aboutMe" rows='25'></textarea>
        </div>
        <div id="rightContainer">
            <h1>Recent Activity</h1>
            <textarea id="activity" rows='25' cols='35'></textarea>
        </div>
    </div>
</body>
</html>