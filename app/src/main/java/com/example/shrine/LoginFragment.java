package com.example.shrine;


import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Executor;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_login_fragment, container, false);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);

        MaterialButton nextButton = view.findViewById(R.id.next_button);

        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(view1 -> {
            if (!isPasswordValid(passwordEditText.getText())) {
                passwordTextInput.setError(getString(R.string.shr_error_password));
            } else {
                passwordTextInput.setError(null); // Clear the error
                ((NavigationHost) requireActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener((view12, i, keyEvent) -> {
            if (isPasswordValid(passwordEditText.getText())) {
                passwordTextInput.setError(null); //Clear the error
            }
            return false;
        });
        return view;
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FloatingActionButton fab = view.findViewById(R.id.fab);
        Executor executor;
        BiometricPrompt biometricPrompt;
        BiometricPrompt.PromptInfo promptInfo;

        executor = ContextCompat.getMainExecutor(requireContext());
        biometricPrompt = new BiometricPrompt(LoginFragment.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(requireContext(), "Authentication error : "+errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(requireContext(), "Authentication Succeeded", Toast.LENGTH_SHORT).show();
                ((NavigationHost) requireActivity()).navigateTo(new ProductGridFragment(), false);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Unlock Shrine using Biometrics")
                .setNegativeButtonText("Use account password")
                .build();

        fab.setOnClickListener(view1 -> biometricPrompt.authenticate(promptInfo));
    }
}