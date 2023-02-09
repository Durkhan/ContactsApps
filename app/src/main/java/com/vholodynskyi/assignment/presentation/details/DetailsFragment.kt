package com.vholodynskyi.assignment.presentation.details

import android.os.Bundle
import android.util.Log
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
import com.vholodynskyi.assignment.data.db.contacts.DbContact
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding
import com.vholodynskyi.assignment.di.GlobalFactory
import kotlinx.coroutines.launch


open class DetailsFragment : Fragment() {
    var binding: FragmentDetailsBinding? = null

    private val databaseViewModel by activityViewModels<DetailsViewModel> { GlobalFactory }
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
        Log.d("Delegates",GlobalFactory.customDelegate)
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
        val changeName=binding?.changeName?.text
        binding?.change?.setOnClickListener {
            if (!changeName!!.isEmpty()){
                val dbContact= DbContact(
                    args.id,
                    changeName.toString(),
                    databaseViewModel.contact.value?.lastName,
                    databaseViewModel.contact.value?.email,
                    databaseViewModel.contact.value?.photo
                )
                databaseViewModel.update(dbContact)
                findNavController().navigate(R.id.action_details_to_contactList1)
            }

        }

        binding!!.delete.setOnClickListener {
            databaseViewModel.getContactDeleteById(args.id)
            GlobalFactory.deletedItems.add(databaseViewModel.contact.value!!)
            findNavController().navigate(R.id.action_details_to_contactList1)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}