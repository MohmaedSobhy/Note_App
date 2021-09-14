package com.example.noteapp.Model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity()
public class Note  implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String subtitle;
    String time;
    String ImageUrl;
    String Details;
    boolean favourite;
    int color;

    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        subtitle = in.readString();
        time = in.readString();
        ImageUrl = in.readString();
        Details = in.readString();
        favourite = in.readByte() != 0;
        color = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", time='" + time + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", Details='" + Details + '\'' +
                ", favourite=" + favourite +
                ", color=" + color +
                '}';
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public Note(String title, String subtitle, String time, String imageUrl, String details, boolean favourite, int color, int image) {
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        ImageUrl = imageUrl;
        Details = details;
        this.color = color;
        this.favourite=favourite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Note()
    {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(time);
        dest.writeString(ImageUrl);
        dest.writeString(Details);
        dest.writeByte((byte) (favourite ? 1 : 0));
        dest.writeInt(color);
    }
}
