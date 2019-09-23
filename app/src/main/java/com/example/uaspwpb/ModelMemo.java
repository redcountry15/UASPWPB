package com.example.uaspwpb;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ModelMemo extends ArrayAdapter<Memo> {

    Activity context;
    List<Memo> memos;

    public ModelMemo(@NonNull Activity context, List<Memo> memo) {
        super(context,R.layout.model_view, memo);

        this.context = context;
        this.memos = memo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
          View listview = inflater.inflate(R.layout.model_view,null,true);

        TextView tvTitle =listview.findViewById(R.id.txtjudul);
        TextView tvSubtitle =listview.findViewById(R.id.txtDeskrpisi);
        TextView tanggal =listview.findViewById(R.id.txtTangal);

        Memo memo  = memos.get(position);

        tvTitle.setText(memo.getTitle());
        tvSubtitle.setText(memo.getSubTitle());
        tanggal.setText(memo.getDate());



return listview;
    }
}
