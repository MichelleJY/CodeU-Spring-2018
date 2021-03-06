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

package codeu.model.store.persistence;

import codeu.model.data.Conversation;
import codeu.model.data.UserProfile;
import codeu.model.data.Message;
import codeu.model.data.User;
import codeu.model.store.persistence.PersistentDataStoreException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EmbeddedEntity;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map;

/**
 * This class handles all interactions with Google App Engine's Datastore service. On startup it
 * sets the state of the applications's data objects from the current contents of its Datastore. It
 * also performs writes of new of modified objects back to the Datastore.
 */
public class PersistentDataStore {

  // Handle to Google AppEngine's Datastore service.
  private DatastoreService datastore;

  /**
   * Constructs a new PersistentDataStore and sets up its state to begin loading objects from the
   * Datastore service.
   */
  public PersistentDataStore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Loads all User objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<User> loadUsers() throws PersistentDataStoreException {

    List<User> users = new ArrayList<>();

    // Retrieve all users from the datastore.
    Query query = new Query("chat-users");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String userName = (String) entity.getProperty("username");
        String password = (String) entity.getProperty("password");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        User user = new User(uuid, userName, password, creationTime);
        users.add(user);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return users;
  }

  /**
   * Loads all Conversation objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Conversation> loadConversations() throws PersistentDataStoreException {

    List<Conversation> conversations = new ArrayList<>();

    // Retrieve all conversations from the datastore.
    Query query = new Query("chat-conversations");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID ownerUuid = UUID.fromString((String) entity.getProperty("owner_uuid"));
        String title = (String) entity.getProperty("title");
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        Conversation conversation = new Conversation(uuid, ownerUuid, title, creationTime);
        conversations.add(conversation);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return conversations;
  }

  /**
   * Loads all Message objects from the Datastore service and returns them in a List.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public List<Message> loadMessages() throws PersistentDataStoreException {

    List<Message> messages = new ArrayList<>();

    // Retrieve all messages from the datastore.
    Query query = new Query("chat-messages");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        UUID conversationUuid = UUID.fromString((String) entity.getProperty("conv_uuid"));
        UUID authorUuid = UUID.fromString((String) entity.getProperty("author_uuid"));
        Instant creationTime = Instant.parse((String) entity.getProperty("creation_time"));
        String content = (String) entity.getProperty("content");
        Message message = new Message(uuid, conversationUuid, authorUuid, content, creationTime);
        messages.add(message);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return messages;
  }

  /**
   * Loads all UserProfile objects from the Datastore service and returns them in a Map.
   *
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public Map<UUID, UserProfile> loadUserProfiles() throws PersistentDataStoreException {

    Map<UUID, UserProfile> userProfiles = new HashMap<>();

    // Retrieve all user profiles from the datastore.
    Query query = new Query("chat-users-profiles");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UUID uuid = UUID.fromString((String) entity.getProperty("uuid"));
        String aboutMe = (String) entity.getProperty("aboutMe");
        String profilePicture = (String) entity.getProperty("profilePicture");
        Map<String, String> interests = new HashMap<>();
        EmbeddedEntity interestsEntity = (EmbeddedEntity) entity.getProperty("interests");
        for (String key : interestsEntity.getProperties().keySet()) {
          interests.put(key, (String) interestsEntity.getProperty(key));
        }
        Instant creationTime = Instant.parse((String) entity.getProperty("last_time_online"));
        UserProfile userProfile = new UserProfile(uuid, aboutMe, profilePicture, interests, creationTime);
        userProfile.setEntityKey(entity.getKey());
        userProfiles.put(uuid, userProfile);
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }

    return userProfiles;
  }

  /**
   * Delete all UserProfile objects with the given uuid from the Datastore service. The returned list may be empty.
   * 
   * @param uuid The UUID of the User Profile objects to be deleted.
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public void deleteUserProfiles() throws PersistentDataStoreException {
    
    // Retrieve all user profiles from the datastore.
    Query query = new Query("chat-users-profiles");
    PreparedQuery results = datastore.prepare(query);
    
    for (Entity entity : results.asIterable()) {
      try {
        datastore.delete(entity.getKey());
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }
  }

  /**
   * Delete all UserProfile objects with the given uuid from the Datastore service. The returned list may be empty.
   * 
   * @param uuid The UUID of the User Profile objects to be deleted.
   * @throws PersistentDataStoreException if an error was detected during the load from the
   *     Datastore service
   */
  public void deleteUserProfiles(UUID uuid) throws PersistentDataStoreException {
    
    // Retrieve all user profiles from the datastore.
    Query query = new Query("chat-users-profiles");
    PreparedQuery results = datastore.prepare(query);
    
    for (Entity entity : results.asIterable()) {
      try {
        UUID profileId = UUID.fromString((String) entity.getProperty("uuid"));
        if (profileId == uuid) {
          datastore.delete(entity.getKey());
        }
      } catch (Exception e) {
        // In a production environment, errors should be very rare. Errors which may
        // occur include network errors, Datastore service errors, authorization errors,
        // database entity definition mismatches, or service mismatches.
        throw new PersistentDataStoreException(e);
      }
    }
  }


