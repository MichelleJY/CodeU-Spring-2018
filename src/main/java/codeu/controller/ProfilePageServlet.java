package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.UserProfileStore;
import java.time.Instant;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    /* TODO 
     * Retrieve UserProfile data
     * Fill ProfilePage fields with retrieved data
     */
    request.getRequestDispatcher("/WEB-INF/view/profilepage.jsp").forward(request, response);
  }

  /**
   * This function fires when a user posts the /profilepage URL. It simply forwards the request to
   * profilepage.jsp.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
      /* TODO 
       * Send AboutMe, and Interests data to UserProfile object
       * Update UserProfileStore with new UserProfile object. 
       */
      String username = (String) request.getSession().getAttribute("user");
      if (userStore.isUserRegistered(username)) {
        User user = userStore.getUser(username);
        UserProfile userProfile = null;
        String aboutMe = request.getParameter("aboutMe");
        String profilePicture = request.getParameter("profilepicture");
        String category = request.getParameter("myFavorites");
        String subCategory = request.getParameter("subcategory");

        if (userProfileStore.isUserProfileCreated(user.getId())) {
          userProfile = userProfileStore.getUserProfile(user.getId());
          userProfile.setLastTimeOnline(Instant.now());
        }
        else {
          userProfile = new UserProfile(user.getId(), "", "", new HashMap<String, String>(), Instant.now());
        }

        userProfile.setAboutMe(aboutMe);
        userProfile.setProfilePicture(profilePicture);
        userProfile.addInterest(category, subCategory);

        userProfileStore.addUserProfile(userProfile);
        request.getRequestDispatcher("/WEB-INF/view/profilepage.jsp").forward(request, response);
      }
    } 
}
