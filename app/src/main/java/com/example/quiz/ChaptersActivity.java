package com.example.quiz;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.example.quiz.DatabaseResults.DatabaseAccessResult;
import com.example.quiz.Others.ExChapter;
import com.example.quiz.Others.ExampleAdapterChapter;

import java.util.ArrayList;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChaptersActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ArrayList<ExChapter> chaptersList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ExampleAdapterChapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String[] aTitle;

    private DatabaseAccessResult databaseAccessResult; //referencja do bazy z wynikami



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable( R.drawable.ic_backarrow ));

        Resources res = getResources();
        aTitle = res.getStringArray(R.array.chaptersName);
        for (int i = 0; i < aTitle.length; i++){
            chaptersList.add(new ExChapter("  "+String.valueOf(i+1) + ". ",aTitle[i], 11, 11,11 ));
        }

        databaseAccessResult = DatabaseAccessResult.getInstance(getApplicationContext());
//        for (int i = 0; i <aTitle.length; i++){
        for (int i = 0; i < 2; i++){
            databaseAccessResult.open();
            int valid = databaseAccessResult.getCorrectCount("Table"+ String.valueOf(i+1));
            int invalid = databaseAccessResult.getWrongCount("Table"+ String.valueOf(i+1));
            int remembered = databaseAccessResult.getRememberCount("Table"+ String.valueOf(i+1));
            databaseAccessResult.close();

            chaptersList.get(i).setCorrect(valid);
            chaptersList.get(i).setIncorrect(invalid);
            chaptersList.get(i).setRemembered(remembered);
        }


        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapterChapter(chaptersList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new ExampleAdapterChapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int questionId = 1, helper = 0;
                switch (position+1) {
                    case 1: questionId = 1;
                            helper = 0;
                            break;
                    case 2: questionId = 58;
                            helper = 57;
                            break;
//                    case 3: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 4: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 5: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 6: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 7: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 8: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 9: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 10: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 11: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 12: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 13: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 14: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 15: questionId = 58;
//                        helper = 57;
//                        break;
//                    case 16: questionId = 58;
//                        helper = 57;
//                        break;
                }

                Intent intent = new Intent(ChaptersActivity.this, OneQuestionActivity.class);
                intent.putExtra("questionId", questionId);
                intent.putExtra("helper", helper);
                intent.putExtra("title", aTitle[position]);
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
