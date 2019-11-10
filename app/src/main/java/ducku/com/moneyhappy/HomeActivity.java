package ducku.com.moneyhappy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import ducku.com.moneyhappy.adapter.PageTransactionAdapter;
import ducku.com.moneyhappy.adapter.TransactionAdapter;
import ducku.com.moneyhappy.model.Transaction;

public class HomeActivity extends AppCompatActivity {

    private List<Transaction> transactionList = new ArrayList<>();
    private TransactionAdapter adapter;
    private RecyclerView rvDeal;

    FloatingActionButton fabAddTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tb8 = findViewById(R.id.tb8);
        setSupportActionBar(tb8);
        setTitle("Giao dịch Demo");

        bindView();

        addControls();

        addEvents();
    }

    private void bindView() {

        ViewPager pager = findViewById(R.id.vpTransaction);
        PageTransactionAdapter pageTransactionAdapter = new PageTransactionAdapter(getSupportFragmentManager());
//        pageTransactionAdapter.add(TransactionFragment.newInstance(10, 2019), "Thang 10");
//        pageTransactionAdapter.add(TransactionFragment.newInstance(11, 2019), "Thang 11");
        pager.setAdapter(pageTransactionAdapter);

        TabLayout tabMonTransaction = findViewById(R.id.tabMonthTransaction);
        tabMonTransaction.setupWithViewPager(pager);

        pager.setCurrentItem(pageTransactionAdapter.getCount() - 1);
    }

    private void addEvents() {
        fabAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ManHinhGiaoDich.class);
                startActivity(intent);
                Toast.makeText(HomeActivity.this, "Open Add Transaction", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControls() {
        fabAddTransaction = findViewById(R.id.fabAddTransaction);
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
                //code xử lý khi bấm menu2
                break;
            case R.id.menuvi:
                Intent intent= new Intent(HomeActivity.this,ManHinhVi.class);
                startActivity(intent);
                break;
            case R.id.menunhom:
                Intent intent2= new Intent(HomeActivity.this,ManHinhNhom.class);
                startActivity(intent2);
                break;
            case R.id.menudangxuat:
                Intent intent1= new Intent(HomeActivity.this,ManHinhDangNhap.class);
                startActivity(intent1);
                break;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}
