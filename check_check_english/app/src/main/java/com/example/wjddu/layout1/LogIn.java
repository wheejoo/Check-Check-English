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
    private TextView t;
    private TextView t2;
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
        t = findViewById(R.id.t);
        t2 = findViewById(R.id.t2);
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
            t2.setText("이메일공백");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            t2.setText("이메일형식불일치");
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            t2.setText("비밀번호공백");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            t2.setText("비밀번호형식불일치");
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
                            //Intent intent = new Intent(LogIn.this, MainActivity.class);
                            //startActivity(intent);
                        } else {
                            // 회원가입 실패
                            //Toast.makeText(LogIn.this, getString(R.string.failed_signup), Toast.LENGTH_SHORT).show();
                            try {
                                throw task.getException();
                            }
                            catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthEmailException e){
                                Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                            }
                            catch (FirebaseAuthException e){
                                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(LogIn.this, getString(R.string.success_signup), Toast.LENGTH_SHORT).show();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser user = task.getResult().getUser();
                            User userModel = new User(user.getEmail());
                            databaseReference.child("users").child(user.getUid()).setValue(userModel);
                        } else {
                            // 로그인 실패
                            Toast.makeText(LogIn.this,getString(R.string.failed_signup),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
