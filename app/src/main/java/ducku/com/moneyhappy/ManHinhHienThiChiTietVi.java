package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhHienThiChiTietVi extends AppCompatActivity {
    String  userID;
    ImageView imgluunha;
    EditText editnamenha;
    TextView txtsotien;
    LinearLayout line4;
    int id_wl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_hien_thi_chi_tiet_vi);

        Toolbar tb11 = findViewById(R.id.tb11);
        setSupportActionBar(tb11);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chi tiết ví");

        addControls();
        addEvents();
    }

    private void addEvents() {
        imgluunha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String nameWL      = editnamenha.getText().toString();
            if(nameWL.isEmpty()){
                Toast.makeText(ManHinhHienThiChiTietVi.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
            }
            else {
                String url = "act=update_wallet&name="+nameWL+"&id="+id_wl;
                url = url.replace(" ", "%20");
                new api().execute(url);
                Toast.makeText(ManHinhHienThiChiTietVi.this, "Save Successfully!", Toast.LENGTH_SHORT).show();
                imgluunha.setVisibility(View.INVISIBLE);
                editnamenha.setEnabled(false);
            }
            }
        });
    }

    private void addControls() {
        userID = Preferences.getUser(this);

        line4=findViewById(R.id.line4);
        imgluunha=findViewById( R.id.imgluunha);
        editnamenha=findViewById(R.id.editnamenha);
        txtsotien=findViewById(R.id.txtSoTien);

        Intent intent=getIntent();

        String name_ct=intent.getStringExtra("name_wl");
        int amount=intent.getIntExtra("amount_wl",-1);
        id_wl = intent.getIntExtra("id_wl", -1);

        editnamenha.setText(name_ct);
        txtsotien.setText(amount+" đ");

        imgluunha.setVisibility(View.INVISIBLE);

    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menuchitietnhom,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuedit:
                imgluunha.setVisibility(View.VISIBLE);
                editnamenha.setEnabled(true);
                Toast.makeText(ManHinhHienThiChiTietVi.this,"Bạn đang ở trong trạng thái sửa ví",Toast.LENGTH_LONG).show();
                break;
            case R.id.menudelete:
                new DeleteWallet().execute("act=delete_wallet&id="+id_wl);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DeleteWallet extends api {
        protected void onPostExecute(String s) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                String result = obj.getString("result");
                if(result.equals("true")){
                    Toast.makeText(ManHinhHienThiChiTietVi.this, "Deleted!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ManHinhHienThiChiTietVi.this, "Ví còn liên kết nhiều giao dịch, Ko thể xóa ví này!", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void DeleteWallet(String id) {
        new api().execute("act=delete_wallet&id="+id_wl);

    }
}
