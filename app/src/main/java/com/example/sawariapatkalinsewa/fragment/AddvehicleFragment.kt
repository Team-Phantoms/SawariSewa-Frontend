package com.example.sawariapatkalinsewa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.entity.Vehicle
import com.example.sawariapatkalinsewa.repository.BusinessRepository
import com.example.sawariapatkalinsewa.repository.VehicleRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddvehicleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddvehicleFragment : Fragment(),View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var vusername: TextInputEditText
    private lateinit var vechbrand: TextInputEditText
    private lateinit var vechmodel: TextInputEditText
    private lateinit var platenum: TextInputEditText
    private lateinit var btnaddvehicle: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
         val view=inflater.inflate(R.layout.fragment_addvehicle, container, false)

        vusername=view.findViewById(R.id.vusername)
        vechbrand=view.findViewById(R.id.vechbrand)
        vechmodel=view.findViewById(R.id.vechmodel)
        platenum=view.findViewById(R.id.platenum)
        btnaddvehicle=view.findViewById(R.id.btnaddvehicle)

        vusername.setText("${ServiceBuilder.username}")


        btnaddvehicle.setOnClickListener(this)
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddvehicleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AddvehicleFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnaddvehicle -> {
                savevehicle()
            }

        }
    }

    private fun savevehicle() {
        val clusername=vusername.text.toString()
        val vechbrand = vechbrand.text.toString()
        val vechmodel = vechmodel.text.toString()
        val vechplatenum = platenum.text.toString()

        val vehicle=Vehicle(vechbrand = vechbrand,vechmodel=vechmodel,vechplatenum = vechplatenum,clusername=clusername)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val vehicleRepository = VehicleRepository()
                val response =  vehicleRepository.insertvehicle(vehicle)
                if(response.success == true){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Vehicle Details added", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}