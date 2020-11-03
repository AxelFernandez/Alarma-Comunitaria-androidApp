package com.axelfernandez.alarmacomunitaria.ui.Information

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import com.axelfernandez.alarmacomunitaria.HomeActivity
import com.axelfernandez.alarmacomunitaria.R
import com.axelfernandez.alarmacomunitaria.models.InfoPlus
import com.axelfernandez.alarmacomunitaria.utils.ViewUtil
import kotlinx.android.synthetic.main.information_fragment.view.*

class InformationFragment : Fragment() {

    companion object {
        fun newInstance() = InformationFragment()
    }

    private lateinit var viewModel: InformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.information_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InformationViewModel::class.java)
        val view = view?:return
        var addressEdit = view.findViewById(R.id.address) as EditText
        var phoneEdit = view.findViewById(R.id.phone) as EditText

        view.save_direction.setOnClickListener {
            val address = addressEdit.text.toString()
            val phone = phoneEdit.text.toString()
            val user = ViewUtil.getUserFromSharedPreferences(requireContext())
            val info = InfoPlus(phone,address,user.id?:"")
            viewModel.registryUserInfo(info)
        }

        viewModel.returnResponse().observe(viewLifecycleOwner, Observer {
            if (it.done){
                ViewUtil.setIsCompletedRegistered(requireContext(),true)
                val intent = Intent(context, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                activity?.finish()

            }
        })
    }

}