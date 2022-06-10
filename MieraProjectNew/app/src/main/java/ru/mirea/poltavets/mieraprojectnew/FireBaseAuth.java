package ru.mirea.poltavets.mieraprojectnew;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.poltavets.mieraprojectnew.ui.home.HomeFragment;

public class FireBaseAuth extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordTextEdit;
    private TextView statusTextView;
    private FirebaseAuth auth;
    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleSignInOptions gso;
    private GoogleSignInClient gcs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fire_base_auth);
        // Views
        emailEditText = findViewById(R.id.editTextEmail);
        passwordTextEdit = findViewById(R.id.editTextPassword);
        statusTextView = findViewById(R.id.textViewStatus);
        // Buttons
        findViewById(R.id.signInBtn).setOnClickListener(this);
        findViewById(R.id.createBtn).setOnClickListener(this);
        findViewById(R.id.signOutBtn).setOnClickListener(this);
        // [START initialize_auth]
        auth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        gso  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gcs = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.google_sign_in).setOnClickListener(this::googleSignIn);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    @SuppressLint("StringFormatInvalid")
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            statusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            findViewById(R.id.editTextEmail).setVisibility(View.GONE);
            findViewById(R.id.editTextPassword).setVisibility(View.GONE);
            findViewById(R.id.signInBtn).setVisibility(View.GONE);
            findViewById(R.id.createBtn).setVisibility(View.GONE);

        } else {
            statusTextView.setText(R.string.signed_out);
            findViewById(R.id.editTextPassword).setVisibility(View.VISIBLE);
            findViewById(R.id.editTextEmail).setVisibility(View.VISIBLE);
            findViewById(R.id.signInBtn).setVisibility(View.VISIBLE);
            findViewById(R.id.createBtn).setVisibility(View.VISIBLE);
        }
        findViewById(R.id.signOutBtn).setVisibility(View.VISIBLE);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailEditText.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        String password = passwordTextEdit.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordTextEdit.setError("Required.");
            valid = false;
        } else {
            passwordTextEdit.setError(null);
        }
        return !valid;
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (validateForm()) {
            return;
        }
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success");
                FirebaseUser user = auth.getCurrentUser();
                updateUI(user);
                finish();
            } else {
                // If sign in fails, display a message to the user
                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                Toast.makeText(FireBaseAuth.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                updateUI(null);
            }
        });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (validateForm()) {
            return;
        }
        // [START sign_in_with_email]
        // [END sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(FireBaseAuth.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    // [START_EXCLUDE]
                    if (!task.isSuccessful()) {
                        statusTextView.setText(R.string.auth_failed);
                        finish();
                    }
                    // [END_EXCLUDE]
                });
    }
    private void signOut() {
        auth.signOut();
        updateUI(null);
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.createBtn) {
            createAccount(emailEditText.getText().toString(),
                    passwordTextEdit.getText().toString());
        } else if (i == R.id.signInBtn) {
            signIn(emailEditText.getText().toString(),
                    passwordTextEdit.getText().toString());
        } else if (i == R.id.signOutBtn){
            signOut();
        }
    }

    private void googleSignIn(View view) {
        Intent signInIntent = gcs.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                finish();
                getSupportFragmentManager().beginTransaction().add(R.id.homeContainer,
                        new HomeFragment())
                .commit();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Smth went wrong",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }
}