  /** Write a User object to the Datastore service. */
  public void writeThrough(User user) {
    Entity userEntity = new Entity("chat-users");
    userEntity.setProperty("uuid", user.getId().toString());
    userEntity.setProperty("username", user.getName());
    userEntity.setProperty("password", user.getPassword());
    userEntity.setProperty("creation_time", user.getCreationTime().toString());
    datastore.put(userEntity);
  }

  /** Write a UserProfile object to the Datastore service. 
   *  Deletes the existing UserProfile object from the Datastore if applicable.
   */
  public void writeThrough(UserProfile userProfile) {
    Entity userProfileEntity = new Entity("chat-users-profiles");
    try {
      if (userProfile.getEntityKey() != null) {
        Entity datastoreEntity = datastore.get(userProfile.getEntityKey());
        userProfileEntity = datastoreEntity;
      }
    }
    catch (Exception e) {
      System.out.println(e);
    }
    userProfileEntity.setProperty("uuid", userProfile.getId().toString());
    userProfileEntity.setProperty("aboutMe", userProfile.getAboutMe());
    userProfileEntity.setProperty("profilePicture", userProfile.getProfilePicture());
    EmbeddedEntity interestsEntity = new EmbeddedEntity();
    Map<String, String> interests = userProfile.getInterests();
    for (String key : interests.keySet()) {
      interestsEntity.setProperty(key, interests.get(key));
    }
    userProfileEntity.setProperty("interests", interestsEntity);
    userProfileEntity.setProperty("last_time_online", userProfile.getLastTimeOnline().toString());
    datastore.put(userProfileEntity);
  }


  /** Write a Message object to the Datastore service. */
  public void writeThrough(Message message) {
    Entity messageEntity = new Entity("chat-messages");
    messageEntity.setProperty("uuid", message.getId().toString());
    messageEntity.setProperty("conv_uuid", message.getConversationId().toString());
    messageEntity.setProperty("author_uuid", message.getAuthorId().toString());
    messageEntity.setProperty("content", message.getContent());
    messageEntity.setProperty("creation_time", message.getCreationTime().toString());
    datastore.put(messageEntity);
  }

  /** Write a Conversation object to the Datastore service. */
  public void writeThrough(Conversation conversation) {
    Entity conversationEntity = new Entity("chat-conversations");
    conversationEntity.setProperty("uuid", conversation.getId().toString());
    conversationEntity.setProperty("owner_uuid", conversation.getOwnerId().toString());
    conversationEntity.setProperty("title", conversation.getTitle());
    conversationEntity.setProperty("creation_time", conversation.getCreationTime().toString());
    datastore.put(conversationEntity);
  }
}
