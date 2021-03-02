package com.example.womensskill;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class StorePerformanceActivity extends BaseClass {
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_store_performance);

        barChart = (BarChart) findViewById(R.id.bargraph);

        YAxis leftAxis = barChart.getAxisLeft();
        YAxis rightAxis = barChart.getAxisRight();
        XAxis xAxis = barChart.getXAxis();

        xAxis.setPosition( XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setDrawAxisLine(true);
        xAxis.setLabelCount(7);
        xAxis.setDrawGridLines(false);


        leftAxis.setTextSize(12f);
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setDrawGridLines(true);

        rightAxis.setDrawAxisLine(true);
        rightAxis.setDrawGridLines(true);
        rightAxis.setDrawLabels(true);
        ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();
        dataVals.add( new BarEntry(0, Integer.valueOf(15) ));
        dataVals.add( new BarEntry( 3, 20 ) );
        dataVals.add( new BarEntry( 9, 30 ) );
        dataVals.add( new BarEntry( 12, 50 ) );
        dataVals.add( new BarEntry( 15, 60 ) );
        dataVals.add( new BarEntry( 18, 70 ) );
        dataVals.add( new BarEntry( 21, 75 ) );
        dataVals.add( new BarEntry(24, 80 ));
        dataVals.add( new BarEntry( 27, 85 ) );
        dataVals.add( new BarEntry( 30, 100 ) );

        BarDataSet barDataSet1 = new BarDataSet(dataVals,"Monthly Revenue ");
        BarData barData = new BarData();
        barData.addDataSet(barDataSet1);
        barData.setBarWidth(0.3f); // set custom bar width
        barChart.setData(barData);
        barChart.setData(barData);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate();
        barChart.setScaleEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setBackgroundColor(Color.rgb(255, 255, 255));
        barChart.animateXY(2000, 2000);
        barChart.setDrawBorders(false);
        //   barChart.setDescription(desc);
        barChart.setDrawValueAboveBar(true);
        barData.notifyDataChanged(); // let the data know a dataSet changed
        barDataSet1.notifyDataSetChanged();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_store_performance;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
}