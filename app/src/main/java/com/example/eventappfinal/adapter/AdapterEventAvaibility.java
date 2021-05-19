package com.example.eventappfinal.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.eventappfinal.R;
import com.example.eventappfinal.activity.DetailEventActivity;
import com.example.eventappfinal.fragment.OneTimeEventFragment;

import java.util.ArrayList;

public class AdapterEventAvaibility extends RecyclerView.Adapter<AdapterEventAvaibility.ViewHolder>{
    private ArrayList nameList;
    private ArrayList contentList;
    private ArrayList idList;
    private ArrayList emailList;
    private ArrayList dateList;

    private Context context;
    RecyclerView mRecyclerView;


//Membuat Konstruktor pada Class RecyclerViewAdapter
public AdapterEventAvaibility(OneTimeEventFragment oneTimeEventFragment, ArrayList nameList, ArrayList contentList, ArrayList idList, ArrayList emailList, ArrayList dateList) {
        this.nameList = nameList;
        this.contentList = contentList;
        this.idList = idList;
        this.emailList = emailList;
        this.dateList = dateList;
        }

//ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView detail, name, content, id, email, date;

    ViewHolder(View itemView) {
        super(itemView);

        //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
        context = itemView.getContext();

        //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
        mRecyclerView = itemView.findViewById(R.id.recyclerOneTime);
        name = itemView.findViewById(R.id.viewEventNameAvaibility);
        content = itemView.findViewById(R.id.viewEventContentAvaibility);
        id = itemView.findViewById(R.id.viewEventIdAvaibility);
        email = itemView.findViewById(R.id.viewEventEmailAvaibility);
        date = itemView.findViewById(R.id.viewEventDateAvaibility);
        detail = itemView.findViewById(R.id.eventDetail);
    }
}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_event_avaibility, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String Name = (String) nameList.get(position);//Mengambil data (Judul) sesuai dengan posisi yang telah ditentukan
        final String Content = (String) contentList.get(position);
        final String Id = (String) idList.get(position);
        final String Email = (String) emailList.get(position);
        final String Date = (String) dateList.get(position);
        holder.name.setText(Name);
        holder.content.setText(Content);
        holder.id.setText(Id);
        holder.email.setText(Email);
        holder.date.setText(Date);


//      onklik see detail
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent dataForm = new Intent(view.getContext(), DetailEventActivity.class);
                dataForm.putExtra("SendId", holder.id.getText().toString());
                context.startActivity(dataForm);
                ((Activity)context).finish();
            }

        });
    }
    @Override
    public int getItemCount() {
        return idList.size();
    }
}

