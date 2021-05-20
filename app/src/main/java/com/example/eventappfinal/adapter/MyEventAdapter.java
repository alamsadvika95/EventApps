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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventappfinal.R;
import com.example.eventappfinal.activity.AddNoteActivity;
import com.example.eventappfinal.activity.DetailMyEventActivity;
import com.example.eventappfinal.activity.MyEventActivity;
import com.example.eventappfinal.activity.SeeNoteActivity;
import com.example.eventappfinal.database.DatabaseHelper;

import java.util.ArrayList;

public class MyEventAdapter extends RecyclerView.Adapter<MyEventAdapter.ViewHolder> {

    private ArrayList nameList;
    private ArrayList contentList;
    private ArrayList idList;
    private ArrayList categoryList;
    private ArrayList dateList;

    private Context context;
    RecyclerView mRecyclerView;

    public MyEventAdapter(MyEventActivity myEventActivity, ArrayList nameList, ArrayList contentList, ArrayList idList, ArrayList categoryList, ArrayList dateList) {
        this.nameList = nameList;
        this.contentList = contentList;
        this.idList = idList;
        this.categoryList = categoryList;
        this.dateList = dateList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView detail, name, content, id, category, date;
        private ImageButton delete, note, seeNote;

        ViewHolder(View itemView) {
            super(itemView);

            //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
            context = itemView.getContext();

            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            mRecyclerView = itemView.findViewById(R.id.recyclerMyEvent);
            name = itemView.findViewById(R.id.viewEventName);
            content = itemView.findViewById(R.id.viewEventContent);
            id = itemView.findViewById(R.id.viewEventId);
            category = itemView.findViewById(R.id.viewEventCategory);
            date = itemView.findViewById(R.id.viewEventDate);
            detail = itemView.findViewById(R.id.detail);
            delete = itemView.findViewById(R.id.delete);
            note = itemView.findViewById(R.id.addNote);
            seeNote = itemView.findViewById(R.id.seeNote);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_my_event, parent, false);
        return new ViewHolder(V);
    }

    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final String Name = (String) nameList.get(position);
        final String Content = (String) contentList.get(position);
        final String Id = (String) idList.get(position);
        final String Category = (String) categoryList.get(position);
        final String Date = (String) dateList.get(position);
        holder.name.setText(Name);
        holder.content.setText(Content);
        holder.id.setText(Id);
        holder.category.setText(Category);
        holder.date.setText(Date);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }

            private void showDialog(){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Are you sure want to delete this event?");
                alertDialogBuilder
                        .setIcon(R.drawable.ic_delete)
                        .setCancelable(false)
                        .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                DatabaseHelper getDatabase = new DatabaseHelper(context);
                                SQLiteDatabase DeleteData = getDatabase.getWritableDatabase();
                                String selection = "id" + " LIKE ?";
                                String[] selectionArgs = {holder.id.getText().toString()};
                                DeleteData.delete("tb_event", selection, selectionArgs);

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

        holder.note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataForm = new Intent(view.getContext(), AddNoteActivity.class);
                dataForm.putExtra("SendId", holder.id.getText().toString());
                context.startActivity(dataForm);
                ((Activity)context).finish();
            }
        });

        holder.seeNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataForm = new Intent(view.getContext(), SeeNoteActivity.class);
                dataForm.putExtra("SendId", holder.id.getText().toString());
                context.startActivity(dataForm);
                ((Activity)context).finish();
            }
        });

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataForm = new Intent(view.getContext(), DetailMyEventActivity.class);
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
