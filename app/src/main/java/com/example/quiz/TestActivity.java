package com.example.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.btn_Answer1) Button btn_Answer1;
    @BindView(R.id.btn_Answer2) Button btn_Answer2;
    @BindView(R.id.btn_Answer3) Button btn_Answer3;
    @BindView(R.id.btn_Answer4) Button btn_Answer4;
    @BindView(R.id.btn_Answer5) Button btn_Answer5;

    @BindView(R.id.btn_Next) Button btn_Next;

    @BindView(R.id.tv_Question) TextView tv_Question;
    @BindView(R.id.tv_1PerAll) TextView tv_1PerAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTitle("Test");




    }
}
