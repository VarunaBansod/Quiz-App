package com.example.todoapp_trial1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todoapp_trial1.R
import com.example.todoapp_trial1.databinding.FragmentSignInBinding
import com.example.todoapp_trial1.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view) //step4: inside init View is passed. we got View from onViewCreated method
        registerEvent() //step6: made another function
    }

    private fun registerEvent() {
        //step12: adding another listener on signinTV so that when user will click on Singin they'll redirected ot login pg
        binding.SignUpTv.setOnClickListener {
            navControl.navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.nextBtn.setOnClickListener {
            //step8: converting all the inputs to string type
            val email = binding.emailET.text.toString().trim() //trim() will trim extra white space
            val pass = binding.passET.text.toString().trim()
            //step 9: writing conditions, when user can register
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (email.isNotEmpty() && pass.isNotEmpty()) {
                    loginUser(email, pass) //if email and pass entered lagin user () will be called
                } else {
                    Toast.makeText(context, "Please enter your details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful)
                navControl.navigate(R.id.action_signInFragment_to_homeFragment2)
            else
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun init(view: View) {
        navControl =Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }
}
