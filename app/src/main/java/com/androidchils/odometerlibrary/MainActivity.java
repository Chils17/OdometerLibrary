package com.androidchils.odometerlibrary;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidchils.odometer.Odometer;

public class MainActivity extends AppCompatActivity {

    private Odometer odometer;
    private TextView tvOutPut;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        odometer = (Odometer) findViewById(R.id.odometer);
        tvOutPut = (TextView) findViewById(R.id.tvOutPut);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        Odometer odometer = new Odometer.Builder(this)
                .background(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black))
                .font(getString(R.string.lato_regular))
                .reading("1234")
                .slot(4)
                .textColor(ContextCompat.getColor(this, R.color.white))
                .textSize(18)
                .build();
        ((LinearLayout) findViewById(R.id.linear)).addView(odometer);

        Odometer odo = new Odometer.Builder(this)
                .textSize(18)
                .textColor(ContextCompat.getColor(this, R.color.white))
                .reading("12345")
                .slot(5)
                .font(getString(R.string.lato_regular))
                .background(ContextCompat.getColor(this, R.color.white), ContextCompat.getColor(this, R.color.black))
                .build();

        ((LinearLayout) findViewById(R.id.linear)).addView(odo);

        clickListener();

    }


    private void clickListener() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutPut.setText(odometer.getFinalOdometerValue());
            }
        });
    }

}
