package come.example.pradeep.nimnayaui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Reply extends AppCompatActivity {
    String commentid;
    TextView reply;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    String JSON_STRING;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        //popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,height);

        //ProgressBar
        bar = (ProgressBar)findViewById(R.id.progressBar);

        reply = (EditText) findViewById(R.id.tx_comment);
        commentid = getIntent().getExtras().getString("id");
        Toast.makeText(Reply.this, commentid, Toast.LENGTH_SHORT).show();
        listView = (ListView) findViewById(R.id.listview1);
        contactAdapter = new ContactAdapter(this, R.layout.rowlayout);
        listView.setAdapter(contactAdapter);
        new Reply.BackgroundTask().execute();
    }

    public void Replybtn(View view) {
        String Reply = reply.getText().toString();
        String type = "reply";


        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, Reply, commentid);

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
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("commentid", "UTF-8") + "=" + URLEncoder.encode(commentid, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
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
            json_url = "https://nimnayatesting.000webhostapp.com/Lahiru/getreply.php";
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
            if(json_string != null) {
                displayreply();
                bar.setVisibility(View.INVISIBLE);
            }else {
                int i = 0;
                while (i < 10){
                    Toast.makeText(Reply.this, json_string, Toast.LENGTH_LONG).show();
                i++;
            }
            }

        }
    }

    public void displayreply(){
        try {
            jsonObject = new JSONObject(json_string);
            jsonArray = jsonObject.getJSONArray("server_response");

            int count = 0;
            String userpic;
            String username;
            String comment;

            while(count<jsonArray.length()){
                JSONObject jo = jsonArray.getJSONObject(count);
                userpic = jo.getString("Userpic_url");
                username= jo.getString("User_Name");
                comment = jo.getString("reply");
                Contact contacts = new Contact(userpic,username,comment);
                contactAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
