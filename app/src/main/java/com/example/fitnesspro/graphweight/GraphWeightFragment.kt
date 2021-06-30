package com.example.fitnesspro.graphweight

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.fitnesspro.R
import com.example.fitnesspro.addweight.AddWeightViewModel
import com.example.fitnesspro.addweight.AddWeightViewModelFactory
import com.example.fitnesspro.database.FitnessDatabase
import com.example.fitnesspro.databinding.FragmentGraphWeightBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DateFormat
import java.util.*

class GraphWeightFragment: Fragment() {
    private var _binding: FragmentGraphWeightBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGraphWeightBinding.inflate(inflater, container, false)

        // get the data source
        val application = requireNotNull(this.activity).application
        val dataSource = FitnessDatabase.getInstance(application).weightDao

        // create and bind the viewModel
        val viewModelFactory = GraphWeightViewModelFactory(dataSource)
        val viewModel =  ViewModelProvider(this, viewModelFactory).get(GraphWeightViewModel::class.java)
        binding.graphWeightViewModel = viewModel

        viewModel.weeklyEntries.observe(viewLifecycleOwner, Observer {
            it?.let {
                // NOTE: List must be sorted by x-value, or graphing library will throw an exception.
                //       This *should* be taken care of by the Database, which already sorts by timestamp
                // TODO: refactor this
                // TODO: use notifyDataChanged()
                // TODO: Make look better!
                val dataSet = LineDataSet(it, "Label")
                dataSet.valueTextColor = Color.WHITE
                val lineData = LineData(dataSet)
                binding.chart.data = lineData
                binding.chart.setDrawBorders(true)

                val xaxis = binding.chart.xAxis
                xaxis.position = XAxis.XAxisPosition.BOTTOM
                xaxis.textColor = Color.WHITE
                xaxis.valueFormatter = TestFormatter()
                xaxis.labelRotationAngle = 30.0f

                val leftAxis = binding.chart.axisLeft
                leftAxis.textColor = Color.WHITE


//                binding.chart.xAxis.valueFormatter = TestFormatter()
//                binding.chart.xAxis.setDrawLabels(true)
//                binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//                binding.chart.xAxis.labelCount = 8
                binding.chart.fitScreen()
                binding.chart.invalidate()
            }
        })

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

// TODO: rename this, maybe make inner class or something
class TestFormatter: IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val timestamp = value.toLong()

        val date = Date(timestamp)
        val dateTimeFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return dateTimeFormat.format(date)
    }
}