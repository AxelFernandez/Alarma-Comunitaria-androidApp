package com.axelfernandez.alarmacomunitaria.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.axelfernandez.alarmacomunitaria.R
import com.axelfernandez.alarmacomunitaria.utils.ViewUtil
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val root = view?:return
        homeViewModel.suscribeToTopic(root)
        root.emergency_button.setOnClickListener {
            homeViewModel.getLocationAndSendAlarm(requireActivity(),it,"Emergencia")
        }

        root.medic_button.setOnClickListener {
            homeViewModel.getLocationAndSendAlarm(requireActivity(),it,"Alarma Medica")
        }

        homeViewModel.returnResponse().observe(viewLifecycleOwner, Observer {
            if (it.done){
                ViewUtil.setSnackBar(root,R.color.colorAccent,"Alarma Enviada Correctamente")
            }else{
                ViewUtil.setSnackBar(root,R.color.red,"Hubo un problema al enviar la alarme, intenta de nuevo")
            }
        })
    }
}