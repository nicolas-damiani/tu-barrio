package com.engineers.tubarrio.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.engineers.tubarrio.R;
import com.engineers.tubarrio.requests.LoginRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

public class LoginActivity extends Activity {

    private GoogleSignInClient googleClient;
    public static final int RC_SIGN_IN = 777;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SignInButton loginBtn = (SignInButton) findViewById(R.id.sign_in_button);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("879686178659-1q5oi1ng2eiv6nc0cduj7epubf06ic1m.apps.googleusercontent.com")
                .build();

        googleClient = GoogleSignIn.getClient(this, gso);

    }

    private void signIn() {
        Intent signInIntent = googleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            GoogleSignInResult task = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(GoogleSignInResult completedTask) {

            GoogleSignInAccount account = completedTask.getSignInAccount();
            if (account!=null) {
                String googleToken = account.getIdToken();
                // Signed in successfully, show authenticated UI.
                LoginRequest loginRequest = new LoginRequest(this, googleToken);
            }else{
                Toast.makeText(this, "Error, verifique conexion a internet", Toast.LENGTH_SHORT);
            }

    }

}
