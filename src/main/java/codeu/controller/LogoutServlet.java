package codeu.controller;

import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

/** Servlet class responsible for the logout page. */
public class LogoutServlet extends HttpServlet {

  /**
   * This function fires when a user requests the /logout URL. It simply forwards the request to
   * logout.jsp.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    request.getRequestDispatcher("/WEB-INF/view/logout.jsp").forward(request, response);
  }

   /**
    * This function fires when a user submits the logout.
    */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
        request.getSession().invalidate();
        response.sendRedirect("/login");
  }
}
