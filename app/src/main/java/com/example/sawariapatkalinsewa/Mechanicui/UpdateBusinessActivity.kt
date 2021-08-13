package com.example.sawariapatkalinsewa.Mechanicui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.Business
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateBusinessActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etFullName: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var rdoMale: RadioButton
    private lateinit var rdoFemale: RadioButton
    private lateinit var rdoOthers: RadioButton
    private lateinit var btnUpdate: Button
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_business)

        etFullName = findViewById(R.id.etFullName)
        etAge = findViewById(R.id.etAge)
        rdoMale = findViewById(R.id.rdoMale)
        rdoFemale = findViewById(R.id.rdoFemale)
        rdoOthers = findViewById(R.id.rdoOthers)
        btnUpdate = findViewById(R.id.btnUpdate)
        linearLayout=findViewById(R.id.linearLayout)

        val intent = intent.getParcelableExtra<Business>("business")
        if(intent != null){
            etFullName.setText(intent.fullname)
            etAge.setText(intent.phone.toString())
            if (intent.gender == "Two Wheelers"){
                rdoMale.isChecked = true
            }
            else if(intent.gender == "Four Wheelers"){
                rdoFemale.isChecked = true
            }
            else{
                rdoOthers.isChecked = true
            }
        }

        btnUpdate.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnUpdate ->{
                updateBusiness()
            }
        }
    }

    private fun updateBusiness() {
        val intent = intent.getParcelableExtra<Business>("business")
        val fullName = etFullName.text.toString()
        val phone = etAge.text.toString()
        var gender = ""
        when {
            rdoFemale.isChecked -> {
                gender = "Four Wheelers"
            }
            rdoMale.isChecked -> {
                gender = "Two Wheelers"
            }
            rdoOthers.isChecked -> {
                gender = "Both"
            }
        }
        val business = Business(fullname = fullName, phone = phone, gender = gender)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val businessRepository = BusinessRepository()
                val response = businessRepository.updateBusiness(intent?._id!!,business)
                if(response.success == true){
                    withContext(Dispatchers.Main) {
                        val snack = Snackbar.make(linearLayout, "Update SuccessFull", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@UpdateBusinessActivity,
                        ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}