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
            <img id="profilePicture" src="<%=request.getSession().getAttribute("profilePic")%>">
            <br/>
             <select name="profilePicture" id="selector">
                    <option value="/resources/images/profile-pictures/happy_man.jpg">Happy Man</option>
                    <option value="/resources/images/profile-pictures/scenery.jpg">Scene</option>
                    <option value="/resources/images/profile-pictures/thumbsup.jpg">Thumbs Up</option>
                    <option value=/resources/images/profile-pictures/codeguy.png>Code Guy</option>
                    <option value="/resources/images/profile-pictures/Determination.png">Determination</option>
                    <option value="/resources/images/profile-pictures/cat.jpg">Kitties</option>
                    <option value="/resources/images/profile-pictures/puppy.jpg">Puppy</option>
                    <option value="/resources/images/profile-pictures/sunhat.png">Sun hat</option>
                    <option value="/resources/images/profile-pictures/surprised.png">Surprised</option>
                    <option value="/resources/images/profile-pictures/unicorn.jpeg">Unicorn</option>
            </select>
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
            <select name="subcategorySelect" id="selector">
                <option selected="selected" value="nothing">None of these</option>
                <optgroup label="Books">
                    <option value="GoblinSlayer">Goblin Slayer</option>
                    <option value="GoT">Game Of Thrones</option>
                    <option value="CMIYC">Catch Me If You Can</option>
                </optgroup>
                <optgroup label="Food">
                    <option value="Waffles">Waffles</option>
                    <option value="Wings">Wings</option>
                    <option value="Pho">Pho</option>
                </optgroup>
                <optgroup label="Hobbies">
                    <option value="Cooking">Cooking</option>
                    <option value="Reading">Reading</option>
                    <option value="UpsideDownWaterSledding">UpsideDownWaterSledding</option>
                </optgroup>
                <optgroup label="Movies">
                    <option value="Deadpool2">Deadpool 2</option>
                    <option value="Madea">Madea</option>
                    <option value="JamesBond">James Bond</option>
                </optgroup>
                <optgroup label="Songs">
                    <option value="NetflixTrip">Netflix Trip by AJR</option>
                    <option value="TheWolf">The Wolf by Siames</option>
                    <option value="Despacito">Despacito by Luis Fonsi</option>
                </optgroup>
                <optgroup label="Sports">
                    <option value="Snowboarding">Snowboarding</option>
                    <option value="RockClimbing">Rock Climbing</option>
                    <option value="Basketball">Basketball</option>
                </optgroup>
                <optgroup label="TV Shows">
                    <option value="BlackMirror">Black Mirror</option>
                    <option value="AdventureTime">Adventure Time</option>
                    <option value="Fargo">Fargo</option>
                </optgroup>
            </select>
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