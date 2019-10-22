package ducku.com.moneyhappy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ManHinhDangNhap extends AppCompatActivity {

    Button btnDangKy;
    EditText EditTextSDT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        addControls();
//        addEvent();
    }

//    private void addEvent() {
//    }

    private void addControls() {
        btnDangKy   = findViewById(R.id.btnDangNhap);
        EditTextSDT = findViewById(R.id.editTextSDT);
    }
}
