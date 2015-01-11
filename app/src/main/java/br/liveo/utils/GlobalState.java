package br.liveo.utils;
import android.app.Application;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by Leonel on 10-01-2015.
 */
public class GlobalState extends Application {

    @Override
    public void onCreate() {

        Log.d("com.parse.push", "successfully entered GlobalState onCreate.");

        // Enable Local Datastore from Parse.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "oLa5rBcC7WP6DuyN62ZBugrYToBSO2TWseZ6p89X", "zEPOBSozh4szqL80cD1BogXTFVjK3xu0PAGbd4XS");
        ParsePush.subscribeInBackground("Castillomax", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        ParseObject installation = new ParseInstallation();
        installation.put("UserMail", "latencio");
        installation.put("UserName", "Leonel Atencio");
        installation.saveInBackground();
        Log.d("com.parse.push", "successfully updated installation details.");

        super.onCreate();
    }

}
