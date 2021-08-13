package com.example.sawariapatkalinsewa.Customerui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.entity.Feedback
import com.example.sawariapatkalinsewa.repository.FeedBackRepository
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedbackActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var etname:TextInputEditText
    private lateinit var etemail:TextInputEditText
    private lateinit var etmechanic:TextInputEditText
    private lateinit var etmessage:TextInputEditText
    private lateinit var btnsubmit:Button
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        etname=findViewById(R.id.etname)
        etemail=findViewById(R.id.etemail)
        etmechanic=findViewById(R.id.etmechanic)
        etmessage=findViewById(R.id.etmessage)
        btnsubmit=findViewById(R.id.btnsubmit)
        linearLayout=findViewById(R.id.linearLayout)
        val intent = intent.getParcelableExtra<Business>("feedback")

        if (intent != null) {
            etmechanic.setText(intent.mechusername)
        }
        etname.setText("${ServiceBuilder.username}")
        btnsubmit.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnsubmit ->{
                postfeedback()
            }

        }

    }

    private fun postfeedback() {
        val clusername=etname.text.toString()
        val clemail=etemail.text.toString()
        val mechusername=etmechanic.text.toString()
        val message=etmessage.text.toString()

        val feedback=Feedback(
                clusername=clusername,
                clemail=clemail,
                mechusername=mechusername,
                message=message,
        )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val feedBackRepository = FeedBackRepository()
                val response =  feedBackRepository.postfeedback(feedback)
                if(response.success == true){
                    withContext(Dispatchers.Main) {
                        val snack = Snackbar.make(linearLayout, "Feedback Added", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@FeedbackActivity,
                            ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}