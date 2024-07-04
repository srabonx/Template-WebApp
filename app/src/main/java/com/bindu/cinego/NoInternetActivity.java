package com.bindu.cinego;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;

public class NoInternetActivity extends AppCompatActivity
{

    LottieAnimationView lottieAnim;

    TextView noNetText;

    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        lottieAnim = findViewById(R.id.lottieNoNet);
        lottieAnim.playAnimation();

        noNetText = findViewById(R.id.noNetText);
        anim = AnimationUtils.loadAnimation(this,R.anim.splash_screen_anim);

        noNetText.startAnimation(anim);

    }
}