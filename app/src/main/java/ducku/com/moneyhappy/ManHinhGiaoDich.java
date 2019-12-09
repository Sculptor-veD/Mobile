package ducku.com.moneyhappy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ducku.com.moneyhappy.model.Preferences;

public class ManHinhGiaoDich extends AppCompatActivity {

    String iduser;
    LinearLayout choiceCategory;
    EditText edtCategory;
    TextView txtIdCategory;
    EditText edtWallet;
    TextView txtIdWallet;
    ImageView imgWallet;
    ImageView imgCategory;
    ImageView imgcalendar;
    Calendar calendar=Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");

    EditText edtMoney, edtDescript, edtCalendar;

    int idCategory, idImgCategory, idWallet, idImgWallet, typeCategory,idwl;
    String nameCategory, nameWallet;

    // btn save
    LinearLayout convertBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_giao_dich);

        addControls();
        addEvents();
        Intent intent=getIntent();
        String namewl=intent.getStringExtra("nameWL");
        idwl=intent.getIntExtra("idWL",-1);
        Toast.makeText(ManHinhGiaoDich.this,idwl+"",Toast.LENGTH_LONG).show();
        edtWallet.setText(namewl);
    }

    private void addEvents() {
        edtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtWallet.getText().toString().isEmpty())
                {
                    Toast.makeText(ManHinhGiaoDich.this,"Hãy chọn ví trước",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(ManHinhGiaoDich.this, ManHinhThuChi.class);
                    intent.putExtra("idwallet",idWallet); //phuoc - them
                    startActivityForResult(intent,1);
                }
            }
        });

        edtWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ManHinhGiaoDich.this, LoadWalletActivity.class);
                startActivityForResult(intent,22);
            }
        });


        convertBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtCalendar.getText().toString().isEmpty() || edtMoney.getText().toString().isEmpty() || txtIdCategory.getText().toString().isEmpty() || edtWallet.getText().toString().isEmpty()) {
                    // Show notify here
                    Toast.makeText(ManHinhGiaoDich.this, "Chọn danh mục, Ví và Ko để trống trường ...", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Handle logic Save here

                    String descript = edtDescript.getText().toString();
                    String created = edtCalendar.getText().toString();
                    String monney = edtMoney.getText().toString();
                    if(idwl>0) {
                        idWallet=idwl;
                        String url = "act=save_transaction&wallet_id=" + idWallet + "&category_id=" + idCategory + "&descript=" + descript + "&created=" + created + "&amount=" + monney + "&type=" + typeCategory + "&iduser=" + iduser;
                        url = url.replace(" ", "%20");
                        Log.e("URL: ", url);
                        new SaveTransaction().execute(url);
                    }
                }
            }
        });

        //Xử lý hiển thị DatePicker
        imgcalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyNgay();
            }
        });
    }

    private void xuLyNgay() {
        final Calendar calendar=Calendar.getInstance();
        int ngay=calendar.get(Calendar.DATE);
        int thang=calendar.get(Calendar.MONTH);
        int nam=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog= new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy/MM/dd");
                edtCalendar.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },nam,thang,ngay);
        datePickerDialog.show();
    }


    private void addControls() {
        iduser = Preferences.getUser(ManHinhGiaoDich.this);

        choiceCategory = findViewById(R.id.choiceCategory);
        edtCategory = findViewById(R.id.edtCategory);
        edtWallet = findViewById(R.id.edtWallet);
        txtIdCategory = findViewById(R.id.txtIdCategory);
        txtIdWallet = findViewById(R.id.txtIdWallet);
        imgCategory = findViewById(R.id.imgQuestion);
        imgWallet = findViewById(R.id.imgWallet);



        edtCalendar = findViewById(R.id.edtCalendar);
        edtDescript = findViewById(R.id.edtDescript);
        edtMoney = findViewById(R.id.edtMoney);
        convertBtnSave = findViewById(R.id.convertBtnSave);

        //Xử lý ngày
        imgcalendar=findViewById(R.id.imgCalendar);
        calendar=Calendar.getInstance();
        edtCalendar.setText(sdf.format(calendar.getTime()));

    }


    private class SaveTransaction extends api {
        protected void onPostExecute(String s) {

            JSONObject obj = null;
            try {
                obj = new JSONObject(s);
                String result = obj.getString("result");
                if(result.equals("true")){
                    Toast.makeText(ManHinhGiaoDich.this, "Save Success!", Toast.LENGTH_LONG).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ManHinhGiaoDich.this, "Error!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                nameCategory= data.getStringExtra("name_category");
                typeCategory = data.getIntExtra("type_category", -1);
                idCategory= data.getIntExtra("id_category",-1);
                idImgCategory=data.getIntExtra("image_category",-1);
                if(idCategory!=-1)
                {
                    imgCategory.setImageResource(idImgCategory);
                    edtCategory.setText(nameCategory);
                    txtIdCategory.setText(idCategory+"");
                }
            }
        }

        if(requestCode==22)
        {
            if(resultCode==RESULT_OK)
            {
                nameWallet= data.getStringExtra("name_wallet");
                idWallet= data.getIntExtra("id_wallet",-1);
                idImgWallet=data.getIntExtra("image_wallet",-1);
                if(idWallet!=-1)
                {
                    //imgWallet.setImageResource(idImgWallet);
                    edtWallet.setText(nameWallet);
                    txtIdWallet.setText(idWallet+"");
                }
            }
        }
    }
}
