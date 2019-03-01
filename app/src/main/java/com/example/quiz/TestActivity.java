package com.example.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.quiz.DatabaseQuestions.DatabaseAccess;
import java.util.Random;
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


    private DatabaseAccess databaseAccess; //referencja do bazy z pytaniami
    private String[] newData = new String[6]; //array który ma pytanie i 5 odpowiedzi


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        setTitle("Test");

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

}
