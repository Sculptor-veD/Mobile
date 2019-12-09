package ducku.com.moneyhappy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.PeriodicSync;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ducku.com.moneyhappy.adapter.CategoryAdapter;
import ducku.com.moneyhappy.model.Category;
import ducku.com.moneyhappy.model.Preferences;

public class ManHinhNhom extends AppCompatActivity {
    String name_wl;
    String name_wl1;
    int IdWallet;
    String userID;
    ImageView imgadd;
    TextView txtNameWl;
    ImageButton imgchonvi;
    ListView lvCategoryThuChi;
    ArrayList<Category> arrayCategory;
    CategoryAdapter adapter;
    Resources res;

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_nhom);

        addControls();
        addEvents();
        Toolbar tb2 = findViewById(R.id.toolbar2);
        setSupportActionBar(tb2);
        setTitle("Nhóm");

        new GetCategoryChi().execute("act=getcategory&iduser=" + userID + "&type=0");
        new GetCategoryThu().execute("act=getcategory&iduser=" + userID + "&type=1");

    }

    private void addControls() {
        userID = Preferences.getUser(this);

        Intent intent=getIntent();
        name_wl= intent.getStringExtra("NameWallet");
        IdWallet=intent.getIntExtra("IDWallet",-1);
        name_wl1=intent.getStringExtra("nameWL");
        txtNameWl= findViewById(R.id.txtNameWl);
        if(name_wl1!=null)
        {
            name_wl=name_wl1;
            txtNameWl.setText(name_wl);
        }
        else {
            txtNameWl.setText(name_wl);
        }
        imgadd= findViewById(R.id.imgAdd);


        imgchonvi=findViewById(R.id.imgchonvi);
        lvCategoryThuChi =  findViewById(R.id.lvKhoanChi);


        arrayCategory = new ArrayList<>();

        res = getResources();
    }

    private void addEvents()
    {
        imgchonvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ManHinhNhom.this,ManHinhVi.class);
                startActivity(intent);
            }
        });

        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhNhom.this, ManHinhTaoNhom.class);
                intent.putExtra("nameWL",name_wl);
                intent.putExtra("idWL",IdWallet);
                startActivityForResult(intent,44);
            }
        });

        lvCategoryThuChi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(txtNameWl.getText().toString()=="")
                {
                    Toast.makeText(ManHinhNhom.this,"Bạn chưa chọn ví!, hãy nhấp vào" +
                            " nút bên phải phía trên để chọn ví ^^",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(ManHinhNhom.this, ManHinhHienThiChiTietNhom.class);
                    intent.putExtra("id_ct", arrayCategory.get(position).get_id());
                    intent.putExtra("name_ct", arrayCategory.get(position).get_name());
                    intent.putExtra("img_ct", arrayCategory.get(position).get_img());
                    intent.putExtra("id_parent", arrayCategory.get(position).get_parentId());
                    intent.putExtra("type", arrayCategory.get(position).get_type());

                    intent.putExtra("name_wl", txtNameWl.getText().toString());



                    startActivity(intent);
                }
            }
        });
    }

    private class GetCategoryChi extends api {
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
                }
                adapter = new CategoryAdapter(ManHinhNhom.this, 0, arrayCategory);
                lvCategoryThuChi.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetCategoryThu extends api {
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

                }
                adapter = new CategoryAdapter(ManHinhNhom.this, 0, arrayCategory);
                lvCategoryThuChi.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuhd:
                Intent intent3= new Intent(ManHinhNhom.this,HomeActivity.class);
                intent3.putExtra("nameWL",name_wl);
                intent3.putExtra("idWL",IdWallet);
                startActivityForResult(intent3,1);
                break;
            case R.id.menuvi:
                Intent intent= new Intent(ManHinhNhom.this,ManHinhVi.class);
                startActivity(intent);
                break;
            case R.id.menunhom:
                Intent intent2= new Intent(ManHinhNhom.this,ManHinhNhom.class);
                startActivity(intent2);
                break;
            case R.id.menudangxuat:
                Intent intent1= new Intent(ManHinhNhom.this,ManHinhDangNhap.class);
                startActivity(intent1);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            txtNameWl.setText(name_wl);
        }
    }
}
