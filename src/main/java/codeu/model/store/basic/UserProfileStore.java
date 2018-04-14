// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.model.store.basic;

import codeu.model.data.UserProfile;
import codeu.model.store.persistence.PersistentStorageAgent;
import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */
public class UserProfileStore {

  /** Singleton instance of UserProfileStore. */
  private static UserProfileStore instance;

  /**
   * Returns the singleton instance of UserProfileStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */
  public static UserProfileStore getInstance() {
    if (instance == null) {
      instance = new UserProfileStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * Instance getter function used for testing. Supply a mock for PersistentStorageAgent.
   *
   * @param persistentStorageAgent a mock used for testing
   */
  public static UserProfileStore getTestInstance(PersistentStorageAgent persistentStorageAgent) {
    return new UserProfileStore(persistentStorageAgent);
  }

  /**
   * The PersistentStorageAgent responsible for loading User Profiles from and saving User Profiles to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of User Profiles. */
  private Map<UUID, UserProfile> userProfiles;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private UserProfileStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    userProfiles = new HashMap<>();
  }

  /** Load a set of randomly-generated UserProfile objects. */
  public void loadTestData() {
    userProfiles.putAll(DefaultDataStore.getInstance().getAllUserProfiles());
  }

  /**
   * Access the UserProfile object with the given UUID.
   *
   * @return null if UUID does not match any existing UserProfile.
   */
  public UserProfile getUserProfile(UUID UUID) {
    // This approach will be pretty slow if we have many User Profiles.
    if (userProfiles.containsKey(UUID)) return userProfiles.get(UUID);
    return null;
  }


  /** Add a new user profile to the current set of UserProfiles known to the application. */
  public void addUserProfile(UserProfile profile) {
    userProfiles.put(profile.getId(), profile);
    persistentStorageAgent.writeThrough(profile);
  }

  /** Return true if the given UUID is known to the application. */
  public boolean isUserProfileCreated(UUID UUID) {
    return userProfiles.containsKey(UUID);
  }

  /**
   * Sets the List of User Profiles stored by this UserProfileStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setUserProfiles(Map<UUID, UserProfile> userProfiles) {
    this.userProfiles = userProfiles;
  }
}
