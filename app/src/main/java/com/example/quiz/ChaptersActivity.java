package com.example.quiz;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.quiz.DatabaseResults.DatabaseAccessResult;
import com.example.quiz.Others.ExChapter;
import com.example.quiz.Others.ExampleAdapterChapter;
import java.util.ArrayList;

public class ChaptersActivity extends AppCompatActivity {

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
            int resultCount = databaseAccessResult.getRecordCount("Table"+ String.valueOf(i+1));
            int invalid = resultCount - valid;
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
                startActivity(intent);
            }
        });


    }


//    private void loadTestFile(ArrayList<ExQuestion> nextExampleList, String reference) {
//        JSONObject test = createJSONFromFile(R.raw.pytania);
//        JSONArray arr = null;
//        try {
//            arr = test.getJSONArray(reference);
//            for (int i = 0; i < arr.length(); i++)
//            {
//                String number = arr.getJSONObject(i).getString("Number");
//                String question = arr.getJSONObject(i).getString("Question");
//                String wrongAnswer1 = arr.getJSONObject(i).getString("WrongAnswer1");
//                String wrongAnswer2 = arr.getJSONObject(i).getString("WrongAnswer2");
//                String wrongAnswer3 = arr.getJSONObject(i).getString("WrongAnswer3");
//                String wrongAnswer4 = arr.getJSONObject(i).getString("WrongAnswer4");
//                String correctAnswer = arr.getJSONObject(i).getString("CorrectAnswer");
//                nextExampleList.add(new ExQuestion(number, question,  wrongAnswer1, wrongAnswer2, wrongAnswer3, wrongAnswer4, correctAnswer ));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private JSONObject createJSONFromFile(int fileID) {
//        JSONObject result = null;
//        try {
//            InputStream inputStream = getResources().openRawResource(fileID);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            StringBuilder builder = new StringBuilder();
//
//            for (String line = null; (line = reader.readLine()) != null ; ) {
//                builder.append(line).append("\n");
//            }
//
//            String resultStr = builder.toString();
//            JSONTokener tokener = new JSONTokener(resultStr);
//            result = new JSONObject(tokener);
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        return result;
//    }

}
