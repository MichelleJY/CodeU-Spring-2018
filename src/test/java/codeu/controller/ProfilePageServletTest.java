package codeu.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.time.Instant;
import java.util.Map;
import codeu.model.data.User;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.UserProfileStore;
import java.util.HashMap;



public class ProfilePageServletTest {

  private ProfilePageServlet profilePageServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private HttpSession mockSession;
  private UserStore mockUserStore;
  private UserProfileStore mockProfileStore;


  @Before
  public void setup() {
    profilePageServlet = new ProfilePageServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockSession = Mockito.mock(HttpSession.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profilepage.jsp")).thenReturn(mockRequestDispatcher);
    mockUserStore = Mockito.mock(UserStore.class);
    mockProfileStore = Mockito.mock(UserProfileStore.class);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    profilePageServlet.setUserStore(mockUserStore);
    profilePageServlet.setUserProfileStore(mockProfileStore);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    UUID fakeUUID = UUID.randomUUID();
    Instant current = Instant.now();
    Map<String,String> fakeInterests = new HashMap<String, String>();
    fakeInterests.put("test_category", "test_interest");
    User fakeuser = new User(fakeUUID, "test_username", "password", current);
    UserProfile fakeprofile = new UserProfile(fakeUUID, "test_about", "picture_url", fakeInterests, current);

    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeuser); //
    Mockito.when(mockProfileStore.getUserProfile(fakeUUID)).thenReturn(fakeprofile);

    profilePageServlet.doGet(mockRequest, mockResponse); //
    Mockito.verify(mockRequest).setAttribute("interests", fakeInterests);
    Mockito.verify(mockRequest).setAttribute("aboutMe", "test_about");
    Mockito.verify(mockRequest).setAttribute("profilePic", "picture_url");

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}