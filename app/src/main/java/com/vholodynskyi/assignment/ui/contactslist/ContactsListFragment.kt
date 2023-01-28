package com.vholodynskyi.assignment.ui.contactslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vholodynskyi.assignment.api.utility.FollowData
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.di.GlobalFactory
import kotlinx.coroutines.test.withTestContext

open class ContactsListFragment : Fragment() {
    private val contactsListViewModel:ContactsListViewModel by activityViewModels { GlobalFactory }
    private val contactAdapter: ContactAdapter by lazy {
        ContactAdapter(
            requireActivity(),
            this::onContactClicked
        )
    }

    private fun onContactClicked(id: String) {
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
        getLivedataObserver()
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
                    contactAdapter.items=it.isSuccess
                    binding?.progressBar?.isVisible = false

                }
            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}