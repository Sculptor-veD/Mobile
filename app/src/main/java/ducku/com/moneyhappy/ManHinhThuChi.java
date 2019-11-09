package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ducku.com.moneyhappy.adapter.CategoryAdapter;
import ducku.com.moneyhappy.model.Category;
import ducku.com.moneyhappy.model.Preferences;

public class ManHinhThuChi extends AppCompatActivity {

    ListView lvCategory;
    ArrayList<Category> arrayCategory;
    CategoryAdapter adapter;
    Resources res;
    TabHost tabHost;
    String idWallet;

    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_thu_chi);

        getidwallet();

        addControls();

        addEvents();


    }

    private void getidwallet(){
        Intent intent = this.getIntent();
        idWallet = intent.getStringExtra("xxx");
        Toast.makeText(ManHinhThuChi.this, "WEBSERVER-re:\n\n" + idWallet + "\n\n", Toast.LENGTH_SHORT).show();
    }


    private void addEvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("t1")) {
                    new GetCategory().execute("act=getcategory&walletid="+idWallet+"&type=1"); //phuoc - sua
                }
                else {
                    new GetCategory().execute("act=getcategory&walletid="+idWallet+"&type=0"); //phuoc - sua
                }
                Toast.makeText(ManHinhThuChi.this, "WEBSERVER-re:\n\n" + idWallet + "\n\n", Toast.LENGTH_SHORT).show();
            }
        });

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("id_category",arrayCategory.get(position).get_id());
                intent.putExtra("image_category",arrayCategory.get(position).get_img());
                intent.putExtra("name_category",arrayCategory.get(position).get_name());
                intent.putExtra("type_category", arrayCategory.get(position).get_type());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    private void addControls() {
        userID = Preferences.getUser(ManHinhThuChi.this);

        tabHost= findViewById(R.id.tabhost);

        tabHost.setup();

        final TabHost.TabSpec tab1=tabHost.newTabSpec("t1");
        tab1.setIndicator("KHOẢN THU");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2=tabHost.newTabSpec("t2");
        tab2.setIndicator("KHOẢN CHI");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        lvCategory=findViewById(R.id.lv_load_category);

        arrayCategory = new ArrayList<>();

        res = getResources();

        // Set defult load is tab thu and Listview Thu
        tabHost.setCurrentTab(0);
        new GetCategory().execute("act=getcategory&walletid="+idWallet+"&type=1"); //phuoc - sua
    }

    private class GetCategory extends api {
        @Override
        protected void onPostExecute(String s) {

            try {
                arrayCategory = new ArrayList<>();
                JSONArray array = new JSONArray(s);
                for(int i=0;i<array.length();i++)
                {
                    JSONObject obcategory=array.getJSONObject(i);
                    int id=obcategory.getInt("cid");
                    int parent_id=obcategory.getInt("parent");
                    String name=obcategory.getString("cname");
                    int type= obcategory.getInt("type");
                    int idImg=res.getIdentifier(obcategory.getString("img"),"drawable",getPackageName());
                    arrayCategory.add(new Category(id,parent_id,idImg,type,name));
                    Log.d("Log",name);
                }
                adapter = new CategoryAdapter(ManHinhThuChi.this, 0, arrayCategory);
                lvCategory.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
