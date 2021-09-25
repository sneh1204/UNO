package com.example.uno.helpers;

import android.view.View;
import android.widget.ImageView;

import com.example.uno.GlideApp;
import com.example.uno.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class Utils {

    public static final String COLOR_RED = "#FA1005";
    public static final String COLOR_YELLOW = "#EFFA05";
    public static final String COLOR_GREEN = "#2EFA05";
    public static final String COLOR_BLUE = "#055AFA";
    public static final String COLOR_BLACK = "#000000";

    public static final String COLOR_ACTIVE = "#0CFA05";

    /**
     * @param date
     * @return
     */
    public static String getPrettyTime(Date date) {
        PrettyTime time = new PrettyTime();
        return time.format(date);
    }

    public static String getCardColor(String card){
        if(isDraw4(card))   return COLOR_BLACK;
        String colorCode = card.substring(0, 1);
        if(colorCode.equals("Y"))   return COLOR_YELLOW;
        else if(colorCode.equals("R"))   return COLOR_RED;
        else if(colorCode.equals("G"))   return COLOR_GREEN;
        else   return COLOR_BLUE;
    }

    public static String getCardDisplay(String card){
        if(isDraw4(card)) return card;
        else if(isSkip(card))  return "Skip";
        else return card.substring(1);
    }

    public static boolean isDraw4(String card){
        return card.startsWith("+");
    }

    public static boolean isSkip(String card){
        return card.endsWith("S");
    }

    public static void setImage(View view, ImageView image, String uid, String photoRef) {
        if (photoRef != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(uid).child(photoRef);
            GlideApp.with(view)
                    .load(storageReference)
                    .into(image);
        } else {
            GlideApp.with(view)
                    .load(R.drawable.profile_image)
                    .into(image);
        }
    }

}
