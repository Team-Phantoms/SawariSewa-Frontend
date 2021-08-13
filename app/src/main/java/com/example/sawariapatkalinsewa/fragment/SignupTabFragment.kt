package com.example.sawariapatkalinsewa.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.sawariapatkalinsewa.channel.NotificationChannels
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.entity.Mechanic
import com.example.sawariapatkalinsewa.entity.client
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log

@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class   SignupTabFragment: Fragment() {

    //declare variables
    //mechanic consits this
    val type= arrayOf("Two whelers","Four Whelers")
    lateinit var rbcustomer:RadioButton
    lateinit var rbmechanic:RadioButton
    lateinit var spinner: Spinner
    lateinit var mechaddress:TextInputLayout
    lateinit var mechcontact:TextInputLayout
    lateinit var mechcitizenship:TextInputLayout
    lateinit var mechshop:TextInputLayout
    lateinit var mechpan:TextInputLayout
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
               val clfname=clfname.text.toString()
               val cllname=cllname.text.toString()
               val clemail=clemail.text.toString()
               val clusername=clusername.text.toString()
               val clpassword=clpass.text.toString()
               val confpassword=confirmpassword.text.toString()

               if (clpassword!=confpassword){
                   clpass.error = "Password doesnot match"
                   clpass.requestFocus()
                   return@setOnClickListener
               }
               else{
                   val client=client(clfname=clfname,cllname = cllname,clemail = clemail,clusername = clusername,clpassword = clpassword)

                       CoroutineScope(Dispatchers.IO).launch {
                           try {
                           val customerRepository=CustomerRepository()
                           val response=customerRepository.registerCustomer(client)
                           if (response.success==true){
                               withContext(Dispatchers.Main) {
                                   showHighPriorityNotification()
                                   val snack = Snackbar.make(view, "Registration Succesfull go to Login tab", Snackbar.LENGTH_LONG)
                                   snack.show()
                               }
                           }
                       }
                           catch (ex: Exception) {
                               withContext(Dispatchers.Main) {
                                   showHighPriorityNotification()
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
                val mechaddress= mechaddress.editText?.text.toString()
                val mechPhone= mechcontact.editText?.text.toString()
                val mechcitizenship=mechcitizenship.editText?.text.toString()
                val mechworkplace=mechshop.editText?.text.toString()
                val mechPANnum=mechpan.editText?.text.toString()
                val mechpassword = clpass.text.toString()
                val mechpass = confirmpassword.text.toString()

                if (mechpassword != mechpass) {
                    clpass.error = "Password doesnot match"
                    clpass.requestFocus()
                    return@setOnClickListener
                } else {
                    val mechanic =
                            Mechanic(mechfname = mechfname, mechlname = mechlname, mechemail = mechemail, mechusername = mechusername,
                                    mechvechtype=mechvechtype,mechaddress=mechaddress,mechPhone=mechPhone,mechcitizenship=mechcitizenship,
                                    mechworkplace=mechworkplace,mechPANnum=mechPANnum,
                                    mechpassword = mechpassword)

                    try {
                        CoroutineScope(Dispatchers.IO).launch {
                            val mechanicRepository = MechanicRepository()
                            val response = mechanicRepository.registerMechanic(mechanic)
                            if (response.success == true) {

                                withContext(Dispatchers.Main) {
                                    showHighPriorityNotification()
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

    private fun showHighPriorityNotification() {
        val notificationManager= context?.let { NotificationManagerCompat.from(it) }

        val notificationChannels= context?.let { NotificationChannels(it) }
        if (notificationChannels != null) {
            notificationChannels.createNotificationChannels()
        }

        val notification= context?.let {
            notificationChannels?.let { it1 ->
                NotificationCompat.Builder(it, it1.CHANNEL_1)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle("Account")
                    .setContentText(" $sel Account created  now you can login")
                    .setColor(Color.BLACK)
                    .build()
            }
        }

        if (notification != null) {
            if (notificationManager != null) {
                notificationManager.notify(1,notification)
            }
        }
    }

}
