package com.example.noteapp.Model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@androidx.room.Dao
public interface Access {
    @Insert
    public void InsertNewNote(Note note);

    @Delete
    public void DeleteNote(Note note);

    @Query("SELECT * FROM Note")
    public List<Note> getAllNotes();

    @Query("SELECT * FROM Note  WHERE favourite=1")
    public List <Note>getFavouriteNotes();

    @Update()
    public void UpdateNote(Note note);




}
