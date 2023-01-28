package com.vholodynskyi.assignment.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.vholodynskyi.assignment.R
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding
import com.vholodynskyi.assignment.db.DatabaseViewModel
import com.vholodynskyi.assignment.di.GlobalFactory
import kotlinx.coroutines.launch


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null

    private val databaseViewModel by activityViewModels<DatabaseViewModel> { GlobalFactory }
    private val args:DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(layoutInflater, container, false)
            .also {
                binding = it
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        databaseViewModel.getContactById(args.id)
        lifecycleScope.launch {
            databaseViewModel.contact.observe(viewLifecycleOwner, Observer {dbContact->
                with(binding!!){
                    firstname.text=dbContact.firstName
                    lastname.text=dbContact.lastName
                    email.text=dbContact.email
                    context?.let { Glide.with(it).load(dbContact.photo).into(medium) }
                }
            })
        }


        binding!!.delete.setOnClickListener {
            databaseViewModel.getContactDeleteById(args.id)
            findNavController().navigate(R.id.action_details_to_contactList1)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}