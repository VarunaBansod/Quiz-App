package com.example.todoapp_trial1.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.todoapp_trial1.R
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment(R.layout.fragment_splash) {

    //creating firebaseAuth to check if user is logged in ot not
    private lateinit var auth:FirebaseAuth
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        val isLogin: Boolean= auth.currentUser !=null
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
                if(auth.currentUser!=null){
                    navController.navigate(R.id.action_splashFragment_to_homeFragment)
                }else{
                    navController.navigate(R.id.action_splashFragment_to_signInFragment)
                }
        },2000)
    }
    private fun init(view: View){
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)
    }
}