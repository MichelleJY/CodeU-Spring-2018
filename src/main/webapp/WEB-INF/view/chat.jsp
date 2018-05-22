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
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="codeu.model.data.User" %>
<%@ page import="codeu.model.store.basic.UserProfileStore" %>
<%@ page import="codeu.model.data.UserProfile" %>

<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
%>

<!DOCTYPE html>
<html>
<head>
  <%@include file="navigationbar.jsp" %>
  <title><%= conversation.getTitle() %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <link rel="stylesheet" href="/css/chat.css" type="text/css">


  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };

  </script>
  <%--<script type="text/javascript">
    setTimeout(function(){
        location = ''
    },10000)
  </script>--%>
</head>
<body onload="scrollChat()">
  <div id="container">

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
    <%
      for (Message message : messages) {
      /*%><div class = "profilenames" style= "color: green">
        <%*/
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName();
          %>
      <li><div id="profileName"><%= author %>:</div>
        <div id="commonInterests">
          <%
          
          String result = "Common interests: ";
          String currentUser = (String)request.getSession().getAttribute("user");

          if (currentUser != null){
            User currUser = UserStore.getInstance().getUser(currentUser);
            User otherUser = UserStore.getInstance().getUser(author);
            UserProfile currProf = UserProfileStore.getInstance().getUserProfile(currUser.getId());
            UserProfile otherProf = UserProfileStore.getInstance().getUserProfile(otherUser.getId());
            HashMap<String,String> currentInterests = (HashMap<String,String>)currProf.getInterests();
            HashMap<String,String> otherInterests = (HashMap<String,String>)otherProf.getInterests();

            for(String interest: currentInterests.keySet()){
              if(otherInterests.containsKey(interest)){
                String currInterest = currentInterests.get(interest);
                String[] currSplit = currInterest.split(", ");

                String otherInterest = otherInterests.get(interest);
                String[] otherSplit = otherInterest.split(", ");

                for(int i = 0; i < currSplit.length; i++)
                  for(int j = 0; j < otherSplit.length; j++)
                      if(currSplit[i].equals(otherSplit[j]))
                        result += " " + currSplit[i] + ",";
                      

              }
            }
            if(result.equals("Common interests: "))
              result = "No interests in common.";
            else
              result = result.substring(0, result.length()-1);
          }
          %>
          <%= result %></div> 
        <%= message.getContent() %></li>
    <%
      }
    %>
      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
        <input type="text" name="message">
        <br/>
        <button type="submit">Send</button>
    </form>
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

</body>
</html>
