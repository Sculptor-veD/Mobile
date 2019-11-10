package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ManHinhDoiMatKhau extends AppCompatActivity {

    Button btnXacNhan;
    EditText EditTextPW1, EditTextPW2;
    String PhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_doi_mat_khau);

        getPhonenum();
        addControls();
        addEvent();

    }

    private void getPhonenum(){
        Intent intent = this.getIntent();
        PhoneNum = intent.getStringExtra("phonenum");
    }

    private void addControls(){
        btnXacNhan = findViewById(R.id.btnxacnhan);
        EditTextPW1 = findViewById(R.id.editmk);
        EditTextPW2 = findViewById(R.id.editmknhaplai);
    }

    private void  addEvent(){
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditTextPW1.getText().toString().equals(EditTextPW2.getText().toString())){
                    String QueryString = "act=editpw&phone=" + PhoneNum + "&pw=" + EditTextPW1.getText().toString();
                    //Toast.makeText(ManHinhDoiMatKhau.this, "query string:\n\n" + QueryString + "\n\n", Toast.LENGTH_SHORT).show();
                    new goiDoiMK().execute(QueryString);
                }else{
                    Toast.makeText(ManHinhDoiMatKhau.this, "Mật khẩu không trùng nhau !:\n\n", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private class goiDoiMK extends api {
        @Override
        protected void onPostExecute(String s) {
            JSONObject obj = null;

            try {
                String kqtv ="wait.";
                obj = new JSONObject(s);
                String result = obj.getString("result");
                if (result.equals("update")) {
                    kqtv="Đã cập nhật mật khẩu";;
                    Intent intent1 = new Intent(ManHinhDoiMatKhau.this, ManHinhDangNhap.class);
                    startActivity(intent1);

                } else if (result.equals("insert")) {
                    kqtv="Đã đăng ký thành công";
                    Intent intent1 = new Intent(ManHinhDoiMatKhau.this, ManHinhDangNhap.class);
                    startActivity(intent1);

                }else if (result.equals("false")){
                    kqtv="Có lỗi xãy ra";
                }
                //Toast.makeText(ManHinhDoiMatKhau.this, "query string:\n\n" + kqtv + "\n\n", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
