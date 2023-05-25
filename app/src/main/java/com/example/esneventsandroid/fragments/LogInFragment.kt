package com.example.esneventsandroid.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.esneventsandroid.MainActivity
import com.example.esneventsandroid.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LogInFragment : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val editTextEmail: EditText = view.findViewById(R.id.email_log_in)
        val editTextPassword: EditText = view.findViewById(R.id.password_log_in)
        val buttonSignIn: Button = view.findViewById(R.id.button_log_in)
        val buttonSwitchToSignUp: Button = view.findViewById(R.id.button_log_in_sign_up)
        val buttonGoogleSignIn: Button = view.findViewById(R.id.button_log_in_google)

        // Set click listener for sign in button
        buttonSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            signIn(email, password)
        }

        // Set click listener for switch to sign up button
        buttonSwitchToSignUp.setOnClickListener {
            // Replace the current fragment with the sign up fragment
            (activity as? MainActivity)?.replaceFragment(SignUpFragment())
        }

        // Set click listener for Google sign in button
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

        buttonGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        return view
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    Toast.makeText(activity, "Login successful!", Toast.LENGTH_SHORT).show()
                    (activity as? MainActivity)?.replaceFragment(EventsFragment())
                } else {
                    Toast.makeText(activity, "Login failed. Check your credentials again!.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    (activity as? MainActivity)?.replaceFragment(EventsFragment())
                } else {
                    (activity as? MainActivity)?.replaceFragment(EventsFragment())
                    Toast.makeText(requireContext(), "Authentication Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                (activity as? MainActivity)?.replaceFragment(EventsFragment())
                //Toast.makeText(requireContext(), "Google sign in failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}