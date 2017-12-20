package come.example.pradeep.nimnayaui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ViewFlipper;
import android.view.animation.Animation;

import static android.R.style.Animation;

public class Home extends AppCompatActivity {

    ViewFlipper viewFlipper;
    Animation fadeIn,fadeOut;
    ImageButton button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setViewFlipper();
        button1 = (ImageButton)findViewById(R.id.sixenglish);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (Home.this, Subject.class);
                startActivity(intent);
            }
        });
    }


    public void setViewFlipper(){
        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(fadeIn);
        viewFlipper.setOutAnimation(fadeOut);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();
    }
}
