package com.vholodynskyi.assignment.ui.contactslist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vholodynskyi.assignment.utility.FollowData
import com.vholodynskyi.assignment.api.contacts.ApiContact
import com.vholodynskyi.assignment.utility.AppPreferences
import com.vholodynskyi.assignment.utility.datastore
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.db.DatabaseViewModel
import com.vholodynskyi.assignment.db.contacts.DbContact
import com.vholodynskyi.assignment.di.GlobalFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

open class ContactsListFragment : Fragment() {

    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }
    private lateinit var datastore:AppPreferences
    private val contactsListViewModel:ContactsListViewModel by activityViewModels { GlobalFactory}
    private val databaseViewModel:DatabaseViewModel by activityViewModels{GlobalFactory}
    private var contactsList= arrayListOf<DbContact>()

    private fun onContactClicked(id:String) {
        findNavController()
            .navigate(ContactsListFragmentDirections.actionContactListToDetails(id))
    }

    private var binding: FragmentContactsListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Creates a vertical Layout Manager
        return FragmentContactsListBinding.inflate(layoutInflater, container, false)
            .apply {
                contactList.layoutManager = LinearLayoutManager(context)
                contactList.adapter = contactAdapter
            }
            .also {
                binding = it
            }
            .root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        datastore = AppPreferences(requireContext().datastore)

        lifecycleScope.launch(){
            if (datastore.isEmpty.first()){
                getLivedataObserver()
            }
            else{
                getDataFromDatabase()

            }
        }



    }

    private fun getLivedataObserver() {
        contactsListViewModel.liveDatalistofContacts.observe(viewLifecycleOwner, Observer {
            when(it) {
                is FollowData.Loading -> {
                    binding?.progressBar?.isVisible = it.isLoading
                }

                is FollowData.Failure -> {
                    Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding?.progressBar?.isVisible = false
                }

                is FollowData.Success -> {
                    addDatatoDatabase(it.isSuccess)

                }
            }
        })

    }

    private fun addDatatoDatabase(items: List<ApiContact>) {
        for (contact in items){
            var dbContact=DbContact(id = 0,
                contact.name?.firstName,
                contact.name?.lastName,
                contact.email,
                contact.picture!!.medium
            )
            contactsList.add(dbContact)
        }
        databaseViewModel.insert(contactsList)
        binding?.progressBar?.isVisible = false

        lifecycleScope.launch {
            datastore.setIsNotEmpty(false)
        }

        getDataFromDatabase()

    }
    private fun getDataFromDatabase(){
        databaseViewModel.getAllContacts().observe(viewLifecycleOwner, Observer {contacts->
            contacts?.let {
                contactAdapter.items = it
            }

        })
        binding?.progressBar?.isVisible = false

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}