<!DOCTYPE html>
   <link rel="stylesheet" href="/css/navigation.css">
  <nav>
    <a id="navTitle" href="/">ChatU</a>
    <a href="/conversations" class="nav">Conversations</a>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/activityFeed" class="nav">Activity Feed</a>
      <a href="/profilepage/" class="nav">My Profile</a>
    <% } else{ %>
      <a href="/login" class="nav">Login</a>
      <a href="/register" class="nav">Register</a>

    <% } %>
      <a href="/about.jsp" class="nav">About</a>

  </nav>
