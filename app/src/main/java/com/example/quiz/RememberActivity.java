package com.example.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.quiz.DatabaseQuestions.DatabaseAccess;
import com.example.quiz.DatabaseResults.DatabaseAccessResult;
import com.example.quiz.OnlyQuestion.OnlyQuestionAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RememberActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.tv_EmptyList) TextView tv_EmptyList;

    @BindView(R.id.toolbar) Toolbar toolbar;

    private OnlyQuestionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private DatabaseAccessResult databaseAccessResult;
    private DatabaseAccess databaseAccess;
    private String[] numberArray1;
    private String[] numberArray2;

    private ArrayList<String> questionArray1 = new ArrayList<String>();
    private ArrayList<String> questionArray2 = new ArrayList<String>();
    private String[] concatedText;

    private String[][] tablica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable( R.drawable.ic_backarrow ));

        getQuestionNumber();
        concatedText = new String [tablica.length];

        for (int i = 0; i< tablica.length; i++){
            concatedText[i] = tablica[i][0];
        }

        if(concatedText.length == 0){
            mRecyclerView.setVisibility(View.GONE);
            tv_EmptyList.setVisibility(View.VISIBLE);
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OnlyQuestionAdapter(concatedText);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnlyQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(RememberActivity.this, OneRememberActivity.class);
                intent.putExtra("position", position+1);
                intent.putExtra("tablica", tablica);

                Bundle b = new Bundle();
                b.putSerializable("Array", tablica);
                intent.putExtras(b);

                startActivity(intent);
            }
        });
    }

    private void getQuestionNumber() {
        databaseAccessResult = DatabaseAccessResult.getInstance(getApplicationContext());
        databaseAccessResult.open();

            numberArray1 = databaseAccessResult.getRememberedQuestion("Table1");
            numberArray2 = databaseAccessResult.getRememberedQuestion("Table2");

        databaseAccessResult.close();
        getQuestionText(numberArray1, numberArray2);

    } // pobieranie numerów pytań, które są zapamiętane - numery są później wykorzystywane do pobierania pytań

    private void getQuestionText(String[] numberArray1, String[] numberArray2) {
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        int size = numberArray1.length + numberArray2.length;
        tablica = new String[size][7];

        for (int i = 0; i < numberArray1.length; i++) {
            String[] text = databaseAccess.getQuestionText("Table1", numberArray1[i]);
            for (int k = 0; k < 6; k++){
                tablica[i][k] = text[k];
                tablica[i][6] = String.valueOf(numberArray1[i]);
            }
        }
        for (int i = 0; i < numberArray2.length; i++) {
            String[] text = databaseAccess.getQuestionText("Table2", numberArray2[i]);
            for (int k = 0; k < 6; k++){
                tablica[i+numberArray1.length][k] = text[k];
                tablica[i+numberArray1.length][6] = String.valueOf(numberArray2[i]);
            }
        }


        int tA = questionArray1.size();
        int tB = questionArray2.size();

        concatedText = new String[tA+tB];

        for (int i = 0; i < questionArray1.size(); i++){
            concatedText[i] =  questionArray1.get(i);
        }
        for (int i = 0; i < questionArray2.size(); i++){
            concatedText[tA+i] =  questionArray2.get(i);
        }

        databaseAccess.close();
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
