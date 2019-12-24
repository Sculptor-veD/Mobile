package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ducku.com.moneyhappy.adapter.ItemReportAdapter;
import ducku.com.moneyhappy.model.ItemReport;
import ducku.com.moneyhappy.model.Preferences;

public class ViewReport extends AppCompatActivity {

    PieChart mChartChi, mChartThu;
    RecyclerView recyclerViewThu, recyclerViewChi;
    TextView textViewTienVao, textViewTienRa, textViewThuNhap;
    private ItemReportAdapter adapterThu;
    private ItemReportAdapter adapterChi;
    public List<ItemReport> itemReportThuList =  new ArrayList<>();
    public List<ItemReport> itemReportChiList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Toolbar tb8 = findViewById(R.id.tb8);
        setSupportActionBar(tb8);
        setTitle("View Report");

        AnhXa();
        loadData();
        List<ItemReport> itemReport1 = itemReportChiList;
        List<ItemReport> itemReport2 = itemReportThuList;
//        addDataSet(mChartChi, itemReportChiList);
//        addDataSet(mChartThu, itemReportThuList);
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

        RecyclerView.LayoutManager layoutManagerThu = new LinearLayoutManager(this);
        recyclerViewThu = (RecyclerView) findViewById(R.id.rvKhoanThu);
        recyclerViewThu.setLayoutManager(layoutManagerThu);

        RecyclerView.LayoutManager layoutManagerChi = new LinearLayoutManager(this);
        recyclerViewChi = (RecyclerView) findViewById(R.id.rvKhoanChi);
        recyclerViewChi.setLayoutManager(layoutManagerChi);

        textViewTienVao = findViewById(R.id.value_tienvao);
        textViewTienRa = findViewById(R.id.value_tienra);
        textViewThuNhap = findViewById(R.id.value_thunhaprong);
    }

    private void addDataSet(PieChart pieChart, List<ItemReport> listItem) {
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<Integer> colors=new ArrayList<>();
        int i = 3;
        for (ItemReport item : listItem) {
            yEntrys.add(new PieEntry(item.get_amount(),item.get_name()));
            colors.add(ColorTemplate.VORDIPLOM_COLORS[i]);
            i++;
        }

        PieDataSet pieDataSet=new PieDataSet(yEntrys,"");
        pieDataSet.setColors(colors);

        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void loadData() {
        Intent intent = getIntent();
        String startdate = intent.getStringExtra("startdate");
        String enddate = intent.getStringExtra("enddate");
        String account_id = Preferences.getUser(getBaseContext());
        String token = Preferences.getToken(getBaseContext());

        String url= "act=getreport&account_id="+account_id+"&startdate="+startdate+"&enddate="+enddate+"&token="+token;
        Log.e("URL: ", url);

        new GetListItemReportChi().execute(url+"&type=0");
        new GetListItemReportThu().execute(url+"&type=1");

        String thunhaprong = intent.getStringExtra("thunhaprong");
        textViewThuNhap.setText(thunhaprong);
    }

    public class GetListItemReportThu extends api {

        @Override
        public void onPostExecute(String s) {
            JSONArray array = null;
            try {
                array = new JSONArray(s);
                int tienvao = 0;
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object=array.getJSONObject(i);
                    int id=object.getInt("id");
                    int parent_id=object.getInt("parent_id");
                    String name=object.getString("name");
                    int image_name=getResources().getIdentifier(object.getString("image_name"),"drawable", getBaseContext().getPackageName());
                    int amount=object.getInt("amount");
                    ItemReport item = new ItemReport(id, parent_id, image_name, amount, name);
                    itemReportThuList.add(item);
                    tienvao += amount;
                }
                addDataSet(mChartThu, itemReportThuList);
                adapterThu = new ItemReportAdapter(getBaseContext(), itemReportThuList);
                recyclerViewThu.setAdapter(adapterThu);

                textViewTienVao.setText(tienvao+"đ");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public class GetListItemReportChi extends api {

        @Override
        public void onPostExecute(String s) {
            JSONArray array = null;
            try {
                array = new JSONArray(s);
                int tienra = 0;
                for(int i=0;i<array.length();i++)
                {
                    JSONObject object=array.getJSONObject(i);
                    int id=object.getInt("id");
                    int parent_id=object.getInt("parent_id");
                    String name=object.getString("name");
                    int image_name=getResources().getIdentifier(object.getString("image_name"),"drawable", getBaseContext().getPackageName());
                    int amount=object.getInt("amount");
                    ItemReport item = new ItemReport(id, parent_id, image_name, amount, name);
                    itemReportChiList.add(item);
                    tienra -= amount;
                }
                addDataSet(mChartChi, itemReportChiList);
                adapterChi = new ItemReportAdapter(getBaseContext(), itemReportChiList);
                recyclerViewChi.setAdapter(adapterChi);
                adapterChi.notifyDataSetChanged();

                textViewTienRa.setText(tienra+"đ");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
