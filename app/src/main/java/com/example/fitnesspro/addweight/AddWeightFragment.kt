package com.example.fitnesspro.addweight

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnesspro.R
import com.example.fitnesspro.database.FitnessDatabase
import com.example.fitnesspro.databinding.FragmentAddWeightBinding
import com.google.android.material.datepicker.MaterialDatePicker
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddWeightFragment : Fragment() {

    private var _binding: FragmentAddWeightBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // TODO: refactor according to https://developer.android.com/topic/libraries/architecture/viewmodel
        _binding = FragmentAddWeightBinding.inflate(inflater, container, false)

        // get the data source
        val application = requireNotNull(this.activity).application
        val dataSource = FitnessDatabase.getInstance(application).weightDao

        // create and bind the viewModel
        val viewModelFactory = AddWeightViewModelFactory(dataSource)
        val viewModel =  ViewModelProvider(this, viewModelFactory).get(AddWeightViewModel::class.java)
        binding.addWeightViewModel = viewModel

        // set lifecycle owner so app can observe LiveData changes
        binding.lifecycleOwner = viewLifecycleOwner

        // setup observers
        viewModel.navigateToFirstFragment.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        })
        viewModel.showDateClicker.observe(viewLifecycleOwner, {
            showDateClicker()
        })


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDateClicker() {
        // todo: is there a way to cache this? not sure
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build()

        activity?.let {
            picker.addOnPositiveButtonClickListener { millis ->
                // NOTE: this function gives the millis of the date chosen
                //       we don't want to convert these millis by timezone because it corresponds to
                //       the LOCAL date we want.
                binding.addWeightViewModel!!.onDateChosen(Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC")))
            }

            picker.show(it.supportFragmentManager, picker.toString())
        }
    }
}