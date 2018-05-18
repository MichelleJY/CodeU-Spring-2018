<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ page import="java.util.*" %>
<%@ page import="codeu.model.data.Conversation" %>
<%--<%@ page import="codeu.model.store.UserStore" %>--%>

<%@ page import="codeu.model.data.User" %>


<!DOCTYPE html>
<html>
<head>
  <%@include file="navigationbar.jsp" %>
  <title>Activity Feed</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>
  <div id="container">

    <% if(request.getAttribute("error") != null){ %>
        <h2 style="color:red"><%= request.getAttribute("error") %></h2>
    <% } %>

<%-- IF USER IS LOGGED IN --%>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <h1>Activity Feed</h1>
      <%
      List<Conversation> conversations =
        (List<Conversation>) request.getAttribute("conversations");
      ArrayList<String> displayNames = (ArrayList<String>) request.getAttribute("displayNames");
      List<User> users =
        (List<User>) request.getAttribute("users");
        %>
        <%
          if(users != null){
        %>
        <ul class="mdl-list">
          <%
            for(User user : users){
          %>
            <li> <%= user.getName()%> registered. </li>
          <%
            }
          %>
          </ul>
        <%
        }
        %>
        <%
      if(conversations != null && !conversations.isEmpty()){
      %>
        <ul class="mdl-list">
        <% int i = 0; %>
        <%
          for(Conversation conversation : conversations){
        %>
          <li> <%= displayNames.get(i)%> created a new conversation: <a href="/chat/<%= conversation.getTitle() %>">
          <%= conversation.getTitle() %></a> </li>
          <%i++;%>
        <%
          }
        %>
        </ul>
      <%
      }
      %>
    <%
       }
    %>
    <hr/>
  </div>
</body>
</html>
