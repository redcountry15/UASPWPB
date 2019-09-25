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
       //ketika action yang didaptkan itu tidak sama dengan kosong / null
        if (action!= null){
            //jika action yang didapat sama dengan edit maka kite nge set edit text dari nilai yang suda kita dapatkan
            // melalui getStringExtra
            if (action.equals("EDIT")){
                editTitle.setText(title);
                editSubTitle.setText(subtitle);
                btnAdd.setText("Ubah");
            }
        }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ketika teks yang berada di BtnAdd sama dengan ubah maka akan menjalankan fungsi dibawah
                if(btnAdd.getText().toString().equalsIgnoreCase("Ubah")){
                    // nah disini itu berfungi untuk mengupdate yang ada di database
                    String titles = editTitle.getText().toString();
                    String subtitles = editSubTitle.getText().toString();
                    updateMemo(id,titles,subtitles);
                }else {
                    //ketika kondisi falase maka  fungsi addmemo akan dijalankan
                    AddMemo();
                }
            }
        });



    }

    //fungsi update memo
    private boolean updateMemo( String id,String title , String subtitle){
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("Memo").child(id);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        Memo memo = new Memo(title,currentDate,subtitle,id);
        //set value yang berfunsgi untuk mengupdate database dengan data yang sudah kita perbaharui
        databaseReference.setValue(memo);

        Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
        return  true;
    }


    //menambahkan memo
    public void AddMemo(){

        //mengambil nilai dari edit text
        String title = editTitle.getText().toString().trim();
        String subTitle = editSubTitle.getText().toString().trim();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        //dimasukan ke dalam database dengan menggunakan nilai yang sudah diambil dari editText
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
