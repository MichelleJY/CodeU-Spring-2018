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
import org.junit.Assert;
import org.junit.Test;

public class UserProfileTest {

  @Test
  public void testCreate() {
    UUID id = UUID.randomUUID();
    String aboutMe = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
    String profilePicture = "hrefLinkHere";
    Map<String, String> interests = new HashMap<>();
    interests.put("Sports", "Snowboarding");
    Instant lastTimeOnline = Instant.now();
    
    UserProfile userProfile = new UserProfile(id, aboutMe, profilePicture, interests, lastTimeOnline); 
    
    Assert.assertEquals(id, userProfile.getId());
    Assert.assertEquals(aboutMe, userProfile.getAboutMe());
    Assert.assertEquals(profilePicture, userProfile.getProfilePicture());
    Assert.assertEquals(interests, userProfile.getInterests()); 
    Assert.assertEquals(lastTimeOnline, userProfile.getlastTimeOnline());
  }
}