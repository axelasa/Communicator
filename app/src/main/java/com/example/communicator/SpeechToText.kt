package com.example.communicator

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class SpeechToText : AppCompatActivity() {
    private  val REQUEST_CODE_SPEECH_INPUT = 100
    lateinit var microphone:FloatingActionButton
    lateinit var resDisplay:TextView
    lateinit var next:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speech_to_text)

        microphone = findViewById(R.id.mic)

        resDisplay = findViewById(R.id.speechDisplay)

        next = findViewById(R.id.proceed)

        next.setOnClickListener {
           val intent = Intent(this@SpeechToText,TextToSpeech::class.java)
            startActivity(intent)
        }

        microphone.setOnClickListener {
            speech()

        }
    }

    private fun speech(){
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something....")

       try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT)
        }
       catch (exp:ActivityNotFoundException)
       {
           Toast.makeText(this@SpeechToText, "Sorry Speech Not Supported....", Toast.LENGTH_LONG).show()
       }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_SPEECH_INPUT)
        {
            if (resultCode == RESULT_OK || null!= data)
            {
                val res : ArrayList<String> = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                resDisplay.text = res[0]
            }
        }

    }

}