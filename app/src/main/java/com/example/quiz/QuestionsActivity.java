package com.example.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.quiz.DatabaseQuestions.DatabaseAccess;
import com.example.quiz.OnlyQuestion.OnlyQuestionAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class QuestionsActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private OnlyQuestionAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);

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



        mAdapter.setOnItemClickListener(new OnlyQuestionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(QuestionsActivity.this, OneQuestionActivity.class);
                intent.putExtra("questionId", position+1);


                if(position+1 > 57) {
                    intent.putExtra("helper", 57);
                }

//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                finish();
            }
        });
    }
}
