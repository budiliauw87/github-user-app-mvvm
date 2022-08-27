package com.liaudev.githubuser.core.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Budiliauw87 on 2022-06-22.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
@Entity
public class UserEntity implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "avatarUrl")
    private String avatarUrl;

    @ColumnInfo(name = "follower")
    private int follower;

    @ColumnInfo(name = "following")
    private int following;

    @ColumnInfo(name = "repositories")
    private int repositories;

    @ColumnInfo(name = "cursor")
    private String cursor;

    public UserEntity() {
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getRepositories() {
        return repositories;
    }

    public void setRepositories(int repositories) {
        this.repositories = repositories;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(login);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(email);
        parcel.writeString(company);
        parcel.writeString(avatarUrl);
        parcel.writeInt(follower);
        parcel.writeInt(following);
        parcel.writeInt(repositories);
        parcel.writeString(cursor);
    }

    protected UserEntity(Parcel in) {
        id = in.readString();
        login = in.readString();
        name = in.readString();
        location = in.readString();
        email = in.readString();
        company = in.readString();
        avatarUrl = in.readString();
        follower = in.readInt();
        following = in.readInt();
        repositories = in.readInt();
        cursor = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel in) {
            return new UserEntity(in);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
