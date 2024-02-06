package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.Dataset
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModelList:MutableList<QuizModel>
    lateinit var adapter:QuizListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }
    private fun setupRecyclerView(){
        binding.progrssBar.visibility=View.GONE
        adapter= QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        binding.recyclerView.adapter =adapter
    }
    private fun getDataFromFirebase() {
        binding.progrssBar.visibility=View.VISIBLE
//        FirebaseDatabase.getInstance().reference
//            .get()
//            .addOnSuccessListener { dataSnapshot ->
//                if (dataSnapshot.exists()) {
//                    for (snapshot in dataSnapshot.children) {
//                        val quizModel = snapshot.getValue(QuizModel::class.java)
//                        if (quizModel != null) {
//                            quizModelList. add(quizModel)
//                        }
//                    }
//                }
//                setupRecyclerView()
//        dummy data
        val listQuestionModel= mutableListOf<QuestionModel>()
        listQuestionModel.add(QuestionModel("What is android?", mutableListOf("Language","OS","Product","None"),"OS"))
        listQuestionModel.add(QuestionModel("Who own android", mutableListOf("Apple","Google","Samsung","Microsoft"),"Apple"))
        listQuestionModel.add(QuestionModel("Which assistant android uses?", mutableListOf("Siri","Cortana","Google Assistant","Alex"),"Google Assistant"))
        quizModelList.add(QuizModel("1","Programming","All the programming basics","10",listQuestionModel))
//        quizModelList.add(QuizModel("2","math","All the programming basics","20"))
            }
    }














































/* dummy data
        val listQuestionModel= mutableListOf<QuestionModel>()
        listQuestionModel.add(QuestionModel("What is android?", mutableListOf("Language","OS","Product","None"),"OS"))
        listQuestionModel.add(QuestionModel("Who own android", mutableListOf("Apple","Google","Samsung","Microsoft"),"Apple"))
        listQuestionModel.add(QuestionModel("Which assistant android uses?", mutableListOf("Siri","Cortana","Google Assistant","Alex"),"Google Assistant"))
        quizModelList.add(QuizModel("1","Programming","All the programming basics","10",listQuestionModel))
        quizModelList.add(QuizModel("2","math","All the programming basics","20")) */