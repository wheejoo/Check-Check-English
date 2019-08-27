package com.example.wjddu.layout1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

class User {
    public String email;

    public User() {
    }

    public User(String email) {
        this.email = email;
    }
}

public class LogIn extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button btn_signUp;
    private Button btn_signIn;

    private String email = "";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);
        btn_signUp = findViewById(R.id.btn_signUp);
        btn_signIn = findViewById(R.id.btn_signIn);

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (isValidEmail() && isValidPasswd()) {
                    loginUser(email, password);
                }
            }
        });

        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if (isValidEmail() && isValidPasswd()) {
                    createUser(email, password);
                }
            }
        });
    }
//
//    public void signUp(View view) {
//        email = editTextEmail.getText().toString();
//        password = editTextPassword.getText().toString();
//
//        if (isValidEmail() && isValidPasswd()) {
//            createUser(email, password);
//        }
//    }



//    public void signIn(View view) {
//        email = editTextEmail.getText().toString();
//        password = editTextPassword.getText().toString();
//
//        if (isValidEmail() && isValidPasswd()) {
//            loginUser(email, password);
//        }
//    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            Toast.makeText(LogIn.this, "이메일 공백", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            Toast.makeText(LogIn.this, "이메일 형식 불일치", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(LogIn.this, "비밀번호 공백", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            Toast.makeText(LogIn.this, "비밀번호 형식 불일치(숫자와 문자, 특수문자를 각 하나 이상씩 사용하세요)", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    // 회원가입
    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(LogIn.this, getString(R.string.success_signup), Toast.LENGTH_SHORT).show();
                        } else {
                            // 회원가입 실패
                            //Toast.makeText(LogIn.this, getString(R.string.failed_signup), Toast.LENGTH_SHORT).show();
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(), "비밀번호의 형식이 올바르지 않습니다", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthEmailException e){
                                Toast.makeText(getApplicationContext(), "이메일의 형식이 올바르지 않습니다.", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthException e){
                                Toast.makeText(getApplicationContext(), "이미 있는 이메일 입니다.", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    // 로그인
    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(LogIn.this, getString(R.string.success_login), Toast.LENGTH_SHORT).show();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser user = task.getResult().getUser();
                            User userModel = new User(user.getEmail());
                            databaseReference.child("users").child("email").setValue(userModel);

//                            Intent intent = new Intent(LogIn.this, MainActivity.class);
//                            startActivity(intent);
                            String email = user.getEmail();
                            Intent intent = new Intent(LogIn.this, MainActivity.class);
                            intent.putExtra("value",email);
                            startActivity(intent);
                            finish();
                        } else {
                            // 로그인 실패
                            //Toast.makeText(LogIn.this,getString(R.string.failed_login),Toast.LENGTH_SHORT).show();
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(), "비밀번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthEmailException e){
                                Toast.makeText(getApplicationContext(), "이메일이 틀렸습니다.", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthException e){
                                Toast.makeText(getApplicationContext(), "없는 이메일 입니다.", Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
