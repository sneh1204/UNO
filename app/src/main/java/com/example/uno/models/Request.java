package com.example.uno.models;

import com.example.uno.helpers.Utils;

import java.util.ArrayList;
import java.util.Date;

public class Request {

    String id;

    ArrayList<String> requester;

    Date created_at;

    public String getRequesterId() {
        return requester.get(Utils.ID);
    }

    public String getRequesterRef() {
        return requester.get(Utils.PHOTO_REF);
    }

    public String getRequesterName() {
        return requester.get(Utils.NAME);
    }

    public ArrayList<String> getRequester() {
        return requester;
    }

    public void setRequester(ArrayList<String> requester) {
        this.requester = requester;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
