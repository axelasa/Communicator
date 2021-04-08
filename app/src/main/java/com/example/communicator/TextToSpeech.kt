package com.example.communicator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.*

class TextToSpeech : AppCompatActivity(),TextToSpeech.OnInitListener {

    lateinit var textToSpeech: TextToSpeech
    lateinit var speak:Button
    lateinit var type:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        speak = findViewById(R.id.speech)
        type = findViewById(R.id.edtText)
        textToSpeech = TextToSpeech(this,this)

        speak.setOnClickListener {
            readText()
        }

    }

    override fun onInit(status:Int)
    {
       if (status == TextToSpeech.SUCCESS)
       {
           val res: Int = textToSpeech.setLanguage(Locale.UK)

           if (res==TextToSpeech.LANG_MISSING_DATA||res==TextToSpeech.LANG_NOT_SUPPORTED)
           {
               Toast.makeText(this@TextToSpeech, "Sorry This Language Is Not Supported Yet...", Toast.LENGTH_LONG).show()
           }
           else
           {
             speak.isEnabled=true
             readText()
           }
       }
        else
        {
            Toast.makeText(this@TextToSpeech, "Failed To Initialize", Toast.LENGTH_LONG).show()
        }
    }


    private fun readText()
    {

        val strTxt = type.text.toString()
        textToSpeech.speak(strTxt,TextToSpeech.QUEUE_FLUSH,null)
        Toast.makeText(applicationContext, strTxt, Toast.LENGTH_SHORT).show()

    }

    override fun onDestroy() {

        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onDestroy()
    }
}