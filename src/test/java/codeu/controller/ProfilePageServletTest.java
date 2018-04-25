package codeu.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
<<<<<<< HEAD
import java.time.Instant;
import java.util.UUID;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
=======
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.time.Instant;
import java.util.Map;
>>>>>>> 2ce4bc31dd6fe59bcd9f01c63a272673db692783
import codeu.model.data.User;
import codeu.model.data.UserProfile;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.UserProfileStore;
<<<<<<< HEAD
=======
import java.util.HashMap;

>>>>>>> 2ce4bc31dd6fe59bcd9f01c63a272673db692783


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

    mockSession = Mockito.mock(HttpSession.class);
    Mockito.when(mockRequest.getSession()).thenReturn(mockSession);

    mockUserStore = Mockito.mock(UserStore.class);
    profilePageServlet.setUserStore(mockUserStore);

    mockProfileStore = Mockito.mock(UserProfileStore.class);
    profilePageServlet.setUserProfileStore(mockProfileStore);
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
}
