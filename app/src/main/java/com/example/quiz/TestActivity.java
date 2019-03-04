package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.DatabaseQuestions.DatabaseAccess;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.btn_Answer1) Button btn_Answer1;
    @BindView(R.id.btn_Answer2) Button btn_Answer2;
    @BindView(R.id.btn_Answer3) Button btn_Answer3;
    @BindView(R.id.btn_Answer4) Button btn_Answer4;
    @BindView(R.id.btn_Answer5) Button btn_Answer5;

    @BindView(R.id.btn_Next) Button btn_Next;

    @BindView(R.id.tv_Question) TextView tv_Question;
    @BindView(R.id.tv_1PerAll) TextView tv_1PerAll;

    @BindView(R.id.toolbar)  Toolbar toolbar;

    private DatabaseAccess databaseAccess; //referencja do bazy z pytaniami
    private String[] newData = new String[6]; //array który ma pytanie i 5 odpowiedzi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        loadFromDataBase(1, "Table1");

    }

    private void loadFromDataBase(int questionId, String tableName) {
        databaseAccess.open();
        newData = databaseAccess.getName(questionId, tableName);
        databaseAccess.close();
        changeTexts(newData);
    }

    @OnClick(R.id.btn_Next)
    public void setBtn_Next(){
        int max = 56;
        int min = 1;
        int range = max - min + 1;
        int rand = (int)(Math.random() * range) + min;
        loadFromDataBase(rand, "Table1");
    }

    public void changeTexts(String[] newData){
        int k = enableSpareButtons(newData);
        Random rand = new Random();
        int a = rand.nextInt(k);
        tv_Question.setText(newData[0]);
        if (k == 5){
            if (a == 0){
                btn_Answer1.setText(newData[1]);
                btn_Answer2.setText(newData[2]);
                btn_Answer3.setText(newData[3]);
                btn_Answer4.setText(newData[4]);
                btn_Answer5.setText(newData[5]);
            } else if (a == 1){
                btn_Answer1.setText(newData[2]);
                btn_Answer2.setText(newData[3]);
                btn_Answer3.setText(newData[4]);
                btn_Answer4.setText(newData[5]);
                btn_Answer5.setText(newData[1]);
            } else if (a == 2){
                btn_Answer1.setText(newData[3]);
                btn_Answer2.setText(newData[4]);
                btn_Answer3.setText(newData[5]);
                btn_Answer4.setText(newData[1]);
                btn_Answer5.setText(newData[2]);
            } else if (a == 3){
                btn_Answer1.setText(newData[4]);
                btn_Answer2.setText(newData[5]);
                btn_Answer3.setText(newData[1]);
                btn_Answer4.setText(newData[2]);
                btn_Answer5.setText(newData[3]);
            } else {
                btn_Answer1.setText(newData[5]);
                btn_Answer2.setText(newData[1]);
                btn_Answer3.setText(newData[2]);
                btn_Answer4.setText(newData[3]);
                btn_Answer5.setText(newData[4]);
            }
        } else if (k == 4){
            if (a == 0){
                btn_Answer1.setText(newData[1]);
                btn_Answer2.setText(newData[2]);
                btn_Answer3.setText(newData[3]);
                btn_Answer4.setText(newData[5]);
            } else if (a == 1){
                btn_Answer1.setText(newData[5]);
                btn_Answer2.setText(newData[1]);
                btn_Answer3.setText(newData[2]);
                btn_Answer4.setText(newData[3]);
            } else if (a == 2){
                btn_Answer1.setText(newData[3]);
                btn_Answer2.setText(newData[5]);
                btn_Answer3.setText(newData[1]);
                btn_Answer4.setText(newData[2]);
            } else {
                btn_Answer1.setText(newData[2]);
                btn_Answer2.setText(newData[3]);
                btn_Answer3.setText(newData[5]);
                btn_Answer4.setText(newData[1]);
            }
        } else {
            if (a == 0){
                btn_Answer1.setText(newData[1]);
                btn_Answer2.setText(newData[2]);
                btn_Answer3.setText(newData[5]);
            } else if (a == 1){
                btn_Answer1.setText(newData[2]);
                btn_Answer2.setText(newData[5]);
                btn_Answer3.setText(newData[1]);
            } else {
                btn_Answer1.setText(newData[5]);
                btn_Answer2.setText(newData[2]);
                btn_Answer3.setText(newData[1]);
            }
        }

    }

    private int enableSpareButtons(String[] newData){
        int k = 5;
        if (newData[4] == null){
            btn_Answer5.setVisibility(View.GONE);
            k = 4;
        } else {
            btn_Answer5.setVisibility(View.VISIBLE);
        }
        if (newData[3] == null) {
            k = 3;
            btn_Answer4.setVisibility(View.GONE);
        } else {
            btn_Answer4.setVisibility(View.VISIBLE);
        }
        return k;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}
