package com.example.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.DatabaseResults.DatabaseAccessResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OneRememberActivity extends AppCompatActivity {

    @BindView(R.id.tv_Question) TextView tv_Question;

    @BindView(R.id.btn_Answer1) Button btn_Answer1;
    @BindView(R.id.btn_Answer2) Button btn_Answer2;
    @BindView(R.id.btn_Answer3) Button btn_Answer3;
    @BindView(R.id.btn_Answer4) Button btn_Answer4;
    @BindView(R.id.btn_Answer5) Button btn_Answer5;

    @BindView(R.id.btn_Next) Button btn_Next;
    @BindView(R.id.btn_Previous) Button btn_Previous;

    @BindView(R.id.tv_1PerAll) TextView tv_1PerAll;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ArrayList<Button> buttonArray = new ArrayList<>();

    private DatabaseAccessResult databaseAccessResult;

    private int position;
    private String[][] tablica;
    private ArrayList<Integer> wykluczone = new ArrayList<>();
    private Menu menu;
    private MenuItem itemStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_remember);
        ButterKnife.bind(this);
        databaseAccessResult = DatabaseAccessResult.getInstance(getApplicationContext());
        buttonArray.addAll(Arrays.asList(btn_Answer1, btn_Answer2, btn_Answer3, btn_Answer4, btn_Answer5));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable( R.drawable.ic_backarrow ));

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0)-1;

        Bundle b = getIntent().getExtras();
        tablica = (String[][])b.getSerializable("Array");
        changeTexts(tablica[position]);
    }

    @OnClick(R.id.btn_Next)
    public void setBtn_Next(){
        itemStar.setIcon(R.drawable.ic_star_yes);
        if(position+1 < tablica.length){
            for( int i = 0; i< wykluczone.size(); i++){
                if ((position+1) == wykluczone.get(i)){
                    position++;
                    break;
                }
            }
            position++;
            changeTexts(tablica[position]);
            resetColors();

            btn_Previous.setEnabled(true);
        } else {
            btn_Next.setEnabled(false);
            Toast.makeText(this, "To jest ostatnie pytanie!", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_Previous)
    public void setBtn_Previous(){
        itemStar.setIcon(R.drawable.ic_star_yes);
        if(position+1 > 1){
            for (int i = 0; i<wykluczone.size(); i++){
                if ((position-1) == wykluczone.get(i)){
                    position--;
                    break;
                }
            }
            position--;
            changeTexts(tablica[position]);
            resetColors();

            btn_Next.setEnabled(true);
        } else {
            btn_Previous.setEnabled(false);
            Toast.makeText(this, "To jest pierwsze pytanie!", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeTexts(String[] newData){
        int k = enableSpareButtons(newData);
        Random rand = new Random();
        int a = rand.nextInt(k);
        tv_Question.setText(tablica[position][0]);
        tv_1PerAll.setText(String.valueOf(position+1) + "/" + String.valueOf(tablica.length-wykluczone.size()));
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
        inflater.inflate(R.menu.menu3, menu);
        this.menu = menu;
        itemStar = menu.findItem(R.id.item1);

//        getIfRemembered(questionId);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                if (item.getIcon().getConstantState().equals(
                        getResources().getDrawable(R.drawable.ic_star_yes).getConstantState())) {
                    item.setIcon(R.drawable.ic_star_no);
                    updateRemember(tablica[position][6], 0);
                        wykluczone.add(position);
                }
                else {
                    item.setIcon(R.drawable.ic_star_yes);
                    updateRemember(tablica[position][6], 1);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateRemember(String questionID, int value) {
        String tabName;
        int numerek = Integer.parseInt(questionID);
        if (numerek < 57)
            tabName = "Table1";
        else
            tabName = "Table2";


        databaseAccessResult.open();
        databaseAccessResult.updateRemember(numerek, value, tabName);
        databaseAccessResult.close();
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

    ///////////////
    private void color5(){
        btn_Answer1.setBackgroundColor(Color.RED);
        btn_Answer2.setBackgroundColor(Color.RED);
        btn_Answer3.setBackgroundColor(Color.RED);
        btn_Answer4.setBackgroundColor(Color.RED);
        btn_Answer5.setBackgroundColor(Color.RED);
    }

    private int checkAnswer(){
        int which = 0;
        if(tablica[position][5].equals(btn_Answer1.getText().toString())) {
            btn_Answer1.setBackgroundColor(Color.GREEN);
            which = 1;
        }
        if(tablica[position][5].equals(btn_Answer2.getText().toString())) {
            btn_Answer2.setBackgroundColor(Color.GREEN);
            which = 2;
        }
        if(tablica[position][5].equals(btn_Answer3.getText().toString())) {
            btn_Answer3.setBackgroundColor(Color.GREEN);
            which = 3;
        }
        if(tablica[position][5].equals(btn_Answer4.getText().toString())) {
            btn_Answer4.setBackgroundColor(Color.GREEN);
            which = 4;
        }
        if(tablica[position][5].equals(btn_Answer5.getText().toString())) {
            btn_Answer5.setBackgroundColor(Color.GREEN);
            which = 5;
        }
        return which;
    }

    private void makeButtonsColor(int which){
        buttonArray.remove(which-1);
        for (int i = 0; i < buttonArray.size(); i++)
            buttonArray.get(i).setBackgroundColor(Color.parseColor("#37000000"));
        buttonArray.clear();
        buttonArray.addAll(Arrays.asList(btn_Answer1, btn_Answer2, btn_Answer3, btn_Answer4, btn_Answer5));
    }


    @OnClick({R.id.btn_Answer1,R.id.btn_Answer2,R.id.btn_Answer3,R.id.btn_Answer4,R.id.btn_Answer5})
    public void afterAnswer(View view){
        color5();
        int which = checkAnswer();
        boolean dobrze = false;
        switch(view.getId()){
            case R.id.btn_Answer1:
                if(which == 1) {
                    makeButtonsColor(which);
                    dobrze = true;
                }
                break;
            case R.id.btn_Answer2:
                if(which == 2) {
                    makeButtonsColor(which);
                    dobrze = true;
                }
                break;
            case R.id.btn_Answer3:
                if(which == 3) {
                    makeButtonsColor(which);
                    dobrze = true;
                }
                break;
            case R.id.btn_Answer4:
                if(which == 4) {
                    makeButtonsColor(which);
                    dobrze = true;
                }
                break;
            case R.id.btn_Answer5:
                if(which == 5) {
                    makeButtonsColor(which);
                    dobrze = true;
                }
                break;
        }
        String tableName = null;
        if(Integer.parseInt(tablica[position][6]) < 57){
            tableName = "Table1";
        }
        if(58< Integer.parseInt(tablica[position][6]) && Integer.parseInt(tablica[position][6]) < 94){
            tableName = "Table2";
        }



        if (dobrze){
            updateCorrect(Integer.parseInt(tablica[position][6]), 2, tableName); //dobrze odpowiedziałem
        } else {
            updateCorrect(Integer.parseInt(tablica[position][6]), 1, tableName); //źle odpowiedziałem
        }


        for (int i = 0; i < buttonArray.size(); i++){
            buttonArray.get(i).setEnabled(false);
        }
    }

    private void updateCorrect(int questionId, int i, String tableName){
        databaseAccessResult.open();
        databaseAccessResult.updateCorrect(questionId, i, tableName);
        databaseAccessResult.close();
    }


    private void resetColors(){
        for (int i = 0; i < buttonArray.size(); i++){
            buttonArray.get(i).setEnabled(true);
            buttonArray.get(i).setBackgroundColor(Color.parseColor("#37000000"));
        }
    }
}
