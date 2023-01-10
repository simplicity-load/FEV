package com.fovsol.fev.screens.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.fovsol.fev.R
import com.fovsol.fev.database.TestDatabase
import com.fovsol.fev.databinding.FragmentTestBinding

class TestFragment : Fragment() {

    private lateinit var viewModel: TestViewModel
    private lateinit var binding: FragmentTestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TestDatabase.getInstance(application).testDao

        val arguments = TestFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = TestViewModelFactory(dataSource, application, arguments.testId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TestViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        viewModel.counter.observe(viewLifecycleOwner, Observer {
//            binding.progressText.text = if (it != null) "${it + 1}/30" else "-1/30"
//        })

        viewModel.image.observe(viewLifecycleOwner, Observer {
            val a = it?.substringBeforeLast(".")
            Log.i("lmao", "$a drawable/fevimgs/$a")
            if (a != null) {
                val res = context?.let { c ->
                    ContextCompat.getDrawable(
                        c,
                        resources.getIdentifier(
                            "drawable/img_$a",
                            "drawable",
                            c.packageName
                        )
                    )
                }
                binding.imageView.setImageDrawable(res)
            } else {
                binding.imageView.setImageResource(R.drawable.no_img)
            }
        })
        viewModel.navigateToResult.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(
                    TestFragmentDirections.actionTestFragmentToResultFragment(
                        viewModel.testId
                    )
                )
                viewModel.doneNavigating()
            }
        })

//        viewModel.question.observe(viewLifecycleOwner, Observer {
//            binding.questionText.text = it ?: "Pyetja"
//        })
//
//        viewModel.answer1.observe(viewLifecycleOwner, Observer {
//            binding.optionOneCheckBox.text = it ?: "Opsioni 1"
//        })
//
//        viewModel.answer2.observe(viewLifecycleOwner, Observer {
//            binding.optionTwoCheckBox.text = it ?: "Opsioni 2"
//        })
//
//        viewModel.answer3.observe(viewLifecycleOwner, Observer {
//            binding.optionThreeCheckBox.text = it ?: "Opsioni 3"
//        })

//        binding.nextButton.setOnClickListener { viewModel.nextQuestion() }
        return binding.root
    }

}