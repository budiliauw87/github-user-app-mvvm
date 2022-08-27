package com.liaudev.githubuser.core.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class User implements Parcelable {
    private String id;
    private String login;
    private String name;
    private String location;
    private String email;
    private String company;
    private String avatarUrl;
    private int follower;
    private int following;
    private int repositories;
    private String cursor;
    public User(){}

    protected User(Parcel in) {
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

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}