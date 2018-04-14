package codeu.model.store.basic;

import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.model.data.UserProfile;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserProfileStoreTest {

  private UserProfileStore UserProfileStore;
  private PersistentStorageAgent mockPersistentStorageAgent;

  private final UserProfile userProfile_ONE =
      new UserProfile(UUID.randomUUID(), "test_aboutMe_one", "test_profilePicture_one", new HashMap<String, String>(), Instant.ofEpochMilli(1000));
  private final UserProfile userProfile_TWO =
      new UserProfile(UUID.randomUUID(), "test_aboutMe_two", "test_profilePicture_two", new HashMap<String, String>(), Instant.ofEpochMilli(1000));
  private final UserProfile userProfile_THREE =
      new UserProfile(UUID.randomUUID(), "test_aboutMe_two", "test_profilePicture_two", new HashMap<String, String>(), Instant.ofEpochMilli(1000));

  @Before
  public void setup() {
    mockPersistentStorageAgent = Mockito.mock(PersistentStorageAgent.class);
    UserProfileStore = UserProfileStore.getTestInstance(mockPersistentStorageAgent);

    userProfile_ONE.getInterests().put("test_interest_key_one", "test_interest_value_one");
    userProfile_TWO.getInterests().put("test_interest_key_two", "test_interest_value_two");
    userProfile_THREE.getInterests().put("test_interest_key_three", "test_interest_value_three");

    final Map<UUID, UserProfile> UserProfileMap = new HashMap<>();
    UserProfileMap.put(userProfile_ONE.getId(), userProfile_ONE);
    UserProfileMap.put(userProfile_TWO.getId(), userProfile_TWO);
    UserProfileMap.put(userProfile_THREE.getId(), userProfile_THREE);
    UserProfileStore.setUserProfiles(UserProfileMap);
  }

  @Test
  public void testGetUserProfile_byId_found() {
    UserProfile resultUserProfile = UserProfileStore.getUserProfile(userProfile_ONE.getId());

    assertEquals(userProfile_ONE, resultUserProfile);
  }

  @Test
  public void testGetUserProfile_byId_notFound() {
    UserProfile resultUserProfile = UserProfileStore.getUserProfile(UUID.randomUUID());

    Assert.assertNull(resultUserProfile);
  }

  @Test
  public void testAddUserProfile() {
    UserProfile inputUserProfile = new UserProfile(UUID.randomUUID(), "test_UserProfile_AboutMe_one", "test_UserProfile_profilePicture_one", 
        new HashMap<String, String>(), Instant.now());
    inputUserProfile.getInterests().put("test_category_one", "test_value_one");

    UserProfileStore.addUserProfile(inputUserProfile);
    UserProfile resultUserProfile = UserProfileStore.getUserProfile(inputUserProfile.getId());

    assertEquals(inputUserProfile, resultUserProfile);
    Mockito.verify(mockPersistentStorageAgent).writeThrough(inputUserProfile);
  }

  @Test
  public void testIsUserProfileRegistered_true() {
    Assert.assertTrue(UserProfileStore.isUserProfileCreated(userProfile_ONE.getId()));
  }

  @Test
  public void testIsUserProfileRegistered_false() {
    Assert.assertFalse(UserProfileStore.isUserProfileCreated(UUID.randomUUID()));
  }

  private void assertEquals(UserProfile expectedUserProfile, UserProfile actualUserProfile) {
    Assert.assertEquals(expectedUserProfile.getId(), actualUserProfile.getId());
    Assert.assertEquals(expectedUserProfile.getAboutMe(), actualUserProfile.getAboutMe());
    Assert.assertEquals(expectedUserProfile.getProfilePicture(), actualUserProfile.getProfilePicture());
    Assert.assertEquals(expectedUserProfile.getInterests(), actualUserProfile.getInterests());
    Assert.assertEquals(expectedUserProfile.getlastTimeOnline(), expectedUserProfile.getlastTimeOnline());
  }
}
