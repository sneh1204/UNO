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

    /**
     * @param date
     * @return
     */
    public static String getPrettyTime(Date date) {
        PrettyTime time = new PrettyTime();
        return time.format(date);
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
