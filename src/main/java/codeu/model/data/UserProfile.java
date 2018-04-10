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

package codeu.model.data;

import java.util.Map;
import java.util.HashMap;
import java.time.Instant;
import java.util.UUID;

/** Class representing a registered user's profile. */
public class UserProfile {
    private final UUID id;
    private String aboutMe;
    private String profilePicture;
    private Map<String,String> interests;
    private Instant lastTimeOnline;

    /**
      * Constructs a new UserProfile.
      *
      * @param id The User ID of this UserProfile
      * @param aboutMe The About Me section of this User's Profile
      * @param profilePicture The link to this user's profile picture
      * @param lastTimeOnline The time the user was last online
      */
    public UserProfile(UUID id, String aboutMe, String profilePicture, Map<String, String> interests, Instant lastTimeOnline) {
        this.id = id;
        this.aboutMe = aboutMe;
        this.profilePicture = profilePicture;
        this.interests = interests;
        this.lastTimeOnline = lastTimeOnline;
    }

    public UUID getId() {
        return this.id;
    }

    public String getAboutMe() {
        return this.aboutMe;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public Map<String, String> getInterests() {
        return this.interests;
    }

    public Instant getlastTimeOnline() {
        return this.lastTimeOnline;
    }
    
    public void setAboutMe(String about){
        this.aboutMe = about;
    }
    
    public void setProfilePicture(String picture){
        this.profilePicture = picture;
    }

    public void addInterest(String category, String interest){
        this.interests.put(category, interest);

    }

    public void setLastTimeOnline(Instant time){
        this.lastTimeOnline = time;
    }
}
