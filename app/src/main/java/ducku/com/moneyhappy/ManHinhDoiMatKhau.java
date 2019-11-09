package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class ManHinhDoiMatKhau extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_doi_mat_khau);


        Toolbar tb19 = findViewById(R.id.tb19);
        setSupportActionBar(tb19);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Đổi mật khẩu");
    }
}
