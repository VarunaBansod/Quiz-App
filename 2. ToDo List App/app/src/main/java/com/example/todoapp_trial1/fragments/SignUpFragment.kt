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
import com.example.todoapp_trial1.databinding.FragmentSignUpBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.fragment_sign_up_) {
    //step1: made 3 var
    private lateinit var auth:FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sign_in, container, false) -->written in boilerplate
        //step 2: initialized binding var
        binding= FragmentSignUpBinding.inflate(inflater, container,false )
        return binding.root
    }
    //step3: made onViewCreated function
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view) //step4: inside init View is passed. we got View from onViewCreated method
        registerEvent() //step6: made another function
    }
    //step:5: made init function with parameter "View" of view type and initialized navController and auth var
    private fun init(view: View) {
        navControl= Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }
    //step7: defining registerEvent in this mthd setonclicklisterner made for singup button "Arrow img"
    private fun registerEvent(){
        //step12: adding another listener on signinTV so that when user will click on Singin they'll redirected ot login pg
        binding.signInTV.setOnClickListener {
            navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        binding.nextBtn.setOnClickListener{
            //step8: converting all the inputs to string type
            val email = binding.emailET.text.toString().trim() //trim() will trim extra white space
            val pass = binding.passET.text.toString().trim()
            val verifyPass = binding.rePassET.toString().trim()
            //step 9: writing conditions, when user can register
            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()){
                if(pass==verifyPass){
                    //step10:if condition match then we can call firebase and pass email and pass as args auth to register user
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener( //if this will get completed then completeListener will be called
                        OnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(context,"Woohoo!!...Registered Successfully", Toast.LENGTH_SHORT).show()
                                //step11: now adding navigation control, so go ot nav_graphn.xml and link signUp and Home Page
                                navControl.navigate(R.id.action_signUpFragment_to_homeFragment)
                            }else{
                                Toast.makeText(context, it.exception?.message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }
    }
}