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
         <div id="leftContainer">
          <h1>
            <% if(request.getSession().getAttribute("user") != null){ %>
                <%= request.getSession().getAttribute("user") %>'s 
            <% } %>
            Profile Page</h1>

             
           
            <form action="/profilepage">
              <input type = "submit" value = "Update Profile">
              <br/>
                      <img id="profilePicture" src="https://i.imgur.com/z4amwTY.png">
            <br/><br/>
            
            <select name="myFavorites" id="selector">
                  <option value="Books">Book</option>
                  <option value="Food">Food</option>
                  <option value="Hobbies">Hobbies</option>
                  <option value="Movies">Movies</option>
                  <option value="Songs">Songs</option>
                  <option value="Sports">Sports</option>
                  <option value="TV Shows">Tv Shows</option>
            </select>
            <br/>
            <input type= "text" name="subcategory">
            <br/>
            <h2>Interests</h2>
            <div id = "interestBlock">
              Interests will go in here/be updated in here
            </div>
           
        </center>
        </div>
        <div id="midContainer">
        
             <h1>About Me</h1>
             <textarea name ="aboutMe" id="aboutMe" rows='10' cols='90'></textarea>
             
             <br/>
            </form>
      
        
            <h1>Recent Activity</h1>
            <textarea id="activity" rows='20' cols='90'></textarea>
        </div>

   
</body>
</html>