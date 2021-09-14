package com.example.noteapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.os.Bundle;

import com.example.noteapp.Adapter;
import com.example.noteapp.Model.MyDataBase;
import com.example.noteapp.R;
import com.example.noteapp.databinding.ActivityFavouriteBinding;

public class Favourite extends AppCompatActivity {

    ActivityFavouriteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_favourite);
        MyDataBase dataBase=MyDataBase.getInstance(getApplicationContext());
        Adapter adapter=new Adapter(getApplicationContext());
        binding.recyclerview.setAdapter(adapter);
        adapter.setNotes(dataBase.access().getFavouriteNotes());
        binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }
}