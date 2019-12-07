package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class ViewReport extends AppCompatActivity {

    PieChart mChartChi, mChartThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Toolbar tb8 = findViewById(R.id.tb8);
        setSupportActionBar(tb8);
        setTitle("View Report");

        AnhXa();
        addDataSet(mChartChi);
        addDataSet(mChartThu);
    }

    private void AnhXa() {
        // PieChart Khoan Chi
        mChartChi = (PieChart) findViewById(R.id.piechartKhoanChi);
        mChartChi.setRotationEnabled(true);
        mChartChi.setDescription(new Description());
        mChartChi.setHoleRadius(35f);
        mChartChi.setTransparentCircleAlpha(0);
        mChartChi.setCenterText("CHI");
        mChartChi.setCenterTextSize(10);
        mChartChi.setDrawEntryLabels(true);

        // PieChart Khoan Thu
        mChartThu = (PieChart) findViewById(R.id.piechartKhoanThu);
        mChartThu.setRotationEnabled(true);
        mChartThu.setDescription(new Description());
        mChartThu.setHoleRadius(35f);
        mChartThu.setTransparentCircleAlpha(0);
        mChartThu.setCenterText("THU");
        mChartThu.setCenterTextSize(10);
        mChartThu.setDrawEntryLabels(true);
    }

    private void addDataSet(PieChart pieChart) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        float[] yData = { 25, 40, 70 };
        String[] xData = { "January", "February", "January" };

        for (int i = 0; i < yData.length;i++){
            yEntrys.add(new PieEntry(yData[i],i));
        }
        for (int i = 0; i < xData.length;i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"");
//        pieDataSet.setSliceSpace(2);
//        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);

        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        //legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
