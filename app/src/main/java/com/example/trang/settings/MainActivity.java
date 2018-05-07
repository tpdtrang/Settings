package com.example.trang.settings;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn;
    RadioGroup groupradio1;
    RadioButton radiobtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = groupradio1.getCheckedRadioButtonId();
                radiobtn1 = findViewById(radioId);
                if (radiobtn1.getText().toString().trim().equals("On")) {
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_tudong);
                    TextView textlog = (TextView) dialog.findViewById(R.id.textlog);
                    Button btnkichhoat = (Button) dialog.findViewById(R.id.btnkichhoat);
                    textlog.setVisibility(View.INVISIBLE);
                    dialog.show();
                } else {
                    Toast.makeText(MainActivity.this, "b", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    public void init(){
        btn =(Button) findViewById(R.id.btn);
        groupradio1 = (RadioGroup) findViewById(R.id.groupradio1);
        radiobtn1=(RadioButton) findViewById(R.id.radiobtn1);
    }

    public void checkboxradio(View view) {
        int radioId= groupradio1.getCheckedRadioButtonId();
        radiobtn1= findViewById(radioId);
        Toast.makeText(MainActivity.this, "Ban vua chon "+radiobtn1.getText().toString().trim(),Toast.LENGTH_LONG).show();

    }
}
