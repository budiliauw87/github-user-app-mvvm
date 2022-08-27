package com.liaudev.githubuser.core.utils;

import android.annotation.SuppressLint;

import com.liaudev.githubuser.core.data.local.entity.UserEntity;
import com.liaudev.githubuser.core.data.remote.response.UserResponse;
import com.liaudev.githubuser.core.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Budiliauw87 on 2022-08-21.
 * budiliauw87.github.io
 * Budiliauw87@gmail.com
 */
public class DataMapper {
    public static List<UserEntity> mapResponsesToEntities(UserResponse userResponse){
        @SuppressLint({"NewApi", "LocalSuppress"})
        List<UserEntity> userEntities = userResponse.getUserList().stream().map(user -> {
            UserEntity entity = new UserEntity();
            entity.setId(user.getId());
            entity.setName(user.getName());
            entity.setLogin(user.getLogin());
            entity.setAvatarUrl(user.getAvatarUrl());
            entity.setCompany(user.getCompany());
            entity.setLocation(user.getLocation());
            entity.setEmail(user.getEmail());
            entity.setRepositories(user.getRepositories());
            entity.setFollower(user.getFollower());
            entity.setFollowing(user.getFollowing());
            entity.setCursor(user.getCursor());
            return entity;
        }).collect(Collectors.toList());
        return userEntities;
    }

    public static List<User> mapEntitiesToDomain(List<UserEntity> userEntities){
        @SuppressLint({"NewApi", "LocalSuppress"})
        List<User> userList = userEntities.stream().map(entity -> {
            User user = new User();
            user.setId(entity.getId());
            user.setName(entity.getName());
            user.setLogin(entity.getLogin());
            user.setAvatarUrl(entity.getAvatarUrl());
            user.setCompany(entity.getCompany());
            user.setLocation(entity.getLocation());
            user.setEmail(entity.getEmail());
            user.setRepositories(entity.getRepositories());
            user.setFollower(entity.getFollower());
            user.setFollowing(entity.getFollowing());
            user.setCursor(entity.getCursor());
            return user;
        }).collect(Collectors.toList());
        return userList;
    }

    public static UserEntity mapDomainToEntity(User user){
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setLogin(user.getLogin());
        entity.setAvatarUrl(user.getAvatarUrl());
        entity.setCompany(user.getCompany());
        entity.setLocation(user.getLocation());
        entity.setEmail(user.getEmail());
        entity.setRepositories(user.getRepositories());
        entity.setFollower(user.getFollower());
        entity.setFollowing(user.getFollowing());
        entity.setCursor(user.getCursor());
        return entity;
    }
    public static User mapEntityToDomain(UserEntity entity){
        User user = new User();
        if(entity!=null){
            user.setId(entity.getId());
            user.setName(entity.getName());
            user.setLogin(entity.getLogin());
            user.setAvatarUrl(entity.getAvatarUrl());
            user.setCompany(entity.getCompany());
            user.setLocation(entity.getLocation());
            user.setEmail(entity.getEmail());
            user.setRepositories(entity.getRepositories());
            user.setFollower(entity.getFollower());
            user.setFollowing(entity.getFollowing());
            user.setCursor(entity.getCursor());
        }
        return user;
    }
}
