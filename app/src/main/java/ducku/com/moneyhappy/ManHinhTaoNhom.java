package ducku.com.moneyhappy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ducku.com.moneyhappy.model.Preferences;


public class ManHinhTaoNhom extends AppCompatActivity {

    int type, parentId = 0, image_id=2131230881;
    String userId;
    RadioButton radthu,radchi;
    ImageView imgVi, imgNhomCha,imghinhh;
    EditText editVi,editNhomCha, editNameCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_tao_nhom);

        Toolbar tb4 = findViewById(R.id.tb4);
        setSupportActionBar(tb4);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Nhóm mới");

        addControls();
        addEvents();
    }

    private void addEvents() {

        imgVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent= new Intent(ManHinhTaoNhom.this, ManHinhChonViDeTaoNhom.class);

                //startActivity(intent);

            }
        });

        editVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent= new Intent(ManHinhTaoNhom.this, ManHinhChonViDeTaoNhom.class);
                // startActivity(intent);

            }
        });

        imgNhomCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ManHinhTaoNhom.this,ManHinhLoadNhomCha.class);
                if(radchi.isChecked()==true)
                {
                    type=0;
                    intent.putExtra("type",type);
                }
                else if(radthu.isChecked()==true)
                {
                    type=1;
                    intent.putExtra("type",type);
                }
                startActivityForResult(intent, 2);
            }
        });

        editNhomCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ManHinhTaoNhom.this,ManHinhLoadNhomCha.class);
                if(radchi.isChecked()==true)
                {
                    type=0;
                    intent.putExtra("type",type);
                }
                else if(radthu.isChecked()==true)
                {
                    type=1;
                    intent.putExtra("type",type);
                }
                startActivityForResult(intent, 2);
            }
        });

        imghinhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhTaoNhom.this, ManHinhLoadIcon.class);
                startActivityForResult(intent,1);
            }
        });

    }

    private void addControls() {
        userId = Preferences.getUser(this);

        radthu=findViewById(R.id.radthu);
        radchi=findViewById(R.id.radchi);

        imghinhh=findViewById(R.id.imghinhh);
        imgVi=findViewById(R.id.imgvi);
        editVi=findViewById(R.id.editvi);
        imgNhomCha=findViewById(R.id.imgNhomCha);
        editNhomCha=findViewById(R.id.editNhomCha);
        editNameCategory = findViewById(R.id.editText);

        Intent intent=getIntent();
        String name_wl=intent.getStringExtra("Name");
        editVi.setText(name_wl);

        int id_ct= intent.getIntExtra("id_category",-1);
        int img=intent.getIntExtra("Img",-1);
        String name_ctCha=intent.getStringExtra("NameCT");
        if(id_ct!=-1) {
            editNhomCha.setText(name_ctCha);
            imgNhomCha.setImageResource(img);
        }

    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menutaonhom,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menuUpdateCategoy:
                CreateCategory(editNameCategory.getText().toString() , parentId, image_id);

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                int hinh=data.getIntExtra("id_hinh",-1);
                image_id = data.getIntExtra("id_hinh",2131230881);
                imghinhh.setImageResource(hinh);
            }
            else
            {
                Toast.makeText(ManHinhTaoNhom.this,"Lấy hình thất bại",Toast.LENGTH_LONG).show();
            }
        }

        if(requestCode == 2) {
            if(resultCode==RESULT_OK) {
                String name_wl=data.getStringExtra("Name");
                editVi.setText(name_wl);

                parentId = data.getIntExtra("id_category",-1);
                int img=data.getIntExtra("Img",-1);
                String name_ctCha=data.getStringExtra("NameCT");
                if(parentId!=-1) {
                    editNhomCha.setText(name_ctCha);
                    imgNhomCha.setImageResource(img);
                }
            }
        }
    }

    private void CreateCategory(String name, int parentID, int image) {
        String imageName = getResources().getResourceEntryName(image);
        int type_id = 0;
        if(imageName.isEmpty()) {
            Toast.makeText(this, "Lỗi chọn hình", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(radchi.isChecked()) {
            type_id = 0;
        }
        else {
            type_id = 1;
        }


        if(name.isEmpty()) {
            Toast.makeText(this, "Nhập tên..", Toast.LENGTH_SHORT).show();
        }
        else if(parentID == -1) {
            Toast.makeText(this, "Lỗi, Chọn lại danh mục cha..", Toast.LENGTH_SHORT).show();
        }
        else{
            String url = "act=save_category&name="+name+"&type="+type_id+"&parent_id="+parentID+"&image_name="+imageName+"&iduser="+userId;
            url = url.replace(" ", "%20");
            new SaveCategory().execute(url);
        }
    }

    private class SaveCategory extends api {
        protected void onPostExecute(String s) {

            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                String result = obj.getString("result");
                if(result.equals("true")){
                    Toast.makeText(ManHinhTaoNhom.this, "Save Success!", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ManHinhTaoNhom.this, "Error!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}