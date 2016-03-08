/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.abhiank.droidconapp;

import android.app.Application;

import com.abhiank.droidconapp.data.AnnouncementItem;
import com.abhiank.droidconapp.data.DiscussItem;
import com.abhiank.droidconapp.data.LookingForItem;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Enable Local Datastore.
//    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    ParseObject.registerSubclass(LookingForItem.class);
    ParseObject.registerSubclass(AnnouncementItem.class);
    ParseObject.registerSubclass(DiscussItem.class);
    Parse.initialize(this);

//    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);
  }
}
