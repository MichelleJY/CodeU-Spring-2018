package codeu.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.mockito.ArgumentCaptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.data.User;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.UserProfileStore;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;


public class RegisterServletTest {

  private RegisterServlet registerServlet;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private UserStore mockUserStore;
  private HttpSession mockSession;
  private UserProfileStore mockUserProfileStore;
  @Before
  public void setup() {
    registerServlet = new RegisterServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    mockUserStore = Mockito.mock(UserStore.class);
    mockUserProfileStore = Mockito.mock(UserProfileStore.class);
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/register.jsp")).thenReturn(mockRequestDispatcher);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    registerServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_BadUsername() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("bad !@#$% username");

    registerServlet.doPost(mockRequest, mockResponse);

    Mockito.verifyZeroInteractions(mockResponse);
    Mockito.verify(mockRequest).setAttribute("error", "Please enter only letters, numbers, and spaces.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_NewUser() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("Testertest");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("password");

    PrintWriter mockPrintWriter = Mockito.mock(PrintWriter.class);
    Mockito.when(mockResponse.getWriter()).thenReturn(mockPrintWriter);    

    Mockito.when(mockUserStore.isUserRegistered("Testertest")).thenReturn(false);
    registerServlet.setUserStore(mockUserStore);
    registerServlet.setUserProfileStore(mockUserProfileStore);

    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    registerServlet.doPost(mockRequest, mockResponse); 

    Mockito.verify(mockPrintWriter).println("<p>Username: Testertest</p>");
    Mockito.verify(mockPrintWriter).println("<p>Password: password</p>");

    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    Mockito.verify(mockUserStore).addUser(userArgumentCaptor.capture()); 
    User storedUser = userArgumentCaptor.getValue();
    Assert.assertEquals(storedUser.getName(), "Testertest");

    UUID userId = storedUser.getId();

    ArgumentCaptor<UserProfile> profUserArgumentCaptor = ArgumentCaptor.forClass(UserProfile.class);

    Mockito.verify(mockUserProfileStore).addUserProfile(profUserArgumentCaptor.capture()); 
    UserProfile storedUserProfile = profUserArgumentCaptor.getValue();
    Assert.assertEquals(storedUserProfile.getId(), userId);
    //BCrypt.checkpw() checks that the second param is correctly hashed  
    Assert.assertTrue(BCrypt.checkpw("password", storedUser.getPassword()));  
    //checks that stored password is hashed and not plain text 
    Assert.assertNotEquals("password", storedUser.getPassword());
    Mockito.verify(mockResponse).sendRedirect("/login");

  }

  @Test
  public void testDoPost_ExistingUser() throws IOException, ServletException {
    Mockito.when(mockRequest.getParameter("username")).thenReturn("Testertest");
    Mockito.when(mockRequest.getParameter("password")).thenReturn("password");

    Mockito.when(mockUserStore.isUserRegistered("Testertest")).thenReturn(true);
    registerServlet.setUserStore(mockUserStore);
    registerServlet.setUserProfileStore(mockUserProfileStore);
    
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    registerServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockUserStore, Mockito.never()).addUser(Mockito.any(User.class));

    Mockito.verify(mockRequest).setAttribute("error", "That username is already taken.");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

}
