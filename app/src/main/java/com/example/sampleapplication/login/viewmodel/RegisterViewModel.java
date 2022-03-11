package com.example.sampleapplication.login.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sampleapplication.login.roomdatabase.UserEntity;
import com.example.sampleapplication.login.roomdatabase.UserRepository;

import java.util.List;

public class RegisterViewModel extends AndroidViewModel {
    public UserRepository userRepository;
    public LiveData<List<UserEntity>> getAllPosts;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);


    }
}
