package com.example.andikakamiswara.iklan;

import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {
    InterstitialAd interstitial = new InterstitialAd(MainActivity.this);
    AdRequest adRequest = new AdRequest.Builder().build();
    private static long back_pressed;
    public int nilai = 0;
    public int reset = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // iklan interstitial
        interstitial.setAdUnitId("ca-app-pub-1099831569454372/9936798414");
        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);
        adView.loadAd(adRequest);
        // Load ads into Banner Ads
        adView.loadAd(adRequest);
        // Load ads into Interstitial Ads
       // interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });

        final Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        final Button btn_tasbih = (Button) findViewById(R.id.tasbih);
        btn_tasbih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               nilai++;
               if(nilai == 33 || nilai == 66 || nilai == 99) {
                   vibrator.vibrate(600);
                   btn_tasbih.setTextColor(Color.RED);
               } else {
                   vibrator.vibrate(20);
                   btn_tasbih.setTextColor(Color.BLUE);
               }
                if(nilai > 99) {
                    nilai = 0;
                }
                String nilai_string = String.valueOf(nilai);
                btn_tasbih.setText(nilai_string);
            }
        });

        Button btn_reset = (Button) findViewById(R.id.reset);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nilai = 0; reset++;
                String nilai_string = String.valueOf(nilai);
                btn_tasbih.setText(nilai_string);
                if(reset%3==0) {
                    interstitial.loadAd(adRequest);
                    displayInterstitial();

                }
            }
        });
    }// end on create

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

    }

    public void onBackPressed() {
        interstitial.loadAd(adRequest);
        displayInterstitial();
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

}
