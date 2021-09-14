package com.example.noteapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.noteapp.Adapter;
import com.example.noteapp.Model.MyDataBase;
import com.example.noteapp.Model.Note;
import com.example.noteapp.R;
import com.example.noteapp.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    Adapter adapter;
    MyDataBase dataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

         dataBase=MyDataBase.getInstance(getApplicationContext());
        binding=DataBindingUtil.setContentView(this,R.layout.activity_home);

        adapter=new Adapter(getApplicationContext());
        adapter.setNotes(dataBase.access().getAllNotes());
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));


        binding.floatingbuttone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NoteActivity.class));
            }
        });
        binding.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Favourite.class));
            }
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetResults(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                GetResults(newText);
                return true;
            }
        });
        binding.searchView.setOnCloseListener(new SearchView.OnCloseListener() {
          @Override
          public boolean onClose() {
              adapter.setNotes(dataBase.access().getAllNotes());
              //binding.recyclerview.setAdapter(adapter);
              return false;
          }
      });
    }



    private void GetResults(String text)
    {
        text.toLowerCase();
        List<Note>notes=new ArrayList<>();
        if(dataBase.access().getAllNotes()!=null)
        {
            for (Note n: dataBase.access().getAllNotes())
            {
                if(n.getTitle().toLowerCase().equalsIgnoreCase(text))
                    notes.add(n);

                String []names=n.getTitle().split(" ");
                for(int i=0;i<names.length;i++)
                {
                    if(text.equalsIgnoreCase(names[i]))
                    {
                        boolean doesExist=false;
                        for(Note l: dataBase.access().getAllNotes())
                        {
                            if(l.getId()==n.getId())
                                doesExist=true;

                        }
                        if(!doesExist)
                          notes.add(n);
                    }
                }
            }
            if(notes.size()!=0)
              adapter.setNotes(notes);

          //  binding.recyclerview.setAdapter(adapter);
        }
    }



}