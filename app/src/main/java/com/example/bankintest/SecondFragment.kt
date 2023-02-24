package com.example.bankintest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankintest.adapter.CategoryAdapter
import com.example.bankintest.data_base.AppDatabase
import com.example.bankintest.databinding.FragmentSecondBinding
import com.example.bankintest.viewmodels.MainViewModel

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val adapter = CategoryAdapter()
    private val viewModel: MainViewModel by activityViewModels()

    private var subCatId: Long = -1

    companion object {
        fun create(subCatId: Long) = SecondFragment().apply {
            this.subCatId = subCatId
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvSubcategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvSubcategory.adapter = adapter

        AppDatabase.getDatabase(requireContext()).categoryDao().getAllCategory().observe(viewLifecycleOwner){
            adapter.updateItem(it.filter { it.parent?.id == subCatId })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}