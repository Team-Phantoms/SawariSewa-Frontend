package com.example.sawariapatkalinsewa.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.sawariapatkalinsewa.R
import com.example.sawariapatkalinsewa.api.ServiceBuilder
import com.example.sawariapatkalinsewa.repository.CustomerRepository
import com.example.sawariapatkalinsewa.repository.VehicleRepository
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewVehicleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewVehicleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewusername: TextInputEditText
    private lateinit var viewvechbrand: TextInputEditText
    private lateinit var viewvechmodel: TextInputEditText
    private lateinit var viewplatenum: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_view_vehicle, container, false)
        viewusername=view.findViewById(R.id.viewusername)
        viewvechbrand=view.findViewById(R.id.viewvechbrand)
        viewvechmodel=view.findViewById(R.id.viewvechmodel)
        viewplatenum=view.findViewById(R.id.viewplatenum)

        getVehicle()

        return view
    }

    private fun getVehicle() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val vehicleRepository = VehicleRepository()
                val response = vehicleRepository.getVehicle()
                if (response.success==true){
                    val listvehicle = response.vdata
                    if (listvehicle != null) {
                        withContext(Dispatchers.Main){
                            Log.d("Debug:", "Your data:" + listvehicle[0])
                            viewusername.setText("${listvehicle[0].clusername}")
                            viewvechbrand.setText("${listvehicle[0].vechbrand}")
                            viewvechmodel.setText("${listvehicle[0].vechmodel}")
                            viewplatenum.setText("${listvehicle[0].vechplatenum}")


                        }


                    }
                }


            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(context,
                        "Error : ${ex.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewVehicleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewVehicleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}