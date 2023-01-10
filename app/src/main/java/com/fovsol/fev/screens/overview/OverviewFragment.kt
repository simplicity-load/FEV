package com.fovsol.fev.screens.overview

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fovsol.fev.R
import com.fovsol.fev.database.TestDatabase
import com.fovsol.fev.databinding.FragmentOverviewBinding
import ir.mahozad.android.PieChart
import ir.mahozad.android.unit.Dimension

class OverviewFragment : Fragment() {
    private lateinit var binding: FragmentOverviewBinding
    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TestDatabase.getInstance(application).testDao
        val viewModelFactory = OverviewViewModelFactory(dataSource, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.lifecycleOwner = this

        binding.overviewViewModel = viewModel


        viewModel.incorrectPerc.observe(viewLifecycleOwner, Observer {
            updatePieChart()
        })

        viewModel.correctPerc.observe(viewLifecycleOwner, Observer {
            updatePieChart()
        })

        viewModel.emptyPerc.observe(viewLifecycleOwner, Observer {
            updatePieChart()
        })

        binding.pieChart.apply {
            slices = listOf(
                PieChart.Slice(1.0f, Color.GRAY),
            )
            startAngle = -90
            isCenterLabelEnabled = true

            gap = Dimension.DP(2f)
            labelType = PieChart.LabelType.NONE
            centerLabelSize = Dimension.SP(34f)
            centerLabel = "0"
            centerLabelColor = Color.rgb(155, 155, 155)
            centerLabelIcon = PieChart.DefaultIcons.NO_ICON
            centerLabelIconHeight = Dimension.SP(0f)
            centerLabelIconMargin = Dimension.SP(0f)
            holeRatio = 0.8f
        }

        val adapter = TestsAdapter(TestsListener { testId ->
            findNavController().navigate(
                OverviewFragmentDirections.actionOverviewFragmentToTestFragment(
                    testId
                )
            )
        })
        binding.testList.adapter = adapter

        viewModel.allTests.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.testList.layoutManager = manager

        return binding.root
    }

    fun updatePieChart() {
        if (viewModel.correctPerc.value == null || viewModel.incorrectPerc.value == null || viewModel.emptyPerc.value == null)
            return
        val cPerc: Float = viewModel.correctPerc.value!!
        val cTCount: Int = viewModel.correctTestCount.value!!
        val icPerc: Float = viewModel.incorrectPerc.value!!
        val ePerc: Float = viewModel.emptyPerc.value!!
        binding.pieChart.apply {
            Log.i(
                "PieChart",
                "c: $cPerc | inc: $icPerc | e: $ePerc"
            )
            slices = listOf(
                PieChart.Slice(
                    cPerc,
                    Color.rgb(155, 255, 155)
                ),
                PieChart.Slice(icPerc, Color.rgb(255, 155, 155)),
                PieChart.Slice(ePerc, Color.rgb(155, 155, 155))
            )
            centerLabel = cTCount.toString()
            val greenShade = Color.rgb(
                155,
                155 + (100f * (cPerc)).toInt(),
                155
            )
            Log.i(
                "COLOR",
                "$cPerc | ${155 + (100f * (cPerc)).toInt()}"
            )
            centerLabelColor = greenShade
        }
    }

}
