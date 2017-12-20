package come.example.pradeep.nimnayaui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by pradeep on 11/16/2017.
 */

public class Contact {
    private String username,comment;
    private String userpic;


    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Contact(String userpic, String username, String comment){
        this.setUserpic(userpic);
        this.setUsername(username);
        this.setComment(comment);

    }

}

