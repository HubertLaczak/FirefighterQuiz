package com.example.quiz;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.example.quiz.DatabaseQuestions.DatabaseAccess;
import com.example.quiz.OnlyQuestion.OnlyQuestionAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class QuestionsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private OnlyQuestionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] aTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable( R.drawable.ic_backarrow ));


        //pobieranie wszystkich pytań, samych pytań
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        String[] questionArray = databaseAccess.getAllQuestions("Table1");
        databaseAccess.close();
        databaseAccess.open();
        String[] questionArray2 = databaseAccess.getAllQuestions("Table2");
        databaseAccess.close();

        int tA = questionArray.length;
        int tB = questionArray2.length;
        String[] concated = new String[tA+tB];
        
        for (int i = 0; i < questionArray.length; i++){
            concated[i] =  questionArray [i];
        }
        for (int i = 0; i < questionArray2.length; i++){
            concated[tA+i] =  questionArray2 [i];
        }

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new OnlyQuestionAdapter(concated);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Resources res = getResources();
        aTitle = res.getStringArray(R.array.chaptersName);


        mAdapter.setOnItemClickListener(new OnlyQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(QuestionsActivity.this, OneQuestionActivity.class);
                intent.putExtra("questionId", position+1);
                int select = 0;

                if(position+1 < 57)
                    select = 0;
                if(position+1 >= 58) {
                    intent.putExtra("helper", 57);
                    select = 1;
                }

                intent.putExtra("title", aTitle[select]);
                startActivity(intent);
            }
        });
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
