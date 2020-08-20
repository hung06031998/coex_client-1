package com.upit.coex.user.screen.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.upit.coex.user.R;
import com.upit.coex.user.screen.addinfomation.AddInfomationActivity;
import com.upit.coex.user.screen.dialog.DialogLoading;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirm;
    private Button btnRegister;
    private TextView txtLogin;
    private DialogLoading mDialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindView();
    }

    public void bindView() {
        mDialogLoading = new DialogLoading(this);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirm = findViewById(R.id.edtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPass()) {
                    Intent moveToAddInfo = new Intent(RegisterActivity.this, AddInfomationActivity.class);
                    moveToAddInfo.putExtra("email", edtEmail.getText().toString());
                    moveToAddInfo.putExtra("pass", edtPassword.getText().toString());
                    startActivity(moveToAddInfo);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "check pass !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txtLogin.setOnClickListener(v -> {
            this.finish();
        });
    }

    private boolean checkPass() {
        if (edtPassword.getText().toString().equals(edtConfirm.getText().toString())) {
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
