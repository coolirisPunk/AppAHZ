package com.punkmkt.formula1.models;

/**
 * Created by germanpunk on 28/09/15.
 */
import com.parse.*;

@ParseClassName("Instrument")
public class CustomUser extends ParseObject {
    public CustomUser() {
        // A default constructor is required.
    }

    public String getDisplayName() {
        return getString("displayName");
    }
    public void setDisplayName(String displayName) {
        put("displayName", displayName);
    }

    public ParseUser getOwner() {
        return getParseUser("owner");
    }
    public void setOwner(ParseUser user) {
        put("owner", user);
    }



    public void play() {
        // Ah, that takes me back!
    }
}