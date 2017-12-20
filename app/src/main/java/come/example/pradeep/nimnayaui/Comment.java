package come.example.pradeep.nimnayaui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import static android.R.attr.id;

public class Comment extends AppCompatActivity {
    Button replybtn;
    String[] id;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    EditText comment;
    String json_string;
    String JSON_STRING;
    ProgressBar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

       //popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,height);

        //progressbar
        bar = (ProgressBar)findViewById(R.id.progressBar);

        new BackgroundTask().execute();
        replybtn = (Button)findViewById(R.id.replybtn);
        listView = (ListView) findViewById(R.id.listview);
        comment = (EditText)findViewById(R.id.tx_comment);
        contactAdapter = new ContactAdapter(this,R.layout.rowlayout);
        listView.setAdapter(contactAdapter);



    }

    class BackgroundTask extends AsyncTask<Void,Integer,String> {
        String json_url;

        @Override
        protected String doInBackground(Void... voids) {
            for (int i = 0; i <= 10; i++) {
                publishProgress(i);


                try {
                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((JSON_STRING = bufferedReader.readLine()) != null) {
                        stringBuilder.append(JSON_STRING + "\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                return null;
            }

        @Override
        protected void onPreExecute() {
            json_url = "https://nimnayatesting.000webhostapp.com/Lahiru/json_get_data1.php";
            bar.setMax(100);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            bar.setProgress(values[0]);
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String result) {
            json_string = result;
            displaycomment();
            bar.setVisibility(View.INVISIBLE);
        }
    }

    public void Commentbtn(View view) {
        String Comment = comment.getText().toString();
        String type = "comment";


        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, Comment);


    }

    public void reply(View view){
        Intent intent = new Intent(Comment.this,Reply.class);
        int position = listView.getPositionForView(view);

        intent.putExtra("id",id[position]);
        startActivity(intent);

    }

    public void displaycomment(){

        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String userpic;
            String username;
            String comment;

            id = new String[jsonArray.length()];
            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                id[count] = jo.getString("Comment ID");
                userpic = jo.getString("Userpic_url");
                username= jo.getString("User_Name");
                comment = jo.getString("Comment");
                Contact contacts = new Contact(userpic,username,comment);
                contactAdapter.add(contacts);
                count++;
            }
            Toast.makeText(Comment.this,id[0]+id[1]+id[2],Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
