package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.data.UserProfile;
import codeu.model.data.User;
import codeu.model.store.basic.UserProfileStore;
import java.time.Instant;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.Map;


/**
 * Servlet class responsible for User Profile pages.
 */
public class ProfilePageServlet extends HttpServlet {

  /** Store class that gives access to Users and User Profiles. */
  private UserStore userStore;
  private UserProfileStore userProfileStore;

  /**
   * Set up state for handling profilepage-related requests. This method is only called when running in a
   * server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setUserProfileStore(UserProfileStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.  
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the UserProfileStore used by this servlet. This function provides a common setup method for use
   * by the test framework or the servlet's init() function.
   */
  void setUserProfileStore(UserProfileStore userProfileStore) {
    this.userProfileStore = userProfileStore;
  }

  /**
   * This function fires when a user requests the /profilepage URL. It instantiates the user's 
   * UserProfile object, returns their profile data and forwards the request to
   * profilepage.jsp.
   */
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException { 

    String username = (String)request.getSession().getAttribute("user"); 
    if(username == null){
      response.sendRedirect("/profilepage");
      return;
    }

    User user= userStore.getUser(username);

    if(user == null){
      System.out.println("User not found: " + username);
      response.sendRedirect("/profilepage");
      return;
    }

    UUID id = user.getId();
    UserProfile profile = userProfileStore.getUserProfile(id); //

    Map<String, String> interests = profile.getInterests();
    request.setAttribute("interests", interests);

    String aboutMe = profile.getAboutMe();
    request.setAttribute("aboutMe", aboutMe);

    String profilePic = profile.getProfilePicture();
    request.setAttribute("profilePic", profilePic);

    request.getRequestDispatcher("/WEB-INF/view/profilepage.jsp").forward(request, response);
  }

  /**
   * This function fires when a user posts the /profilepage URL. It simply forwards the request to
   * profilepage.jsp.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
      
      String username = (String) request.getSession().getAttribute("user");
      if (!userStore.isUserRegistered(username)) {
        request.setAttribute("error", "User is not registered");
        request.getRequestDispatcher("/WEB-INF/view/profilepage.jsp").forward(request, response);
        return;
      }
      User user = userStore.getUser(username);
      UUID userId = userStore.getUser(username).getId();
      UserProfile userProfile = null;
      String aboutMe = request.getParameter("aboutMe");
      String profilePicture = request.getParameter("profilePicture");
      String category = request.getParameter("myFavorites");
      String subCategory = request.getParameter("subcategory");

      if (userProfileStore.isUserProfileCreated(userId)) {
        userProfile = userProfileStore.getUserProfile(userId);
        userProfile.setLastTimeOnline(Instant.now());
      }
      else {
        userProfile = new UserProfile(userId, "", "", new HashMap<String, String>(), Instant.now());
      }

      userProfile.setAboutMe(aboutMe);
      userProfile.setProfilePicture(profilePicture);
      userProfile.addInterest(category, subCategory);

      userProfileStore.addUserProfile(userProfile);
      request.getRequestDispatcher("/WEB-INF/view/profilepage.jsp").forward(request, response);
    } 
}
