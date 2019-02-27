package com.example.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.quiz.Others.DatabaseHelper;
import com.example.quiz.Others.ExQuestion;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_AllQuestions) Button btn_AllQuestions;
    @BindView(R.id.btn_Chapters) Button btn_Chapters;
    @BindView(R.id.btn_Test) Button btn_Test;

    final ArrayList<ExQuestion> exampleList_1 = new ArrayList<>();
    final ArrayList<ExQuestion> exampleList_2 = new ArrayList<>();

    DatabaseHelper myDb;
    private  ArrayList<ExQuestion> exampleList;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
//        databaseAccess.open();
//        String raw = databaseAccess.getRaw("3");
//        Toast.makeText(this, raw, Toast.LENGTH_SHORT).show();
//        databaseAccess.close();



//        exampleList = exampleList_1;
//
//        File dbFile = getApplicationContext().getDatabasePath("results.db");
//        if (!dbFile.exists()) {
//            myDb = DatabaseHelper.getInstance(this);
//            addDate(exampleList_1);
//            Toast.makeText(this, "jeden", Toast.LENGTH_SHORT).show();
//        } else {
//            myDb = DatabaseHelper.getInstance(this);
//            Toast.makeText(this, "dwa", Toast.LENGTH_SHORT).show();
//        }

//        File dbFile2 = getApplicationContext().getDatabasePath("questions.db");
//        if (!dbFile2.exists()) {
//            myDb = DatabaseHelper.getInstance(this);
//            addDate(exampleList_1);
//            Toast.makeText(this, "jeden", Toast.LENGTH_SHORT).show();
//        } else {
//            myDb = DatabaseHelper.getInstance(this);
//            Toast.makeText(this, "dwa", Toast.LENGTH_SHORT).show();
//        }
    }


//    public void addDate(ArrayList<ExQuestion> exampleList){
//        for (int i = 0; i < exampleList.size(); i++){
//                myDb.insertData(String.valueOf(R.string.Chapter1), exampleList.get(i).getNumber(),1,0);
//        }
//    }

    @OnClick(R.id.btn_AllQuestions)
    public void setBtn_AllQuestions(){
        Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_Chapters)
    public void setBtn_Chapters(){
        Intent intent = new Intent(MainActivity.this, ChaptersActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btn_Test)
    public void setBtn_Test(){
        Intent intent = new Intent(MainActivity.this, TestActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        this.menu = menu;
//        itemStar = menu.findItem(R.id.item1);
//        getIfRemembered(questionId);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Toast.makeText(this, "Clicked!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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


    //    private void toFile(){
//        //nowy json
//        JSONObject object = new JSONObject();
//        for (int i = 0; i < exampleList_ONE.size(); i++) {
//            try {
//
//                JSONArray array = new JSONArray();
//                JSONObject arrayElementOne = new JSONObject();
//                arrayElementOne.put("Correct", true);
//                arrayElementOne.put("Remember", false);
//
//                array.put(arrayElementOne);
//
//                object.put(String.valueOf(i), array);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        String nowyJson = object.toString();
//
//        //zapis do pliku
//        String filename = "myJSONHubert.json";
//        File file = new File(getApplicationContext().getFilesDir(), filename);
//        FileOutputStream outputStream = null;
//
//        try {
//            outputStream = openFileOutput(filename, MODE_APPEND);
//            outputStream.write(nowyJson.getBytes());
//            outputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_SHORT).show();
//
//
//        //read
//        String ret = "";
//        InputStream inputStream = null;
//        try {
//            inputStream = openFileInput("myJSONHubert.json");
//
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    stringBuilder.append(receiveString);
//                }
//
//                ret = stringBuilder.toString();
//            }
//        }
//        catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        }
//        finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}


