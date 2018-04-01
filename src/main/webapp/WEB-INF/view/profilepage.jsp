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
            <img src="https://i.imgur.com/z4amwTY.png" style="border-radius: 50%; height: 50%; width: 50%">
        </div>
        <div id="rightContainer">
            <h1>Test</h1>
            <textarea id="aboutMe"></textarea>
        </div>
    </div>
</body>
</html>