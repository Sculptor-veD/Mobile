package ducku.com.moneyhappy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class ManHinhDangKy extends AppCompatActivity {

    Button btnDangKy;
    EditText EditTextSDT;
    TextView twThongBao, twTieuDeKhung, twLine1, twLine2;
    String Status = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        addControls();
        addEvent();
        forgotpassword();


    }

    private void addEvent() {
        Intent intent = this.getIntent();
        String action = intent.getStringExtra("action");

        if (action.equals("ForGotPassword")) {

            btnDangKy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String QueryString = "act=register&" + Status + "=" + EditTextSDT.getText().toString();
                    Toast.makeText(ManHinhDangKy.this, "query string:\n\n" + QueryString + "\n\n", Toast.LENGTH_SHORT).show();
                    new goiDangKy().execute(QueryString);
                    twLine1.setText("Mã OTP có giá trị trong 60s");
                    twLine2.setText("Nhập mã OTP tại đây");
                    btnDangKy.setText("Xác nhận");

                }
            });
        } else {
            btnDangKy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String QueryString = "act=register&" + Status + "=" + EditTextSDT.getText().toString();
                    Toast.makeText(ManHinhDangKy.this, "query string:\n\n" + QueryString + "\n\n", Toast.LENGTH_SHORT).show();
                    new goiDangKy().execute(QueryString);
                    twLine1.setText("Mã OTP có giá trị trong 60s");
                    twLine2.setText("Nhập mã OTP tại đây");



                }
            });

        }

    }

    private void addControls() {
        btnDangKy = findViewById(R.id.btnDangKy);
        EditTextSDT = findViewById(R.id.editTextSDT);
        twThongBao = findViewById(R.id.twThongBao);
        twTieuDeKhung = findViewById(R.id.twSDT);
        twLine1 = findViewById(R.id.textView2);
        twLine2 = findViewById(R.id.textView3);

    }

    private class goiDangKy extends api {
        @Override
        protected void onPostExecute(String s) {
            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                String result = obj.getString("result");

                // Xu ly dang ky sdt
                if (Status.equals("phone")) {
                    if (result.equals("OTP")) {
                        //bla bla
                        twThongBao.setText("Đã gửi mã OTP đến " + EditTextSDT.getText().toString());
                        twTieuDeKhung.setText("Nhập mã OTP:");
                        btnDangKy.setText("Xác Nhận");
                        Status = "phone=" + EditTextSDT.getText().toString() + "&otp";
                        EditTextSDT.setHint("Hãy nhập mã OTP");
                        EditTextSDT.setText("");

                    } else if (result.equals("ALREADY_EXIST")) {
                        twThongBao.setText("Số điện thoại đã tồn tại");

                    } else {
                        twThongBao.setText("Không có kết nối");

                    }
                }
                //Xu ly kich haot tai khoan
                else {
                    if (result.equals("true")) {
                        twThongBao.setText("Kich hoat thanh cong -> di den tao mk");
                        Intent intent = new Intent(ManHinhDangKy.this, ManHinhDoiMatKhau.class);
                        intent.putExtra("action","ForGotPassword");
                        startActivity(intent);
                    } else {
                        twThongBao.setText("Mã OTP không hợp lệ");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(ManHinhDangKy.this, "WEBSERVER-re:\n\n" + s + "\n\n", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void forgotpassword() {
        Intent intent = this.getIntent();
        String action = intent.getStringExtra("action");
        if (action.equals("ForGotPassword")) {
            //QUY TRINH KHOI FUCK MAT KHAU
            twTieuDeKhung.setText("Hãy nhập số điện thoại đã dùng để đăng ký.");
            twLine1.setText("Chúng tôi sẽ gửi mã OTP ngày khi bạn nhập số điện thoại hợp lệ.");
            twLine2.setText("");
            btnDangKy.setText("Gửi mã OTP");
        }
    }

}