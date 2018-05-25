<!DOCTYPE html>
   <link rel="stylesheet" href="/css/navigation.css">
  <nav>
    <a id="navTitle" href="/">ChatU</a>
    <a href="/conversations" id="nav">Conversations</a>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/activityFeed" id="nav">Activity Feed</a>
      <a href="/logout" id="nav">Logout</a>
      <a href="/profilepage/" id="nav">My Profile</a>
    <% } else{ %>
      <a href="/login" id="nav">Login</a>
      <a href="/register" id="nav">Register</a>

    <% } %>
      <a href="/about.jsp" id="nav">About</a>

  </nav>
