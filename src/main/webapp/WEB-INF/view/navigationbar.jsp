<!DOCTYPE html>
  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>

    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/activityFeed">Activity Feed</a>
<<<<<<< HEAD


=======
      <a href="/profilepage">My Profile</a>
>>>>>>> 8791396ea5bac6c0ab6a08343043ed77685a1b7c
    <% } else{ %>
      <a href="/login">Login</a>
      <a href="/register">Register</a>

    <% } %>
      <a href="/about.jsp">About</a>

  </nav>

