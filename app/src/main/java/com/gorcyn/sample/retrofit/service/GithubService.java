package com.gorcyn.sample.retrofit.service;

import com.gorcyn.sample.retrofit.model.Repos;
import com.gorcyn.sample.retrofit.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GithubService {

    @GET("/users/{user}")
    void getUser(@Path("user") String user, Callback<User> callback);

    @GET("/users/{user}/repos")
    void getUserRepos(@Path("user") String user, Callback<List<Repos>> callback);
}
