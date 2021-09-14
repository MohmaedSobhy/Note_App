package com.example.noteapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.noteapp.Model.MyDataBase;
import com.example.noteapp.Model.Note;
import com.example.noteapp.R;
import com.example.noteapp.databinding.ActivityNoteBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteActivity extends AppCompatActivity {

    ActivityNoteBinding binding;
    BottomSheetDialog bottomSheetDialog;
    ImageView blue,orange,green,yellow;
    LinearLayout deletenote,addImage,favourite;
    int selectedColor=1;
    String imagePath="";
    boolean isFavourite=false;
    ImageView lover;
    MyDataBase dataBase;
    Note incomingnote=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_note);
        bottomSheetDialog=new BottomSheetDialog(NoteActivity.this);
        bottomSheetDialog.setContentView(R.layout.bottom_dialog);
        blue=bottomSheetDialog.findViewById(R.id.color1);
        orange=bottomSheetDialog.findViewById(R.id.color2);
        green=bottomSheetDialog.findViewById(R.id.color3);
        yellow=bottomSheetDialog.findViewById(R.id.color4);
        deletenote =bottomSheetDialog.findViewById(R.id.delete);
        addImage=bottomSheetDialog.findViewById(R.id.addimage);
        favourite=bottomSheetDialog.findViewById(R.id.favourite);
        lover=bottomSheetDialog.findViewById(R.id.love);
        dataBase=MyDataBase.getInstance(getApplicationContext());

        Intent comingIntent=getIntent();

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }

        if(comingIntent!=null)
        {
            incomingnote=comingIntent.getParcelableExtra("ID");
            if(incomingnote!=null)
            {
                binding.etNoteTitle.setText(incomingnote.getTitle());
                binding.etNoteSubTitle.setText(incomingnote.getSubtitle());
                binding.details.setText(incomingnote.getDetails());
                binding.tvDateTime.setText(incomingnote.getTime());
                selectedColor=incomingnote.getColor();
                String path=incomingnote.getImageUrl();
                if(path!=null)
                {
                    //binding.imgNote.setImageURI(Uri.parse(path));
                    Glide.with(getApplicationContext())
                            .asBitmap().load(path)
                            .into(binding.imgNote);
                    binding.layoutImage.setVisibility(View.VISIBLE);
                    binding.imgNote.setVisibility(View.VISIBLE);
                }
                if(incomingnote.isFavourite())
                   lover.setImageResource(R.drawable.lover);
                else
                    lover.setImageResource(R.drawable.heart);
                int c=incomingnote.getColor();
                blue.setImageResource(0);
                if(c==1)
                {
                    blue.setImageResource(R.drawable.ic_baseline_check_24);
                    binding.colorView.setBackground(getDrawable(R.color.ColorBlueNote));
                }
                else if(c==2)
                {
                    yellow.setImageResource(R.drawable.ic_baseline_check_24);
                    binding.colorView.setBackground(getDrawable(R.color.ColorYellowNote));
                }
                else if(c==3) {
                    orange.setImageResource(R.drawable.ic_baseline_check_24);
                    binding.colorView.setBackground(getDrawable(R.color.ColorOrangeNote));
                }
                else
                {
                    green.setImageResource(R.drawable.ic_baseline_check_24);
                    binding.colorView.setBackground(getDrawable(R.color.ColorGreenNote));
                }
            }
            else{
                binding.tvDateTime.setText(getTime());
            }
        }



        binding.imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(incomingnote==null)
                {
                    Note insert=new Note();

                    if(binding.etNoteTitle.getText().toString().isEmpty())
                    {
                        binding.etNoteTitle.requestFocus();
                        binding.etNoteTitle.setError("Enter Title");
                        return;
                    }

                    if(binding.etNoteSubTitle.getText().toString().isEmpty())
                    {
                        binding.etNoteSubTitle.requestFocus();
                        binding.etNoteSubTitle.setError("Enter SubTitle");
                        return;
                    }

                    insert.setTitle(binding.etNoteTitle.getText().toString());
                    insert.setSubtitle(binding.etNoteSubTitle.getText().toString());
                    insert.setDetails(binding.details.getText().toString());
                    insert.setColor(selectedColor);
                    insert.setFavourite(isFavourite);
                    insert.setImageUrl(imagePath);
                    insert.setTime(getTime());
                    dataBase.access().InsertNewNote(insert);
                    Toast.makeText(NoteActivity.this, "Insert is Done", Toast.LENGTH_SHORT).show();
                }// update current note
                else{
                  UpdateCurrentNote();
                }
            }
        });
        binding.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.layoutImage.setVisibility(View.GONE);
                binding.imgNote.setImageResource(0);
            }
        });
        binding.bottomsheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ShowDialog();
            }
        });
        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                if(incomingnote!=null)
                    UpdateCurrentNote();
                finishAffinity();
            }
        });


    }
    private String getTime()
    {
        Calendar calendar= Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy  hh:mm:ss");
        String date=simpleDateFormat.format(calendar.getTime());
        return date;
    }

    private void UpdateCurrentNote(){
            incomingnote.setColor(selectedColor);
            incomingnote.setDetails(binding.details.getText().toString());
            incomingnote.setSubtitle(binding.etNoteSubTitle.getText().toString());
            incomingnote.setTitle(binding.etNoteTitle.getText().toString());
            incomingnote.setFavourite(isFavourite);
            if(imagePath.length()>2)
                incomingnote.setImageUrl(imagePath);

            dataBase.access().UpdateNote(incomingnote);
    }

    private void ShowDialog() {

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.colorView.setBackground(getDrawable(R.color.ColorBlueNote));
                blue.setImageResource(R.drawable.ic_baseline_check_24);
                selectedColor=1;
                orange.setImageResource(0);
                green.setImageResource(0);
                yellow.setImageResource(0);
            }
        });
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.colorView.setBackground(getDrawable(R.color.ColorYellowNote));
                yellow.setImageResource(R.drawable.ic_baseline_check_24);
                selectedColor=2;
                orange.setImageResource(0);
                green.setImageResource(0);
                blue.setImageResource(0);
            }
        });
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.colorView.setBackground(getDrawable(R.color.ColorOrangeNote));
                orange.setImageResource(R.drawable.ic_baseline_check_24);
                selectedColor=3;
                blue.setImageResource(0);
                green.setImageResource(0);
                yellow.setImageResource(0);
            }
        });
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor=4;
                binding.colorView.setBackground(getDrawable(R.color.ColorGreenNote));
                green.setImageResource(R.drawable.ic_baseline_check_24);
                orange.setImageResource(0);
                blue.setImageResource(0);
                yellow.setImageResource(0);
            }
        });
        deletenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(NoteActivity.this)
                .setTitle("Delete")
                .setMessage("Are You sure you want to Delete This note ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dataBase.access().DeleteNote(incomingnote);
                                binding.layoutImage.setVisibility(View.GONE);
                                binding.etNoteSubTitle.setText("");
                                binding.etNoteTitle.setText("");
                                binding.details.setText("");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                bottomSheetDialog.dismiss();
                builder.create().show();
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent,105);
            }
        });
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   if(!isFavourite)
                   {
                       lover.setImageResource(R.drawable.lover);
                       isFavourite=true;
                   }
                   else {
                       lover.setImageResource(R.drawable.heart);
                       isFavourite=false;
                   }
            }
        });
        bottomSheetDialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap image;
        if(requestCode==105)
        {
            try {
                image= MediaStore.Images.Media.getBitmap(this.getContentResolver(),data.getData());
                binding.layoutImage.setVisibility(View.VISIBLE);
                if(data.getData()!=null)
                    imagePath=getPathFromUri(data.getData());
                binding.imgNote.setImageBitmap(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPathFromUri(Uri contentUri)
    {
        String filePath="";
        Cursor cursor = getContentResolver().query(contentUri,null,null,null,null);
        if (cursor == null){
            filePath = contentUri.getPath();
        }else{
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(incomingnote!=null)
            UpdateCurrentNote();
    }

}