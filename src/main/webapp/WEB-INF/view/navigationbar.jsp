<!DOCTYPE html>
  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/activityFeed">Activity Feed</a>

    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>

    <% } %>
      <a href="/about.jsp">About</a>

  </nav>

