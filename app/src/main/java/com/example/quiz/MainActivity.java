package com.example.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_AllQuestions) Button btn_AllQuestions;
    @BindView(R.id.btn_Chapters) Button btn_Chapters;
    @BindView(R.id.btn_Test) Button btn_Test;
    @BindView(R.id.btn_Remember) Button btn_Remember;

    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

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

    @OnClick(R.id.btn_Remember)
    public void setBtn_Remember(){
        Intent intent = new Intent(MainActivity.this, RememberActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(intent);
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


