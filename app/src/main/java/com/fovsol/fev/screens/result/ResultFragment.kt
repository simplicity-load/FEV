package com.fovsol.fev.screens.result

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.fovsol.fev.R
import com.fovsol.fev.database.TestDatabase
import com.fovsol.fev.databinding.FragmentResultBinding
import ir.mahozad.android.PieChart
import ir.mahozad.android.unit.Dimension

class ResultFragment : Fragment() {

    private lateinit var viewModel: ResultViewModel
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TestDatabase.getInstance(application).testDao

        val arguments = ResultFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = ResultViewModelFactory(dataSource, application, arguments.testId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ResultViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ResultAdapter()
        binding.resultList.adapter = adapter

        viewModel.mainTest.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        val manager = GridLayoutManager(activity, 5, GridLayoutManager.VERTICAL, false)
        binding.resultList.layoutManager = manager

        viewModel.percCorrect.observe(viewLifecycleOwner, Observer {
            updatePieChart()
            if (it != null) {
                viewModel.updateTest()
            }
        })

        viewModel.percIncorrect.observe(viewLifecycleOwner, Observer {
            updatePieChart()
            if (it != null) {
                viewModel.updateTest()
            }
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

        return binding.root
    }

    private fun updatePieChart() {
        if (viewModel.percCorrect.value == null || viewModel.percIncorrect.value == null)
            return
        val cPerc: Float = (viewModel.percCorrect.value!!.toFloat() / 30f)
        val icPerc: Float = (viewModel.percIncorrect.value!!.toFloat() / 30f)
        binding.pieChart.apply {
            slices = listOf(
                PieChart.Slice(
                    cPerc,
                    Color.rgb(155, 255, 155)
                ),
                PieChart.Slice(
                    icPerc,
                    Color.rgb(255, 155, 155)
                )
            )
            centerLabel = (cPerc * 100f).toInt().toString() + "%"
            val greenShade = Color.rgb(
                155,
                155 + (100f * (cPerc)).toInt(),
                155
            )
            centerLabelColor = greenShade
        }
    }
}