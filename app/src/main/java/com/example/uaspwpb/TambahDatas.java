package com.example.uaspwpb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class TambahDatas extends AppCompatActivity {
    DatabaseReference DatabaseMemo;
    ListView listViewMemo;
    List<Memo> listMemo;
    EditText editTitle,editSubTitle,editTanggal;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_datas);

        editTitle = findViewById(R.id.editTitle);
        editSubTitle = findViewById(R.id.editSubtitle);

        DatabaseMemo = FirebaseDatabase.getInstance().getReference("Memo");
        btnAdd = findViewById(R.id.btnUbah);

        Intent intent =  getIntent();
        final String id = intent.getStringExtra("TEXT_ID");
        final String title = intent.getStringExtra("TEXT_TITLE");
        final String tanggal = intent.getStringExtra("TEXT_DATE");
        final String subtitle = intent.getStringExtra("TEXT_SUBTITLE");
        String action = intent.getStringExtra("ACTION");

        if (action!= null){
            if (action.equals("EDIT")){
                editTitle.setText(title);
                editSubTitle.setText(subtitle);
                getSupportActionBar().setTitle("Ubah Data");
                btnAdd.setText("Ubah");
            }
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(btnAdd.getText().toString().equalsIgnoreCase("Ubah")){

                    String titles = editTitle.getText().toString();
                    String subtitles = editSubTitle.getText().toString();
                    updateMemo(id,tanggal,titles,subtitles);
                }else {
                    AddMemo();
                }
            }
        });



    }

    private boolean updateMemo(String id,  String date, String title , String subtitle){
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("Memo").child(id);

        Memo memo = new Memo(title,date,subtitle,id);
        databaseReference.setValue(memo);

        Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
        return  true;
    }

    public void AddMemo(){
        String title = editTitle.getText().toString().trim();
        String subTitle = editSubTitle.getText().toString().trim();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        if (!TextUtils.isEmpty(title)){
            String id = DatabaseMemo.push().getKey();
            Memo memo = new Memo(title,currentDate,subTitle,id);
            DatabaseMemo.child(id).setValue(memo);

            Toast.makeText(this,"Data Succesfully Added",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"You Should Enter Title First",Toast.LENGTH_SHORT).show();
        }
    }
}
