package com.example.uno.helpers;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class Utils {

    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int PHOTO_REF = 2;

    /**
     * @param date
     * @return
     */
    public static String getPrettyTime(Date date) {
        PrettyTime time = new PrettyTime();
        return time.format(date);
    }

}
