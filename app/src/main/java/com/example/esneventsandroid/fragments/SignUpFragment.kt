package com.example.esneventsandroid.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.esneventsandroid.MainActivity
import com.example.esneventsandroid.R
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        auth = FirebaseAuth.getInstance()

        val emailEditText = view.findViewById<EditText>(R.id.email_sign_up)
        val passwordEditText = view.findViewById<EditText>(R.id.password_sign_up)
        val signUpButton = view.findViewById<Button>(R.id.button_sign_up)
        val switchToLoginPage= view.findViewById<TextView>(R.id.button_login_sign_up)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(activity, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(activity, "Registration successful!", Toast.LENGTH_SHORT).show()
                            (activity as? MainActivity)?.replaceFragment(EventsFragment())

                        } else {
                            Toast.makeText(activity, "Registration failed. Please try again later.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        switchToLoginPage.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(LogInFragment())
        }

        return view
    }
}