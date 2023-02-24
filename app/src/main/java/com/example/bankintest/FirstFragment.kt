package com.example.bankintest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commitNow
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.bankintest.adapter.CategoryAdapter
import com.example.bankintest.data_base.AppDatabase
import com.example.bankintest.databinding.FragmentFirstBinding
import com.example.bankintest.viewmodels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val adapter = CategoryAdapter().apply {
        onItemClick = {
            val frag = SecondFragment.create(it.id)
            parentFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                    R.anim.slide_in_left, R.anim.slide_out_right)
                .addToBackStack(frag::class.qualifiedName)
                .replace(R.id.container, frag)
                .show(frag)
                .commit()
        }
    }
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.rvCategory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCategory.adapter = adapter
        binding.swipeRefresh.isRefreshing = true
        viewModel.database = AppDatabase.getDatabase(requireContext())
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getData()
        }
        AppDatabase.getDatabase(requireContext()).categoryDao().getAllCategory().observe(viewLifecycleOwner){
            binding.swipeRefresh.isRefreshing = false
            adapter.updateItem(it.filter { it.parent == null })
        }

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}