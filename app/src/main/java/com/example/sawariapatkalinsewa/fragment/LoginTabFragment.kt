package com.example.sawariapatkalinsewa.fragment

import android.Manifest
import android.app.KeyguardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.sawariapatkalinsewa.Customerui.DashBoardActivity
import com.example.sawariapatkalinsewa.FingerprintHandler
import com.example.sawariapatkalinsewa.Mechanicui.MechDashboardActivity
import com.example.sawariapatkalinsewa.R

import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.MechanicRepository
import com.google.android.material.snackbar.Snackbar

import com.google.android.material.textfield.TextInputEditText
import com.wajahatkarim3.easyvalidation.core.collection_ktx.nonEmptyList
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.xml.validation.Validator

class LoginTabFragment : Fragment() {
    lateinit var cluser:TextInputEditText;
    lateinit var clpass:TextInputEditText;
    lateinit var forgetpass:TextView;
    lateinit var login:Button;
    lateinit var finger:ImageView
    lateinit var rdocustomer:RadioButton;
    lateinit var rdomechanic:RadioButton;
    private var fingerprintManager: FingerprintManager? = null
    private var keyguardManager: KeyguardManager? = null
    private var keyStore: KeyStore? = null
    private var keyGenerator: KeyGenerator? = null
    private val KEY_NAME = "example_key"
    private var cipher: Cipher? = null
    private var cryptoObject: FingerprintManager.CryptoObject? = null
    var type=""


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_tab_fragment, container, false)

        cluser=view.findViewById(R.id.email)
        clpass=view.findViewById(R.id.password)
        forgetpass=view.findViewById(R.id.Forgetpassword)
        login=view.findViewById(R.id.buttonlogin)

        rdocustomer=view.findViewById(R.id.rdocustomer)
        rdomechanic=view.findViewById(R.id.rdomechanic)
        finger=view.findViewById(R.id.finger)



       login.setOnClickListener(object : View.OnClickListener {

           override fun onClick(view: View) {
               when {
                   rdocustomer.isChecked -> {
                       type="Customer"
                       login()
                   }
                   rdomechanic.isChecked -> {
                       type="Mechanic"
                       mechlogin()
                   }

               }



           }
       })
        finger.setOnClickListener{
            Toast.makeText(context, "Click on fingerprint sensor of your mobile", Toast.LENGTH_SHORT).show()
            if (getManagers()) {
                generateKey()

                if (cipherInit()) {
                    cipher?.let {
                        cryptoObject = FingerprintManager.CryptoObject(it)
                    }
                }
            }
            if (cipherInit()) {

                cipher?.let {
                    cryptoObject = FingerprintManager.CryptoObject(it)
                }

                val helper = context?.let { FingerprintHandler(it) }

                if (fingerprintManager != null && cryptoObject != null) {
                    if (helper != null) {
                        helper.startAuth(fingerprintManager!!, cryptoObject!!)
                    }
                }
            }

        }


        return view;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getManagers(): Boolean {
        keyguardManager = context?.getSystemService(AppCompatActivity.KEYGUARD_SERVICE)
                as KeyguardManager
        fingerprintManager = requireContext().getSystemService(AppCompatActivity.FINGERPRINT_SERVICE)
                as FingerprintManager

        if (keyguardManager?.isKeyguardSecure == false) {

            Toast.makeText(context,
                "Lock screen security not enabled in Settings",
                Toast.LENGTH_LONG).show()
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.USE_FINGERPRINT) !=
            PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,
                "Fingerprint authentication permission not enabled",
                Toast.LENGTH_LONG).show()

            return false
        }

        if (fingerprintManager?.hasEnrolledFingerprints() == false) {
            Toast.makeText(context,
                "Register at least one fingerprint in Settings",
                Toast.LENGTH_LONG).show()
            return false
        }
        return true


    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun cipherInit(): Boolean {
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }

        try {
            keyStore?.load(null)
            val key = keyStore?.getKey(KEY_NAME, null) as SecretKey
            cipher?.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(
                "Failed to get KeyGenerator instance", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }

        try {
            keyStore?.load(null)
            keyGenerator?.init(
                KeyGenParameterSpec.Builder(KEY_NAME,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(
                    KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build())
            keyGenerator?.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }
    private fun login() {
        val clusername=cluser.text.toString()
        val clpassword= clpass.text.toString()
if (clusername.isEmpty()){
    cluser.error= "Username Required"
}
        if (clpassword.isEmpty()){
            clpass.error= "Password Required"
        }
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                val repository = CustomerRepository()
                val response = repository.loginUser(clusername,clpassword)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.username ="${response.data}"
                    loginSharedPref()
                    withContext(Dispatchers.Main) {
                        val snack = view?.let { Snackbar.make(it, "Login Succesfull${response.data}", Snackbar.LENGTH_LONG) }
                        if (snack != null) {
                            snack.show()
                        }
                    }
                    startActivity(
                        Intent(
                            context,
                            DashBoardActivity::class.java
                        )
                    )

                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Invalid Login", Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    "Login error", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

        }

        }
    private fun mechlogin() {
        val mechusername=cluser.text.toString()
        val mechpassword= clpass.text.toString()
        if (mechusername.isEmpty()){
            cluser.error= "Username Required"
        }
        if (mechpassword.isEmpty()){
            clpass.error= "Password Required"
        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = MechanicRepository()
                val response = repository.loginMech(mechusername, mechpassword)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    ServiceBuilder.username ="${response.data}"
                    loginSharedPref()
                    withContext(Dispatchers.Main) {
                        val snack = view?.let { Snackbar.make(it, "Login Succesfull${response.data}", Snackbar.LENGTH_LONG) }
                        if (snack != null) {
                            snack.show()
                        }
                    }
                    startActivity(
                            Intent(context,
                                    MechDashboardActivity::class.java
                            )

                    )


                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                context,
                                "Invalid Login", Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            context,
                            "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }

    }
  private fun loginSharedPref() {
        val username = cluser.text.toString()
        val password = clpass.text.toString()
        val loginSharedPref = activity?.getSharedPreferences("LoginPref", AppCompatActivity.MODE_PRIVATE)
        val editor = loginSharedPref?.edit()
      if (editor != null) {
          editor.putString("username", username)
      }
      if (editor != null) {
          editor.putString("password", password)
      }
      if (editor != null) {
          editor.putString("type", type)
      }
      if (editor != null) {
          editor.commit()
      }
    }

    }



