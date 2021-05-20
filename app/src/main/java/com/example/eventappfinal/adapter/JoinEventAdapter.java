package com.example.eventappfinal.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventappfinal.R;
import com.example.eventappfinal.activity.DetailEventActivity;
import com.example.eventappfinal.activity.DetailJoinActivity;
import com.example.eventappfinal.activity.JoinEventActivity;
import com.example.eventappfinal.activity.MyJoinEventActivity;
import com.example.eventappfinal.database.DatabaseHelper;

import java.util.ArrayList;

public class JoinEventAdapter extends RecyclerView.Adapter<JoinEventAdapter.ViewHolder>{
    private ArrayList nameList;
    private ArrayList contentList;
    private ArrayList idList;
    private ArrayList emailList;
    private ArrayList dateList;

    private Context context;
    RecyclerView mRecyclerView;


    //Membuat Konstruktor pada Class RecyclerViewAdapter
    public JoinEventAdapter(MyJoinEventActivity myJoinEventActivity, ArrayList nameList, ArrayList contentList, ArrayList idList, ArrayList emailList, ArrayList dateList) {
        this.nameList = nameList;
        this.contentList = contentList;
        this.idList = idList;
        this.emailList = emailList;
        this.dateList = dateList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView detail, name, content, id, email, date;
        private ImageButton delete;

        ViewHolder(View itemView) {
            super(itemView);

            //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
            context = itemView.getContext();

            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            mRecyclerView = itemView.findViewById(R.id.recyclerJoinEvent);
            name = itemView.findViewById(R.id.viewJoinName);
            content = itemView.findViewById(R.id.viewJoinContent);
            id = itemView.findViewById(R.id.viewJoinId);
            email = itemView.findViewById(R.id.viewJoinCreator);
            date = itemView.findViewById(R.id.viewJoinDate);
            detail = itemView.findViewById(R.id.detailJoin);
            delete = itemView.findViewById(R.id.deleteJoin);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_join_event, parent, false);
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

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
            private void showDialog(){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Are you sure want cancel to join this event?");
                alertDialogBuilder
                        .setIcon(R.drawable.ic_delete)
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                DatabaseHelper getDatabase = new DatabaseHelper(context);
                                SQLiteDatabase DeleteData = getDatabase.getWritableDatabase();
                                String selection = "id" + " LIKE ?";
                                String[] selectionArgs = {holder.id.getText().toString()};
                                DeleteData.delete("tb_join", selection, selectionArgs);

                                int position = idList.indexOf(Id);
                                idList.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context,"Data was deleted",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
//      onklik see detail
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent dataForm = new Intent(view.getContext(), DetailJoinActivity.class);
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
