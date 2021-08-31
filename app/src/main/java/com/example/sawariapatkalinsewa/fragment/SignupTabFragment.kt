package com.example.sawariapatkalinsewa.fragment

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.Mechanic
import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.wajahatkarim3.easyvalidation.core.view_ktx.textEqualTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class   SignupTabFragment: Fragment() {

    //declare variables
    //mechanic consits this
    val type= arrayOf("Two whelers","Four Whelers")
    lateinit var rbcustomer:RadioButton
    lateinit var rbmechanic:RadioButton
    lateinit var spinner: Spinner
    lateinit var mechaddress:TextInputLayout
    lateinit var mechcontact:TextInputEditText
    lateinit var mechcitizenship:TextInputEditText
    lateinit var mechshop:TextInputEditText
    lateinit var mechpan:TextInputEditText
    lateinit var radioGroup: RadioGroup

    //both consumer and mechanic consits this
    lateinit var clfname:TextInputEditText
    lateinit var cllname:TextInputEditText
    lateinit var clemail:TextInputEditText
    lateinit var clusername:TextInputEditText
    lateinit var clpass:TextInputEditText
    lateinit var confirmpassword:TextInputEditText
    lateinit var btnSignup:Button
    var sel :String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate layout
        val view = inflater.inflate(R.layout.signup_tab_fragment, container, false)

        //fetch id to variables
        rbcustomer = view.findViewById(R.id.rbcustomer)
        rbmechanic = view.findViewById(R.id.rbmechanic)
        spinner = view.findViewById(R.id.spinnermech)
        mechaddress = view.findViewById(R.id.mechaddress)
        mechcontact = view.findViewById(R.id.mechcontact)
        mechcitizenship = view.findViewById(R.id.mechcitizenship)
        mechshop = view.findViewById(R.id.mechshop)
        mechpan = view.findViewById(R.id.mechpannum)
        radioGroup=view.findViewById(R.id.rg1)
        clfname=view.findViewById(R.id.clfname)
        cllname=view.findViewById(R.id.cllname)
        clemail=view.findViewById(R.id.clemail)
        clusername=view.findViewById(R.id.clusername)
        clpass=view.findViewById(R.id.clpassword)
        confirmpassword=view.findViewById(R.id.cpassword)
        btnSignup=view.findViewById(R.id.btnSignup)


        //set visibility to gone
        spinner.visibility=View.GONE
        mechaddress.visibility=View.GONE
        mechcontact.visibility=View.GONE
        mechcitizenship.visibility=View.GONE
        mechshop.visibility=View.GONE
        mechpan.visibility=View.GONE

        clemail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(clemail.text.toString()).matches()){
                    btnSignup.isEnabled=true
                }
                else{
                    btnSignup.isEnabled=false
                    clemail.setError("Invalid Email")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        mechcontact.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if (mobileValidate(mechcontact.text.toString()))
               {
                   btnSignup.isEnabled=true
               }
               else{
                   btnSignup.isEnabled=false
                   mechcontact.setError("Invalid Phone")
               }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })




        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rbcustomer -> {
                        spinner.visibility = View.GONE
                        mechaddress.visibility = View.GONE
                        mechcontact.visibility = View.GONE
                        mechcitizenship.visibility = View.GONE
                        mechshop.visibility = View.GONE
                        mechpan.visibility = View.GONE
                        sel = "Customer"

                    }
                    R.id.rbmechanic -> {
                        spinner.isVisible = true
                        mechaddress.isVisible = true
                        mechcontact.isVisible = true
                        mechcitizenship.isVisible = true
                        mechshop.isVisible = true
                        mechpan.isVisible = true
                        val adapter = activity?.let { ArrayAdapter(it.applicationContext, android.R.layout.simple_list_item_1, type) }
                        spinner.adapter = adapter
                        sel = "Mechanic"
                    }
                }
            }

        })

        btnSignup.setOnClickListener {
           if (sel=="Customer"){
               val clfnme=clfname.text.toString()
               val clname=cllname.text.toString()
               val clemail=clemail.text.toString()
               val clusernme=clusername.text.toString()
               val clpassword=clpass.text.toString()
               val confpassword=confirmpassword.text.toString()
if (clfnme.isEmpty()){
    clfname.error= "Firstname required"
    return@setOnClickListener
}
else if (!clfname.text.toString().matches("^[a-zA-Z]*$".toRegex())|| clfnme.isDigitsOnly()){
    clfname.error= "Invalid Only alphabets"
    return@setOnClickListener

               }
            else   if (clname.isEmpty()){
                   cllname.error= "Lastname required"
                   return@setOnClickListener
               }
               else if (!cllname.text.toString().matches("^[a-zA-Z]*$".toRegex())|| clfnme.isDigitsOnly()){
                   cllname.error= "Invalid Only alphabets"
                   return@setOnClickListener

               }
else   if (clusernme.isEmpty()){
    clusername.error= "Username required"
    return@setOnClickListener
}
else if (clfnme.isDigitsOnly()){
    clusername.error= "Invalid only Digits not acceptable"
    return@setOnClickListener
}    else   if (clpassword.isEmpty()||confpassword.isEmpty()){
    clpass.error= "Password required"
    return@setOnClickListener
}
              else if (clpassword!=confpassword){
                   clpass.error = "Password doesnot match"
                   clpass.requestFocus()
                   return@setOnClickListener
               }
               else{
                   val client=client(clfname=clfnme,cllname = clname,clemail = clemail,clusername = clusernme,clpassword = clpassword)

                       CoroutineScope(Dispatchers.IO).launch {
                           try {
                           val customerRepository=CustomerRepository()
                           val response=customerRepository.registerCustomer(client)
                           if (response.success==true){
                               withContext(Dispatchers.Main) {
//                                   showHighPriorityNotification()
                                   val snack = Snackbar.make(view, "Registration Succesfull go to Login tab", Snackbar.LENGTH_LONG)
                                   snack.show()
                               }
                           }
                       }
                           catch (ex: Exception) {
                               withContext(Dispatchers.Main) {
//                                   showHighPriorityNotification()
                                   val snack = Snackbar.make(view, "Registration Succesfull go to Login tab", Snackbar.LENGTH_LONG)
                                   snack.show()
                               }

                           }
                   }

               }
           }

        else if (sel=="Mechanic"){
                val mechfname = clfname.text.toString()
                val mechlname = cllname.text.toString()
                val mechemail = clemail.text.toString()
                val mechusername = clusername.text.toString()
                val mechvechtype =spinner.selectedItem.toString()
                val mechaddres= mechaddress.editText?.text.toString()
                val mechPhone= mechcontact.text.toString()
                val mechcitizen=mechcitizenship.text.toString()
                val mechworkplace=mechshop.text.toString()
                val mechPANnum=mechpan.text.toString()
                val mechpassword = clpass.text.toString()
                val mechpass = confirmpassword.text.toString()
               if (mechfname.isEmpty()){
                   clfname.error= "Firstname required"
                   return@setOnClickListener
               }
               else if (!clfname.text.toString().matches("^[a-zA-Z]*$".toRegex())|| mechfname.isDigitsOnly()){
                   clfname.error= "Invalid Only alphabets"
                   return@setOnClickListener

               }
               else   if (mechlname.isEmpty()){
                   cllname.error= "Lastname required"
                   return@setOnClickListener
               }
               else if (!cllname.text.toString().matches("^[a-zA-Z]*$".toRegex())|| mechlname.isDigitsOnly()){
                   cllname.error= "Invalid Only alphabets"
                   return@setOnClickListener

               }
               else   if (mechusername.isEmpty()){
                   clusername.error= "Username required"
                   return@setOnClickListener
               }
               else if (mechusername.isDigitsOnly()){
                   clusername.error= "Invalid only Digits not acceptable"
                   return@setOnClickListener
               }
               else   if (mechaddres.isEmpty()){
                   mechaddress.error= "Address required"
                   return@setOnClickListener
               }
               else if (!mechaddress.editText?.text.toString().matches("^[a-zA-Z]*$".toRegex())|| mechaddres.isDigitsOnly()){
                   mechaddress.error= "Invalid Only alphabets"
                   return@setOnClickListener

               }
               else   if (mechPhone.isEmpty()){
                   mechcontact.error= "Contact required"
                   return@setOnClickListener
               }
               else   if (mechworkplace.isEmpty()){
                   mechshop.error= "ShopName required"
                   return@setOnClickListener
               }
               else if (!mechshop.text.toString().matches("^[a-zA-Z]*$".toRegex())|| mechaddres.isDigitsOnly()){
                   mechshop.error= "Invalid Only alphabets"
                   return@setOnClickListener

               }
               else   if (mechPANnum.isEmpty()){
                   mechpan.error= "Address required"
                   return@setOnClickListener
               }
               else if (!mechPANnum.isDigitsOnly()){
                   mechpan.error= "Invalid Only digits"
                   return@setOnClickListener

               }
               else   if (mechcitizen.isEmpty()){
                   mechcitizenship.error= "CitizenShip required"
                   return@setOnClickListener
               }
               else if (!mechcitizen.isDigitsOnly()){
                   mechcitizenship.error= "Invalid Only digits"
                   return@setOnClickListener

               }
               else   if (mechpassword.isEmpty()||mechpass.isEmpty()){
                   clpass.error= "Password required"
                   return@setOnClickListener
               }

                if (mechpassword != mechpass) {
                    clpass.error = "Password doesnot match"
                    clpass.requestFocus()
                    return@setOnClickListener
                } else {
                    val mechanic =
                            Mechanic(mechfname = mechfname, mechlname = mechlname, mechemail = mechemail, mechusername = mechusername,
                                    mechvechtype=mechvechtype,mechaddress=mechaddres,mechPhone=mechPhone,mechcitizenship=mechcitizen,
                                    mechworkplace=mechworkplace,mechPANnum=mechPANnum,
                                    mechpassword = mechpassword)

                    try {
                        CoroutineScope(Dispatchers.IO).launch {
                            val mechanicRepository = MechanicRepository()
                            val response = mechanicRepository.registerMechanic(mechanic)
                            if (response.success == true) {

                                withContext(Dispatchers.Main) {
//                                    showHighPriorityNotification()
                                    val snack = Snackbar.make(view, "Registration Succesfull go to Login tab", Snackbar.LENGTH_LONG)
                                    snack.show()
                                }
                            }
                        }
                    } catch (ex: Exception) {

                        val snack = Snackbar.make(view, "Registration Unsucessfull", Snackbar.LENGTH_LONG)
                        snack.show()

                    }
                }
            }
            else{
                val snack = Snackbar.make(view, "Select correct type of customer", Snackbar.LENGTH_LONG)
                snack.show()
            }


        }
            return view;

    }

    private fun mobileValidate(text: String): Boolean {
 val p=Pattern.compile("[0-9][0-9]{9}")
        var m =p.matcher(text)
        return m.matches()
    }

//    private fun showHighPriorityNotification() {
//        val notificationManager= context?.let { NotificationManagerCompat.from(it) }
//
//        val notificationChannels= context?.let { NotificationChannels(it) }
//        if (notificationChannels != null) {
//            notificationChannels.createNotificationChannels()
//        }
//
//        val notification= context?.let {
//            notificationChannels?.let { it1 ->
//                NotificationCompat.Builder(it, it1.CHANNEL_1)
//                    .setSmallIcon(R.drawable.notification)
//                    .setContentTitle("Account")
//                    .setContentText(" $sel Account created  now you can login")
//                    .setColor(Color.BLACK)
//                    .build()
//            }
//        }
//
//        if (notification != null) {
//            if (notificationManager != null) {
//                notificationManager.notify(1,notification)
//            }
//        }
//    }

}
