package com.abhiank.droidconapp.data;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by abhimanyuagrawal on 17/12/15.
 */
@ParseClassName("LookingFor")
public class LookingForItem extends ParseObject{

    public String getLookingFor(){
        return getString("lookingFor");
    }

    public String getName(){
        return getString("name");
    }

    public String getContact(){
        return getString("contactMeAt");
    }

    public String getMoreAboutMe(){
        return getString("linkToMe");
    }
}
