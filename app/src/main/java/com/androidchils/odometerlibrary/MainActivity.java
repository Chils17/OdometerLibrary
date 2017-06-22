package com.androidchils.odometerlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        clickListener();

    }

    private void clickListener() {

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOutPut.setText(odometer.getFinalOdoMiterValue());
            }
        });

    }

}
