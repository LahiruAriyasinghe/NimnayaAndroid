package come.example.pradeep.nimnayaui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button login,register,check;
    TextView email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = (Button)findViewById(R.id.regbtn);
        login = (Button)findViewById(R.id.loginbtn);
        check = (Button)findViewById(R.id.check);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username",email.getText().toString());
                editor.putString("password",password.getText().toString());
                editor.apply();

                Toast.makeText(Login.this,"saved",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (Login.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Login.this, Register.class);
                startActivity(intent);
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if((networkInfo != null) && (networkInfo.isConnected())){
                    Toast.makeText(Login.this,"Device is Connected",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this,"Device is not Connected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
