package com.example.fitnesspro.first

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnesspro.R
import com.example.fitnesspro.addweight.AddWeightViewModel
import com.example.fitnesspro.addweight.AddWeightViewModelFactory
import com.example.fitnesspro.database.FitnessDatabase
import com.example.fitnesspro.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        // get the data source
        val application = requireNotNull(this.activity).application
        val dataSource = FitnessDatabase.getInstance(application).weightDao

        // create and bind the viewModel
        val viewModelFactory = FirstViewModelFactory(dataSource)
        val viewModel =  ViewModelProvider(this, viewModelFactory).get(FirstViewModel::class.java)
        binding.firstViewModel = viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.graphButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_graphWeightFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}