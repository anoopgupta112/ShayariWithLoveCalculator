package com.ACG.sayari;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ACG.sayari.LoveCalculator.home;
import com.ACG.sayari.catagory.Happy;
import com.ACG.sayari.catagory.Motivational;
import com.ACG.sayari.catagory.Romantic;
import com.ACG.sayari.catagory.Sad;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button romantic_btn,sad_btn,happy_btn,motivational_btn;
    LottieAnimationView heart;
    ProgressDialog dialog;


    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dialog = new ProgressDialog(this);
        dialog.setMessage("wait");

        dialog.setCancelable(false);





        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

//        ca-app-pub-8043978777240817/6485126496  orignal
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8043978777240817/7357007423");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);








        romantic_btn=findViewById(R.id.romantic_button);
        sad_btn=findViewById(R.id.sad_button);
        happy_btn=findViewById(R.id.happy_button);
        motivational_btn=findViewById(R.id.motivational_button);
        heart = findViewById(R.id.heart);





        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heart.setMinAndMaxProgress(0.5f,1.0f);
                heart.playAnimation();

                Intent slide = new Intent(MainActivity.this, home.class);
                Bundle bnaniamtion = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim1,R.anim.anim2).toBundle();
                startActivity(slide, bnaniamtion);
            }
        });








        romantic_btn.setOnClickListener(this);
        sad_btn.setOnClickListener(this);
        happy_btn.setOnClickListener(this);
        motivational_btn.setOnClickListener(this);





    }





    @Override
    public void onClick(final View view) {


        if (mInterstitialAd.isLoaded()) {


            mInterstitialAd.show();
//            dialog.dismiss();

        }
        else {
//            dialog.show();
//            Log.d("TAG", "The interstitial wasn't loaded yet.");


            switch (view.getId()) {

                case R.id.romantic_button:


                    Intent intent1 = new Intent(MainActivity.this, Romantic.class);

                    startActivity(intent1);

                    break;

                case R.id.sad_button:
                    Intent intent2 = new Intent(MainActivity.this, Sad.class);
                    startActivity(intent2);
                    break;

                case R.id.motivational_button:
                    Intent intent3 = new Intent(MainActivity.this, Motivational.class);
                    startActivity(intent3);
                    break;

                case R.id.happy_button:
                    Intent intent4 = new Intent(MainActivity.this, Happy.class);
                    startActivity(intent4);
                    break;
            }

            }
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                switch (view.getId()) {

                    case R.id.romantic_button:


                        Intent intent1 = new Intent(MainActivity.this, Romantic.class);

                        startActivity(intent1);

                        break;

                    case R.id.sad_button:
                        Intent intent2 = new Intent(MainActivity.this, Sad.class);
                        startActivity(intent2);
                        break;

                    case R.id.motivational_button:
                        Intent intent3 = new Intent(MainActivity.this, Motivational.class);
                        startActivity(intent3);
                        break;

                    case R.id.happy_button:
                        Intent intent4 = new Intent(MainActivity.this, Happy.class);
                        startActivity(intent4);
                        break;


//
                }



                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });



    }
}