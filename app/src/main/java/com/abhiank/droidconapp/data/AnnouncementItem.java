package com.abhiank.droidconapp.data;

import com.abhiank.droidconapp.util.Utils;
import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by abhimanyuagrawal on 17/12/15.
 */
@ParseClassName("Announcement")
public class AnnouncementItem extends ParseObject{

    public String getAnnouncement(){
        return getString("announcementText");
    }

    public String getTime(){
        return Utils.contextDateText(getCreatedAt());
    }

    public String getPostBy(){
        return getString("announceBy");
    }
}
