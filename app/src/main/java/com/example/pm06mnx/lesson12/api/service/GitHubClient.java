package com.example.pm06mnx.lesson12.api.service;

import com.example.pm06mnx.lesson12.api.model.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {
    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(
            @Path("user") String user
    );
}

