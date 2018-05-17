<%@ page import="codeu.model.data.User" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.text.SimpleDateFormat" %>

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

        <form action="/profilepage/" method="POST">
            <img name="profilePicture" id="profilePicture" src="https://i.imgur.com/z4amwTY.png">
            <br/>
             <h2>
                Last time online: <br>
                <%
                    if (request.getSession().getAttribute("lastTimeOnline") != null) {
                        
                        Instant instant = (Instant)request.getSession().getAttribute("lastTimeOnline");
                        Date date = Date.from(instant);

                        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy \n HH:mm:ss");
                        String formattedDate = formatter.format(date);
                        out.print(formattedDate);
                    }
                    else {
                        out.print("unknown");
                    }
                %>
            </h2>
            
            <input type="submit" value="Update Profile">
            <input name="resetProfile" id="resetCheck" type="checkbox">
            <label for="resetCheck">Reset Profile?</label>
            <br/>
    </div>
    <div id="midContainer">
            <h1>About Me</h1>
            <textarea name="aboutMe" id="aboutMe" rows='10' cols='75'><% 
                if(request.getSession().getAttribute("aboutMe") != null) { 
                    out.print(request.getSession().getAttribute("aboutMe"));
                } 
                %></textarea>
            <br/>
        </form>

        <h1>Interests</h1>
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
            <input type="text" name="subcategory">
            <br/><br/>
        <div id="interestBlock">
          
                <%
                if (request.getSession().getAttribute("interests") != null) {
                    HashMap<String, String> interests = (HashMap<String,String>) request.getSession().getAttribute("interests");
                    for (String interest : interests.keySet()) {
                        out.print(interest + ": " + interests.get(interest) + "<br/>");
                    }
                }
                %>
        </div>
    </div>
</body>
</html>