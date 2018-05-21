<!DOCTYPE html>
   <link rel="stylesheet" href="/css/navigation.css">
  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/activityFeed">Activity Feed</a>
      <a href="/profilepage/">My Profile</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>

    <% } %>
      <a href="/about.jsp">About</a>

  </nav>

