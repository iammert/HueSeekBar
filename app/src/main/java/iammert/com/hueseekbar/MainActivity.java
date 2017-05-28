package iammert.com.hueseekbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import iammert.com.huelib.VerticalAnimationListener;
import iammert.com.huelib.HueSeekBar;
import iammert.com.huelib.ProgressListener;

public class MainActivity extends AppCompatActivity {

    TextView textViewTitle;
    TextView textViewPercentage;
    HueSeekBar hueSeekBar;

    TextView textViewTitle2;
    TextView textViewPercentage2;
    HueSeekBar hueSeekBar2;

    TextView textViewTitle3;
    TextView textViewPercentage3;
    HueSeekBar hueSeekBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewPercentage = (TextView) findViewById(R.id.percentage);
        hueSeekBar = (HueSeekBar) findViewById(R.id.hueSeekBar);

        textViewTitle2 = (TextView) findViewById(R.id.textViewTitle2);
        textViewPercentage2 = (TextView) findViewById(R.id.percentage2);
        hueSeekBar2 = (HueSeekBar) findViewById(R.id.hueSeekBar2);

        textViewTitle3 = (TextView) findViewById(R.id.textViewTitle3);
        textViewPercentage3 = (TextView) findViewById(R.id.percentage3);
        hueSeekBar3 = (HueSeekBar) findViewById(R.id.hueSeekBar3);

        hueSeekBar.setProgressListener(new ProgressListener() {
            @Override
            public void onProgressChange(final int progress) {
                textViewPercentage.setText("%" + progress + " brightness");
            }
        });

        hueSeekBar.setVerticalAnimationListener(new VerticalAnimationListener() {
            @Override
            public void onAnimProgressChanged(int percentage) {
                if(percentage > 80){
                    textViewPercentage.animate().alpha(1).setDuration(0).start();
                    textViewTitle.animate().alpha(0).setDuration(0).start();
                }else{
                    textViewPercentage.animate().alpha(0).setDuration(0).start();
                    textViewTitle.animate().alpha(1).setDuration(0).start();
                }
            }
        });

        hueSeekBar2.setProgressListener(new ProgressListener() {
            @Override
            public void onProgressChange(final int progress) {
                textViewPercentage2.setText("%" + progress + " brightness");
            }
        });

        hueSeekBar2.setVerticalAnimationListener(new VerticalAnimationListener() {
            @Override
            public void onAnimProgressChanged(int percentage) {
                if(percentage > 80){
                    textViewPercentage2.animate().alpha(1).setDuration(0).start();
                    textViewTitle2.animate().alpha(0).setDuration(0).start();
                }else{
                    textViewPercentage2.animate().alpha(0).setDuration(0).start();
                    textViewTitle2.animate().alpha(1).setDuration(0).start();
                }
            }
        });

        hueSeekBar3.setProgressListener(new ProgressListener() {
            @Override
            public void onProgressChange(final int progress) {
                textViewPercentage3.setText("%" + progress + " brightness");
            }
        });

        hueSeekBar3.setVerticalAnimationListener(new VerticalAnimationListener() {
            @Override
            public void onAnimProgressChanged(int percentage) {
                if(percentage > 80){
                    textViewPercentage3.animate().alpha(1).setDuration(0).start();
                    textViewTitle3.animate().alpha(0).setDuration(0).start();
                }else{
                    textViewPercentage3.animate().alpha(0).setDuration(0).start();
                    textViewTitle3.animate().alpha(1).setDuration(0).start();
                }
            }
        });
    }
}
