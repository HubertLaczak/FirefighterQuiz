package com.example.quiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.quiz.DatabaseQuestions.DatabaseAccess;
import com.example.quiz.DatabaseResults.DatabaseAccessResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class OneQuestionActivity extends AppCompatActivity {

    @BindView(R.id.tv_Question) TextView tv_Question;

    @BindView(R.id.btn_Answer1) Button btn_Answer1;
    @BindView(R.id.btn_Answer2) Button btn_Answer2;
    @BindView(R.id.btn_Answer3) Button btn_Answer3;
    @BindView(R.id.btn_Answer4) Button btn_Answer4;
    @BindView(R.id.btn_Answer5) Button btn_Answer5;

    @BindView(R.id.btn_Next) Button btn_Next;
    @BindView(R.id.btn_Previous) Button btn_Previous;


    @BindView(R.id.tv_Correct) TextView tv_Correct;
    @BindView(R.id.tv_Incorrent) TextView tv_Incorrent;
    @BindView(R.id.tv_Remember) TextView tv_Remember;

    @BindView(R.id.tv_1PerAll) TextView tv_1PerAll;

    private ArrayList<Button> buttonArray = new ArrayList<>();

    private DatabaseAccess databaseAccess; //referencja do bazy z pytaniami
    private DatabaseAccessResult databaseAccessResult; //referencja do bazy z wynikami
    private String[] newData = new String[6]; //array który ma pytanie i 5 odpowiedzi

    private int questionId; //id pytania, z tym, że nie od 0 a od 1
    private int qCount;  //liczba rekordów, czyli liczba pytań w danej kategorii (Pytania.db)
    private int resultCount; //liczba rekordów, czyli liczba numerów w danej kategorii (Wyniki.db)
    private int remembered; //mówi, czy dane pytanie jest zapamiętane
    private String tableName; //mówi którą tabelę trzeba otworzyc - Tab1/2/3..
    int helper; //mówi o ile trzeba przesunąć questionId dla wyświetlania i zapisywania danych

    private Menu menu;
    private MenuItem itemStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_question);
        ButterKnife.bind(this);
        buttonArray.addAll(Arrays.asList(btn_Answer1, btn_Answer2, btn_Answer3, btn_Answer4, btn_Answer5));

        resetColors();
        Intent intent = getIntent();
        questionId = intent.getIntExtra("questionId", 0);
        helper = intent.getIntExtra("helper", 0);

        chooseTable(questionId);
        //baza z pytaniami
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        loadFromDataBase(questionId, tableName);
        howManyQuestions(tableName);

        //baza z wynikami
        databaseAccessResult = DatabaseAccessResult.getInstance(getApplicationContext());
        databaseAccessResult.open();
        resultCount = databaseAccessResult.getRecordCount(tableName);
        loadUICorrect(tableName);
        loadUIRemember(tableName);

        tv_1PerAll.setText( (questionId - helper) + "/" + qCount);
    }

    private void chooseTable(int questionId){
         if(questionId > 57 ){
             tableName = "Table2";
         } else {
             tableName = "Table1";
         }
    }


    private void loadUICorrect(String tableName) {
        databaseAccessResult.open();
        int valid = databaseAccessResult.getCorrectCount(tableName);
        int invalid = databaseAccessResult.getWrongCount(tableName);
        databaseAccessResult.close();

        tv_Correct.setText(String.valueOf(valid) + "/" + String.valueOf(resultCount));
        tv_Incorrent.setText(String.valueOf(invalid) + "/" + String.valueOf(resultCount));
    }

    private void loadUIRemember(String tableName) {
        databaseAccessResult.open();
        int remembered = databaseAccessResult.getRememberCount(tableName);
        databaseAccessResult.close();
        tv_Remember.setText(String.valueOf(remembered) + "/" + String.valueOf(resultCount));
    }

    private void getIfRemembered(int questionId){
        databaseAccessResult.open();
        remembered = databaseAccessResult.getIfRemembered(questionId, tableName);
        databaseAccessResult.close();

        if (remembered == 1) {
            itemStar.setIcon(R.drawable.ic_star_yes);
        } else {
            itemStar.setIcon(R.drawable.ic_star_no);
        }
    }

    private void updateCorrect(int questionId, int i, String tableName){
        databaseAccessResult.open();
        databaseAccessResult.updateCorrect(questionId, i, tableName);
        databaseAccessResult.close();
    }

    private void updateRemember(int questionId, int i, String tableName){
        databaseAccessResult.open();
        databaseAccessResult.updateRemember(questionId, i, tableName);
        databaseAccessResult.close();
    }

    private void howManyQuestions(String tableName) {
        databaseAccess.open();
        qCount = databaseAccess.getRecordsCount(tableName);
        databaseAccess.close();
    }

    private void loadFromDataBase(int questionId, String tableName) {
        databaseAccess.open();
        newData = databaseAccess.getName(questionId, tableName);
        databaseAccess.close();
        changeTexts(newData);
    }

    private void deleteResults() {
        databaseAccessResult.open();
        databaseAccessResult.deleteResults();
        databaseAccessResult.close();
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

    @OnClick(R.id.btn_Next)
    public void toNextQuestion(){
        if(questionId-helper < qCount){
            questionId++;
            loadFromDataBase(questionId, tableName);
            btn_Previous.setEnabled(true);
            resetColors();
            getIfRemembered(questionId);
            tv_1PerAll.setText( questionId-helper + "/" + qCount);
        } else {
            btn_Next.setEnabled(false);
        }
    }

    @OnClick(R.id.btn_Previous)
    public void toPreviousQuestion(){
        if(questionId-helper < 2){
            btn_Previous.setEnabled(false);
        } else {
            questionId--;
            loadFromDataBase(questionId, tableName);
            btn_Next.setEnabled(true);
            resetColors();
            getIfRemembered(questionId);
            tv_1PerAll.setText( questionId-helper + "/" + qCount);

        }
    }

    @OnLongClick(R.id.btn_Previous)
    public boolean uno(){
        questionId = 1;
        loadFromDataBase(questionId, tableName);
        btn_Previous.setEnabled(false);
        btn_Next.setEnabled(true);
        resetColors();
        getIfRemembered(questionId);
        tv_1PerAll.setText( questionId-helper+ "/" + qCount);
        return true;
    }

    @OnLongClick(R.id.btn_Next)
    public boolean dos(){
        questionId = qCount;
        loadFromDataBase(questionId, tableName);
        btn_Next.setEnabled(false);
        btn_Previous.setEnabled(true);
        resetColors();
        getIfRemembered(questionId);
        tv_1PerAll.setText( questionId-helper + "/" + qCount);
        return true;
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

    private void color5(){
        btn_Answer1.setBackgroundColor(Color.RED);
        btn_Answer2.setBackgroundColor(Color.RED);
        btn_Answer3.setBackgroundColor(Color.RED);
        btn_Answer4.setBackgroundColor(Color.RED);
        btn_Answer5.setBackgroundColor(Color.RED);
    }

    private int checkAnswer(){
        int which = 0;
        if(newData[5].equals(btn_Answer1.getText().toString())) {
            btn_Answer1.setBackgroundColor(Color.GREEN);
            which = 1;
        }
        if(newData[5].equals(btn_Answer2.getText().toString())) {
            btn_Answer2.setBackgroundColor(Color.GREEN);
            which = 2;
        }
        if(newData[5].equals(btn_Answer3.getText().toString())) {
            btn_Answer3.setBackgroundColor(Color.GREEN);
            which = 3;
        }
        if(newData[5].equals(btn_Answer4.getText().toString())) {
            btn_Answer4.setBackgroundColor(Color.GREEN);
            which = 4;
        }
        if(newData[5].equals(btn_Answer5.getText().toString())) {
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
        if (dobrze){
            updateCorrect(questionId, 2, tableName); //dobrze odpowiedziałem
        } else {
            updateCorrect(questionId, 1, tableName); //źle odpowiedziałem
        }

        loadUICorrect(tableName);
        setDisabled();
    }


    private void resetColors(){
        for (int i = 0; i < buttonArray.size(); i++){
            buttonArray.get(i).setEnabled(true);
            buttonArray.get(i).setBackgroundColor(Color.parseColor("#37000000"));
        }
    }

    public void setDisabled(){
        btn_Answer1.setEnabled(false);
        btn_Answer2.setEnabled(false);
        btn_Answer3.setEnabled(false);
        btn_Answer4.setEnabled(false);
        btn_Answer5.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menu = menu;
        itemStar = menu.findItem(R.id.item1);
        getIfRemembered(questionId);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                if (item.getIcon().getConstantState().equals(
                        getResources().getDrawable(R.drawable.ic_star_yes).getConstantState())) {
                    item.setIcon(R.drawable.ic_star_no);
                    updateRemember(questionId, 0, tableName);
                }
                else {
                item.setIcon(R.drawable.ic_star_yes);
                updateRemember(questionId, 1, tableName);
                }
                break;
            case R.id.item2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder .setMessage("Czy chcesz skasować wyniki w tym rozdziale?")
                        .setCancelable(false)
                        .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteResults();
                                loadUICorrect(tableName);
                                loadUIRemember(tableName);
                                itemStar.setIcon(R.drawable.ic_star_no);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
                break;
        }
        loadUIRemember(tableName);
        return super.onOptionsItemSelected(item);
    }


}
