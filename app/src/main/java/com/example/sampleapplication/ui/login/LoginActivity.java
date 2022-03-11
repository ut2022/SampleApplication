package com.example.sampleapplication.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sampleapplication.R;
import com.example.sampleapplication.databinding.ActivityLoginBinding;
import com.example.sampleapplication.login.model.LoginResults;
import com.example.sampleapplication.login.roomdatabase.UserDao;
import com.example.sampleapplication.login.roomdatabase.UserDatabase;
import com.example.sampleapplication.login.roomdatabase.UserEntity;
import com.example.sampleapplication.login.roomdatabase.UserRepository;
import com.example.sampleapplication.login.viewmodel.LoginViewModel;
import com.example.sampleapplication.ui.register.RegisterActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static String PREFS_NAME = "MyPrefsFile";
    EditText etPhno;
    Button buttonLogin;
    EditText etPassword;
    Button buttonRegister;
    LoginViewModel loginViewModel;
    Boolean islogin = false;
    private LiveData<Integer> user_count;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class); //initialize viewmodel
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userDao = new UserRepository(getApplication()).userDao;
        registerObservers();
        registerButtonObserver();
        binding.setViewModel(loginViewModel);
        loginViewModel.getData();
        dbObserver();
        observerUserExistStatus();
//        loginViewModel.checkuser(getApplication());


        buttonLogin = findViewById(R.id.bt_login);
        etPhno = findViewById(R.id.et_login_phno);
        etPassword = findViewById(R.id.et_login_password);
        buttonRegister = findViewById(R.id.bt_register);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.validate(etPhno.getText().toString(),etPassword.getText().toString());
                loginViewModel.setUserphonenumber(etPhno.getText().toString());
                loginViewModel.setUserpassword(etPassword.getText().toString());
            }
        });
        loginViewModel.getValidationLivedata().observe(this, new Observer<Validationtype>() {
            @Override
            public void onChanged(Validationtype s) {
                switch (s) {
                    case PHONE:
                        Toast.makeText(LoginActivity.this, "Please Fill PhoneNumber", Toast.LENGTH_SHORT).show();
                        break;

                    case SUCCESS:
                       // Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////                intent.putExtra("user", user);
//                        startActivity(intent);
//                        loginViewModel.getAllData();
                        /**
                         * After Successful Validation Check if User Exist
                         */
                        checkIfUserExists();
                        break;

                    case PASSWORD:
                        Toast.makeText(LoginActivity.this, "Please Fill Password", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
    private Boolean validate() {
        if (etPhno.getText().toString().isEmpty() || etPhno.getText().length() <= 10) {
            Toast.makeText(LoginActivity.this, "Please Fill PhoneNumber", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please Fill Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public enum Validationtype {
        PHONE,SUCCESS, PASSWORD
    }

    /**
     * To Check If user Exist in Room Database
     */
    private void checkIfUserExists() {
        String userMob = loginViewModel.getUserphonenumber();
        LiveData<List<UserEntity>> userEntitiesLiveData = loginViewModel.getDbData(userDao);
        userEntitiesLiveData.observe(LoginActivity.this, userEntities -> {
            if (!userEntities.isEmpty()) {
                for (UserEntity userEntity : userEntities) {
                    if (userMob.equals(userEntity.getUserphno())) {
                        loginViewModel.isUserExist.setValue(true);
                        return;
                    }
                }
            }
            loginViewModel.isUserExist.setValue(false);
        });
    }

    /**
     * Observing for Logging In User
     */
    private void observerUserExistStatus() {
        loginViewModel.isUserExist.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean exist) {
                if (exist){
                    /**
                     * Create SharedPres a seperate class
                     */
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, "Login Successful: Navigate to Home", Toast.LENGTH_SHORT).show();
                    //Navigate To HomeActivity
                }else{
                    Toast.makeText(LoginActivity.this, "No user exists with this phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerObservers() {
        loginViewModel.getLoginResponsedata().observe(this, new Observer<List<LoginResults>>() {
            @Override
            public void onChanged(List<LoginResults> loginResults) {
                if (loginResults == null) {
                    Toast.makeText(LoginActivity.this, "Error has occured", Toast.LENGTH_SHORT).show();
                } else {
                    if (!islogin)
                        return;
//                    SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, 0);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    editor.putBoolean("hasLoggedIn", true);
//                    editor.commit();
                }
            }
        });
    }
    private void dbObserver(){
        userDao.getValues().observe(this, new Observer<List<UserEntity>>() {
            @Override
            public void onChanged(List<UserEntity> userEntities) {
                for(UserEntity userEntity:userEntities){
                    if(userEntity.getUserphno().equals(loginViewModel.getUserphonenumber())){
                        Log.d("Phone no","phno is present");
                    }
                }

            }
        });
    }


    private void registerButtonObserver(){
        loginViewModel.registerEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}