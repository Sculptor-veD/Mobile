package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ducku.com.moneyhappy.adapter.CategoryAdapter;
import ducku.com.moneyhappy.model.Category;
import ducku.com.moneyhappy.model.Preferences;

public class LoadCategoryActivity extends AppCompatActivity {

    ListView lvCategory;
    ArrayList<Category> arrayCategory;
    CategoryAdapter adapter;
    Resources res;
    String urlAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_category);

        addControls();
        addEvent();

        new GetCategory().execute(urlAPI);
    }

    private void addControls() {
        lvCategory = (ListView) findViewById(R.id.lv_category);
        arrayCategory = new ArrayList<>();
        res = getResources();

        String userID = Preferences.getUser(LoadCategoryActivity.this);
        urlAPI = "act=getcategory&iduser="+userID+"&type=1";
    }

    private void addEvent() {

        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("id_category", arrayCategory.get(position).get_id());
                intent.putExtra("name_category", arrayCategory.get(position).get_name());
                intent.putExtra("image_category", arrayCategory.get(position).get_img());
                setResult(Activity.RESULT_OK,intent);;
                finish();
            }
        });

    }

    private class GetCategory extends api {
        @Override
        protected void onPostExecute(String s) {

            try {
                JSONArray array = new JSONArray(s);

                for(int i=0;i<array.length();i++)
                {
                    JSONObject obcategory=array.getJSONObject(i);
                    int id=obcategory.getInt("cid");
                    int parent_id=obcategory.getInt("parent");
                    String name=obcategory.getString("cname");
                    int type=obcategory.getInt("type");
                    int idImg=res.getIdentifier(obcategory.getString("img"),"drawable",getPackageName());
                    arrayCategory.add(new Category(id,parent_id,idImg,type,name));

                    Log.d("Log",name);
                }
                adapter = new CategoryAdapter(LoadCategoryActivity.this, 0, arrayCategory);
                lvCategory.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
