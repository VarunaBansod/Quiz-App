package com.example.quizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.quizapp.databinding.ActivityQuizBinding
import com.example.quizapp.databinding.ScoreDialogBinding

class QuizActivity : AppCompatActivity(),View.OnClickListener {

    companion object{
        var questionModelList : List<QuestionModel> =listOf()
        var time: String =""
    }

    lateinit var binding: ActivityQuizBinding
    var currentQuestionIndex=0;
    var selectedAnswer =""
    var score =0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnA.setOnClickListener(this@QuizActivity)
            btnB.setOnClickListener(this@QuizActivity)
            btnC.setOnClickListener(this@QuizActivity)
            btnD.setOnClickListener(this@QuizActivity)
            nextBtn.setOnClickListener(this@QuizActivity)
        }
        loadQuestions()
        startTimer()
    }

    private fun startTimer() {
        val totalTimeInMillis = time.toInt()*60*1000L
        object : CountDownTimer(totalTimeInMillis, 1000L){
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished/1000
                val minutes = seconds/60
                val remainingSeconds = seconds %60
                binding.timerIndicatorTextView.text = String.format("%02d:%02d", minutes,remainingSeconds)
            }

            override fun onFinish() {
                //Finish the quiz
            }
        }.start()
    }

    private fun loadQuestions(){
        selectedAnswer=""
        if(currentQuestionIndex == questionModelList.size){
            finishQuiz()
            return
        }

        binding.apply {
            questionIndicatorTextView.text ="Question ${currentQuestionIndex +1}/${questionModelList.size}"
            questionProgressIndicator.progress =
                ( currentQuestionIndex.toFloat() / questionModelList.size.toFloat() * 100).toInt()
            questionTextView.text = questionModelList[currentQuestionIndex].question
            btnA.text = questionModelList[currentQuestionIndex].options[0]
            btnB.text = questionModelList[currentQuestionIndex].options[1]
            btnC.text = questionModelList[currentQuestionIndex].options[2]
            btnD.text = questionModelList[currentQuestionIndex].options[3]
        }
    }

    override fun onClick(view: View?) {
        binding.apply {
            btnA.setBackgroundColor(getColor(R.color.coral_pink))
            btnB.setBackgroundColor(getColor(R.color.coral_pink))
            btnC.setBackgroundColor(getColor(R.color.coral_pink))
            btnD.setBackgroundColor(getColor(R.color.coral_pink))
        }
            val clickedBtn = view as Button
            if(clickedBtn.id==R.id.nextBtn){
                //next button is clicked
                if(selectedAnswer== questionModelList[currentQuestionIndex].correct){
                    score++
                    Log.i("Score of quiz",score.toString())
                }
                currentQuestionIndex++
                loadQuestions()
            }else{
                //options button is clicked
                selectedAnswer = clickedBtn.text.toString()
                clickedBtn.setBackgroundColor(getColor(R.color.mint_green))
            }
    }
    private fun finishQuiz(){
        val totalQuestions = questionModelList.size
        val percentage =((score.toFloat()/totalQuestions.toFloat()) *100).toInt()
        val dialogBinding = ScoreDialogBinding.inflate((layoutInflater))
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text ="$percentage %"
            if(percentage>60){
                scoreTitle.text ="Congrats! You have passed"
                scoreTitle.setTextColor(Color.parseColor("#FF228B22"))
                scoreProgressIndicator.setIndicatorColor(Color.parseColor("#FF228B22"))
            }else{
                scoreTitle.text ="OOPs! You have failed"
                scoreTitle.setTextColor(Color.RED)
                scoreProgressIndicator.setIndicatorColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener{
                finish()
            }
        }
        AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
    }
}

