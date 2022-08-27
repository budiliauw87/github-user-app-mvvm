package com.liaudev.githubuser.core.data.remote.response;

import com.liaudev.githubuser.core.domain.model.User;

import java.util.List;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class UserResponse {
    private List<User> userList;
    private String nextCursor;

    public UserResponse(List<User> list, String nextCursor) {
        this.userList = list;
        this.nextCursor = nextCursor;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> users) {
        this.userList = users;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

}
