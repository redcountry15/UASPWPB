package com.example.uaspwpb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TampilData extends AppCompatActivity {

    private FloatingActionButton fab;
    ListView ListViewMemo;
    List<Memo> ListMemo;

    DatabaseReference DatabaseMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        fab = findViewById(R.id.fabADD);
        ListViewMemo =findViewById(R.id.ListViewTampilData);
        ListMemo = new ArrayList<>();



        DatabaseMemo = FirebaseDatabase.getInstance().getReference("Memo");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TampilData.this,TambahDatas.class));
            }
        });

        ListViewMemo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Memo memo = ListMemo.get(i);
                showUpdateDialog(memo.getId(),memo.getTitle(),memo.getSubTitle(),memo.getDate());
                return false;

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        DatabaseMemo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListMemo.clear();

                //data snapshot berguna untuk memasukan data ke arraylist dari Class Memo
                for (DataSnapshot SnapshotMemo :dataSnapshot.getChildren()){
                    Memo memo = SnapshotMemo.getValue(Memo.class);
                    ListMemo.add(memo);
                }
                    //memasukan modelmemo adapter agar tampilannya sesuai dengan yang sudah kita tentukan
                    ModelMemo adapter = new ModelMemo(TampilData.this,ListMemo);
                    ListViewMemo.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {




            }
        });
    }

    //menampilkan update dialog
    //memakai constructor agar ketika dipanggil bisa di masukkan apa saja yang akan di intent
    private void showUpdateDialog(final String MemoId, final String MemoTitle, final  String MemoSubtitle, final String MemoDate){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogview = inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogview);

        final Button buttonUpdate =dialogview.findViewById(R.id.btnUpdate);
        final Button buttonDelete =dialogview.findViewById(R.id.btnDelete);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context mcontext; // tidak terpakai
                Memo memo = new Memo(); // tidak terpakai
                Intent intent = new Intent(TampilData.this,TambahDatas.class);
                intent.putExtra("TXT_TANGGAL",MemoDate);
                intent.putExtra("TEXT_TITLE",MemoTitle);
                intent.putExtra("TEXT_SUBTITLE",MemoSubtitle);
                intent.putExtra("TEXT_ID",MemoId);
                intent.putExtra("ACTION","EDIT");

                startActivity(intent);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteMemo(MemoId);
            }
        });

    }

    //delete memo berdasarkan id pada tabel memo pada firebase
    private  void deleteMemo(String id){
        DatabaseReference DatabaseMemo =FirebaseDatabase.getInstance().getReference("Memo").child(id);

        DatabaseMemo.removeValue();
        Toast.makeText(this,"Item Is Deleted",Toast.LENGTH_LONG).show();

    }
}
