package com.example.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Random;
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
}
