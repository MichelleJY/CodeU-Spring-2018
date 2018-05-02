package codeu.controller;

import static org.junit.Assume.assumeFalse;

import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.UUID;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
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
    mockResponse = Mockito.mock(HttpServletResponse.class); 
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profilepage.jsp")).thenReturn(mockRequestDispatcher);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/login.jsp")).thenReturn(mockRequestDispatcher);
    
    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockUserStore = Mockito.mock(UserStore.class);
    profilePageServlet.setUserStore(mockUserStore);

    mockProfileStore = Mockito.mock(UserProfileStore.class);
    profilePageServlet.setUserProfileStore(mockProfileStore);
  }

  @Test
  public void testDoGet_UserExists() throws IOException, ServletException {
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    profilePageServlet.setUserStore(mockUserStore);
    profilePageServlet.setUserProfileStore(mockProfileStore);
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    UUID fakeUUID = UUID.randomUUID();
    Instant current = Instant.now();
    Map<String,String> fakeInterests = new HashMap<String, String>();
    fakeInterests.put("test_category", "test_interest");
    User fakeUser = new User(fakeUUID, "test_username", "password", current);
    UserProfile fakeprofile = new UserProfile(fakeUUID, "test_about", "picture_url", fakeInterests, current);

    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser);
    Mockito.when(mockProfileStore.isUserProfileCreated(fakeUUID)).thenReturn(true);

    Mockito.when(mockProfileStore.getUserProfile(fakeUUID)).thenReturn(fakeprofile);

    profilePageServlet.doGet(mockRequest, mockResponse); 
    Mockito.verify(mockSession).setAttribute("interests", fakeInterests);
    Mockito.verify(mockSession).setAttribute("aboutMe", "test_about");
    Mockito.verify(mockSession).setAttribute("profilePic", "picture_url");
    Mockito.verify(mockSession).setAttribute("lastTimeOnline", current);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoGet_UserNotLoggedIn() throws IOException, ServletException{
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    
    Mockito.when(mockSession.getAttribute("user")).thenReturn(null);

    profilePageServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "That username doesn't exist");
    Mockito.verify(mockResponse).sendRedirect("/login");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);

  }

   @Test
  public void testDoGet_UserDoesntExist() throws IOException, ServletException{
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    profilePageServlet.setUserStore(mockUserStore);

    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(null); 

    profilePageServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "Username not found");
    Mockito.verify(mockResponse).sendRedirect("/login");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);

  }

  @Test
  public void testDoGet_UserProfileDoesntExist() throws IOException, ServletException{
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);
    profilePageServlet.setUserStore(mockUserStore);

    Mockito.when(mockSession.getAttribute("user")).thenReturn("test_username");

    UUID fakeUUID = UUID.randomUUID();
    Instant current = Instant.now();
    User fakeUser = new User(fakeUUID, "test_username", "password", current);
    Mockito.when(mockUserStore.getUser("test_username")).thenReturn(fakeUser); 
    Mockito.when(mockProfileStore.isUserProfileCreated(fakeUUID)).thenReturn(false);

    profilePageServlet.doGet(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "User does not have a profile saved yet");
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);

  }
  
  @Test
  public void testDoPost_UpdateExistingUserProfile() throws IOException, ServletException{
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test username");
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);

    UUID mockId = UUID.randomUUID();
    User mockUser = new User(mockId, "test username", BCrypt.hashpw("test password", BCrypt.gensalt()), Instant.now());
    
    Mockito.when(mockUserStore.getUser("test username")).thenReturn(mockUser);  

    Mockito.when(mockRequest.getParameter("aboutMe")).thenReturn("test_aboutMe");
    Mockito.when(mockRequest.getParameter("profilePicture")).thenReturn("test_profilePicture");
    Mockito.when(mockRequest.getParameter("myFavorites")).thenReturn("test_category");
    Mockito.when(mockRequest.getParameter("subcategory")).thenReturn("test_subcategory");

    Mockito.when(mockProfileStore.isUserProfileCreated(mockId)).thenReturn(true);

    UserProfile mockUserProfile = new UserProfile(mockId, "", "", new HashMap<String, String>(), Instant.now());
    Mockito.when(mockProfileStore.getUserProfile(mockId)).thenReturn(mockUserProfile);

    profilePageServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<UserProfile> userProfileArgumentCaptor = ArgumentCaptor.forClass(UserProfile.class);
    Mockito.verify(mockProfileStore).addUserProfile(userProfileArgumentCaptor.capture());
    mockUserProfile = userProfileArgumentCaptor.getValue();

    Assert.assertEquals(mockUserProfile.getId(), mockId);
    Assert.assertEquals(mockUserProfile.getAboutMe(), "test_aboutMe");
    Assert.assertEquals(mockUserProfile.getProfilePicture(), "test_profilePicture");
    Assert.assertNotEquals(mockUserProfile.getInterests(), null);
    Assert.assertEquals(mockUserProfile.getInterests().get("test_category"), "test_subcategory");
    Assert.assertNotEquals(mockUserProfile.getLastTimeOnline(), null);

    Assert.assertEquals(mockProfileStore.getUserProfile(mockId), mockUserProfile);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }

  @Test
  public void testDoPost_UpdateNewUserProfile() throws IOException, ServletException{
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test username");
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(true);

    UUID mockId = UUID.randomUUID();
    User mockUser = new User(mockId, "test username", BCrypt.hashpw("test password", BCrypt.gensalt()), Instant.now());
    
    Mockito.when(mockUserStore.getUser("test username")).thenReturn(mockUser);  

    Mockito.when(mockRequest.getParameter("aboutMe")).thenReturn("test_aboutMe");
    Mockito.when(mockRequest.getParameter("profilePicture")).thenReturn("test_profilePicture");
    Mockito.when(mockRequest.getParameter("myFavorites")).thenReturn("test_category");
    Mockito.when(mockRequest.getParameter("subcategory")).thenReturn("test_subcategory");

    Mockito.when(mockProfileStore.isUserProfileCreated(mockId)).thenReturn(false);

    profilePageServlet.doPost(mockRequest, mockResponse);

    ArgumentCaptor<UserProfile> userProfileArgumentCaptor = ArgumentCaptor.forClass(UserProfile.class);
    Mockito.verify(mockProfileStore).addUserProfile(userProfileArgumentCaptor.capture());
    UserProfile mockUserProfile = userProfileArgumentCaptor.getValue();

    Assert.assertEquals(mockUserProfile.getId(), mockId);
    Assert.assertEquals(mockUserProfile.getAboutMe(), "test_aboutMe");
    Assert.assertEquals(mockUserProfile.getProfilePicture(), "test_profilePicture");
    Assert.assertNotEquals(mockUserProfile.getInterests(), null);
    Assert.assertEquals(mockUserProfile.getInterests().get("test_category"), "test_subcategory");
    Assert.assertNotEquals(mockUserProfile.getLastTimeOnline(), null);

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
  
  public void testDoPost_UserIsNotRegistered() throws IOException, ServletException{
    Mockito.when(mockSession.getAttribute("user")).thenReturn("test username");
    Mockito.when(mockUserStore.isUserRegistered("test username")).thenReturn(false);

    profilePageServlet.doPost(mockRequest, mockResponse);

    Mockito.verify(mockRequest).setAttribute("error", "User is not registered");
    Mockito.verify(mockResponse).sendRedirect("/login");

    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
  }
}
