package come.example.pradeep.nimnayaui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static come.example.pradeep.nimnayaui.R.id.tx_userpic;

/**
 * Created by pradeep on 11/16/2017.
 */

public class ContactAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public ContactAdapter(Context context, int resource) {

        super(context, resource);
    }


    public void add(Contact object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.rowlayout,parent,false);
            contactHolder = new ContactHolder();
            contactHolder.tx_userpic = (ImageView)row.findViewById(tx_userpic);
            contactHolder.tx_username = (TextView)row.findViewById(R.id.tx_username);
            contactHolder.tx_comment = (TextView)row.findViewById(R.id.tx_comment);
            row.setTag(contactHolder);

        }
        else
        {
            contactHolder = (ContactHolder)row.getTag();
        }
        Contact contacts = (Contact)this.getItem(position);
        // contactHolder.tx_userpic.setImageBitmap(contacts.getUserpic());
        Picasso.with(getContext()).load(contacts.getUserpic()).into(contactHolder.tx_userpic);
        contactHolder.tx_username.setText(contacts.getUsername());
        contactHolder.tx_comment.setText(contacts.getComment());
        return row;
    }

    static class ContactHolder
    {
        TextView tx_comment,tx_username;
        ImageView tx_userpic;
    }




}

