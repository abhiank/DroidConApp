package com.abhiank.droidconapp.data;

import com.abhiank.droidconapp.util.Utils;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by abhimanyuagrawal on 17/12/15.
 */
@ParseClassName("Discuss")
public class DiscussItem extends ParseObject{

    public String getTopic(){
        return getString("topicTitle");
    }

    public String getTime(){
        return Utils.contextDateText(getCreatedAt());
    }

    public String getPostBy(){
        return getString("creatorName");
    }

    public String getComments(){
        return getString("comments");
    }
}
