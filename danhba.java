package com.example.dethibuoisang;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

public class DanhSachDanhBa extends AppCompatActivity {
    private ListView lvRener;
    private ArrayList<String> arrList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danh_sach_danh_ba);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        this.lvRener = findViewById(R.id.list_view_render);

        setSupportActionBar(toolbar);
        EdgeToEdge.enable(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chuong_trinh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemSelected = item.getItemId();
        if (itemSelected == R.id.ds) {
            handleRenderListView();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachDanhBa.this);
            builder.setTitle("Bạn chắc chắn muốn thoát?");
            builder.setMessage("Nếu thoát úng dụng của bạn sẽ bi đóng lại");
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            });
            builder.create();
            builder.show();
            return true;
        }
        return true;
    }

    private void handleRenderListView() {
        String jsonStringArr = handleReadFileJson(R.raw.danhba);
        try {

            JSONArray jsonArray = new JSONArray(jsonStringArr);
            for(int i = 0; i < jsonArray.length(); i++){
                String name = jsonArray.getJSONObject(i).getString("name");
                String sdt = jsonArray.getJSONObject(i).getString("sdt");
                String customer = name + " - " + sdt;
                arrList.add(customer);
            }

            ArrayAdapter adapter = new ArrayAdapter(DanhSachDanhBa.this, android.R.layout.simple_list_item_1, arrList);
            this.lvRener.setAdapter(adapter);

        }catch (Exception e) {
            System.out.println("Err: " + e.toString());
        }
    }

    private String handleReadFileJson(int fileName) {
        String jsonString = null;
        try {
            InputStream inputStream = DanhSachDanhBa.this.getResources().openRawResource(fileName);
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();
            jsonString = new String(bytes, "UTF-8");
        } catch (Exception e) {
            System.out.println("Err: " + e.toString());
        }
        return jsonString;
    }
}