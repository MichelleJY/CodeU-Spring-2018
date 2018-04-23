package codeu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import codeu.model.data.User;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.UserProfileStore;
import java.util.UUID;
import java.time.Instant;
import org.mindrot.jbcrypt.BCrypt;


/**
 * Servlet class responsible for user registration.
 */
public class RegisterServlet extends HttpServlet {

  /**
   * Store class that gives access to Users.
   */
  private UserStore userStore;
  private UserProfileStore profileStore;

  /**
   * Set up state for handling registration-related requests. This method is only called when
   * running in a server, not when running in a test.
   */
  @Override
  public void init() throws ServletException {
    super.init();
    setUserStore(UserStore.getInstance());
    setUserProfileStore(UserProfileStore.getInstance());
  }

  /**
   * Sets the UserStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */
  void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  /**
   * Sets the UserProfileStore used by this servlet. This function provides a common setup method
   * for use by the test framework or the servlet's init() function.
   */

  void setUserProfileStore(UserProfileStore profileStore) {
    this.profileStore = profileStore;
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException { 
    request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws IOException, ServletException {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

    if (!username.matches("[\\w*\\s*]*")) {
      request.setAttribute("error", "Please enter only letters, numbers, and spaces.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }

    if (userStore.isUserRegistered(username)) {
      request.setAttribute("error", "That username is already taken.");
      request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
      return;
    }


    response.getWriter().println("<p>Username: " + username + "</p>");
    response.getWriter().println("<p>Password: " + password + "</p>");

    UUID id = UUID.randomUUID();
    Instant current = Instant.now();

    User user = new User(id, username, BCrypt.hashpw(password, BCrypt.gensalt()), current);
    userStore.addUser(user);

    UserProfile profile = new UserProfile(id, "No about me yet", "https://i.imgur.com/z4amwTY.png", null, current);
    profileStore.addUserProfile(profile); 

    response.sendRedirect("/login");
  }
}
