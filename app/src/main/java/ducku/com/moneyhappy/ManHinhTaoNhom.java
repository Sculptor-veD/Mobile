package ducku.com.moneyhappy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;


public class ManHinhTaoNhom extends AppCompatActivity {

    int type;
    RadioButton radthu,radchi;
    ImageView imgVi, imgNhomCha,imghinhh;
    EditText editVi,editNhomCha;
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
                Intent intent= new Intent(ManHinhTaoNhom.this, ManHinhChonViDeTaoNhom.class);

                startActivity(intent);

            }
        });

        editVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ManHinhTaoNhom.this, ManHinhChonViDeTaoNhom.class);
                startActivity(intent);

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
                startActivity(intent);
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
                startActivity(intent);
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
        radthu=findViewById(R.id.radthu);
        radchi=findViewById(R.id.radchi);

        imghinhh=findViewById(R.id.imghinhh);
        imgVi=findViewById(R.id.imgvi);
        editVi=findViewById(R.id.editvi);
        imgNhomCha=findViewById(R.id.imgNhomCha);
        editNhomCha=findViewById(R.id.editNhomCha);

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
                imghinhh.setImageResource(hinh);
            }
            else
            {
                Toast.makeText(ManHinhTaoNhom.this,"Lấy hình thất bại",Toast.LENGTH_LONG).show();
            }
        }
    }
}