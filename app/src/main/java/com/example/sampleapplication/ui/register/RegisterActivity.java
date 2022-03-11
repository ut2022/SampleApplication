package com.example.sampleapplication.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sampleapplication.R;
import com.example.sampleapplication.login.roomdatabase.UserEntity;
import com.example.sampleapplication.login.roomdatabase.UserRepository;
import com.example.sampleapplication.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    EditText regName;
    EditText regEmail;
    EditText regPassword;
    EditText regPhno;
    Button proceedBtn;
    String username,userphno,userpassword,UserEmail;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = findViewById(R.id.et_reg_name);
        regPhno = findViewById(R.id.et_reg_phno);
        regEmail = findViewById(R.id.et_reg_email);
        regPassword = findViewById(R.id.et_reg_password);
        proceedBtn = findViewById(R.id.bt_proceed);



        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate())
                    return;
                username = regName.getText().toString();
                userphno = regPhno.getText().toString();
                userpassword = regPassword.getText().toString();
                UserEmail = regEmail.getText().toString();

                userRepository=new UserRepository(getApplication());
                UserEntity userEntity=new UserEntity(username,UserEmail,userphno,userpassword);
                userRepository.InsertTask(userEntity);

                regName.setText("");
                regEmail.setText("");
                regPassword.setText("");
                regEmail.setText("");

//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);

            }
        });

    }
    private Boolean validate() {
        if (!isValidEmail(regEmail.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Please Check mailid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (regPhno.getText().toString().isEmpty() || regPhno.getText().length() < 10) {
            Toast.makeText(RegisterActivity.this, "Please Fill Phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (regName.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please Fill Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!validatePassword(regPassword.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Please check password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean validatePassword(final String password){
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

//                Toast.makeText(this, "Back button pressed!", Toast.LENGTH_SHORT).show();
//                this.onBackPressed();
                finish();  //destroy method called
                return true;
        }
        return super.onOptionsItemSelected(item);  //immediate base class function call
    }
}
