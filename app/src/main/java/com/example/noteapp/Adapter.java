package com.example.noteapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.noteapp.Activity.NoteActivity;
import com.example.noteapp.Model.Note;
import com.example.noteapp.databinding.NoteItemBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.HoldItem> {
    private Context context;
    private List<Note> notes=new ArrayList<>();

    public Adapter(Context context) {
        this.context = context;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HoldItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        NoteItemBinding binding= DataBindingUtil.inflate(inflater, R.layout.note_item,parent,false);
        return new HoldItem(binding);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull HoldItem holder, int position) {
     holder.binding.header.setText(notes.get(position).getTitle());
     holder.binding.SubTitle.setText(notes.get(position).getSubtitle());
     holder.binding.date.setText(notes.get(position).getTime());
     String path=notes.get(position).getImageUrl();
     if(path!=null)
     {
         holder.binding.imgNote.setVisibility(View.VISIBLE);
         Glide.with(context)
                 .asBitmap().load(path)
                 .into(holder.binding.imgNote);
     }
        ColorDrawable colorDrawable;
      switch (notes.get(position).getColor())
      {
          case 1:
              colorDrawable= new ColorDrawable(ContextCompat.getColor(context, R.color.ColorBlueNote));
              holder.binding.parent.setBackground(colorDrawable);
              break;
          case 2:
              colorDrawable=new ColorDrawable(ContextCompat.getColor(context, R.color.ColorYellowNote));
              holder.binding.parent.setBackground(colorDrawable);
              break;
          case 3:
              colorDrawable= new ColorDrawable(ContextCompat.getColor(context, R.color.ColorOrangeNote));
              holder.binding.parent.setBackground(colorDrawable);
              break;
          case 4:
              colorDrawable= new ColorDrawable(ContextCompat.getColor(context, R.color.ColorGreenNote));
              holder.binding.parent.setBackground(colorDrawable);
              break;
          default:

      }

      holder.binding.parent.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context, NoteActivity.class);
              intent.putExtra("ID",notes.get(position));
              context.startActivity(intent);
          }
      });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class  HoldItem extends RecyclerView.ViewHolder{
        NoteItemBinding binding;
        public HoldItem(@NonNull NoteItemBinding binding ) {
            super(binding.getRoot());
            this.binding=binding;
        }

    }
}
