package com.example.quiz;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.example.quiz.DatabaseResults.DatabaseAccessResult;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsActivity extends AppCompatActivity {
    @BindView(R.id.tv_Res0) TextView tv_Res0;
    @BindView(R.id.tv_Res1) TextView tv_Res1;
    @BindView(R.id.tv_Res2) TextView tv_Res2;
    @BindView(R.id.tv_Res3) TextView tv_Res3;
    @BindView(R.id.tv_Res4) TextView tv_Res4;

    @BindView(R.id.tv_noChart) TextView tv_noChart;


    @BindView(R.id.chart) PieChart chart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    double[] results = new double[3];
    double sumaPytan;
    String description [] = {"Dobrze", "Źle", "Brak odpowiedzi"};

    private DatabaseAccessResult databaseAccessResult; //referencja do bazy z wynikami

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true);
        getSupportActionBar().setHomeAsUpIndicator( getResources().getDrawable( R.drawable.ic_backarrow ));

        setData();
        if (results[0] != 0 && results[1] != 0) {
            setupPieChart();
            tv_noChart.setVisibility(View.GONE);
        }
        else {
            chart.setVisibility(View.GONE);
            tv_noChart.setVisibility(View.VISIBLE);
        }

    }



    private void setData() {
        databaseAccessResult = DatabaseAccessResult.getInstance(getApplicationContext());
        databaseAccessResult.open();

        for (int i = 0; i < 2; i++){
            double valid = databaseAccessResult.getCorrectCount("Table"+ String.valueOf(i+1));
            double invalid = databaseAccessResult.getWrongCount("Table"+ String.valueOf(i+1));
            int ile = databaseAccessResult.getRecordCount("Table"+ String.valueOf(i+1));
            double resultCount = databaseAccessResult.getRecordCount("Table"+ String.valueOf(i+1));
            resultCount = resultCount - valid - invalid;
            results[0] += valid;
            results[1] += invalid;
            results[2] += resultCount;
            sumaPytan +=ile;
        }
        databaseAccessResult.close();

        tv_Res0.setText(String.format("%.0f", sumaPytan));
        tv_Res1.setText(String.format("%.0f", results[0]));
        tv_Res2.setText(String.format("%.0f", results[1]));
        tv_Res3.setText(String.format("%.0f", results[2]));

        float a = (float) (results[0]/sumaPytan*100);
        tv_Res4.setText(String.format("%.1f", a) + "%");

    }

    private void setupPieChart() {
        List<PieEntry> pieEntries = new ArrayList<>();
            pieEntries.add(new PieEntry((float) results[0], description[0]));
            pieEntries.add(new PieEntry((float) results[1], description[1]));

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        dataSet.setColors(Color.GREEN, Color.RED);

        dataSet.setValueTextSize(14f);
        dataSet.setSliceSpace(2);
//        dataSet.setValueFormatter(new PercentFormatter());


        PieData data = new PieData(dataSet);
        data.setValueTextSize(14f); //wielkość wartości
        data.setValueFormatter(new PercentFormatter());

        //Chart
        chart.setData(data);
        chart.animateY(1000);
        chart.getDescription().setEnabled(false);
        chart.setCenterText(generateCenterSpannableText());

        chart.setUsePercentValues(true); //wyświetlaj udział procentowy!
        chart.setEntryLabelTextSize(18f); //wielkość podpisu
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setEnabled(false);
    }


    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Wyniki Pytań!");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        return s;
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
