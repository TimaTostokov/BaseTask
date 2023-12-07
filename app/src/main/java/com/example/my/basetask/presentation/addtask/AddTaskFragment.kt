package com.example.my.basetask.presentation.addtask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.my.basetask.MainActivity
import com.example.my.basetask.R
import com.example.my.basetask.corebase.BaseFragment
import com.example.my.basetask.databinding.FragmentAddTaskBinding
import com.example.my.basetask.model.TaskModel
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_ADD_TASK
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_ADD_TO_MAIN
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_MAIN_TO_ADD
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_SET_TASK
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_UPDATE_TASK

class AddTaskFragment : BaseFragment<FragmentAddTaskBinding, ViewModel>() {
    override val viewModel: ViewModel
        get() = TODO("Not yet implemented")

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddTaskBinding.inflate(inflater, container, false)

    private var _task: TaskModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    override fun initStart() {
        super.initStart()
        setFragmentResultListener(REQUIRES_KEY_MAIN_TO_ADD) { _, bundle ->
            bundle.getSerializable(REQUIRES_KEY_SET_TASK)?.let { task ->
                _task = task as TaskModel
                initView()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        _task?.let { task ->
            binding.btnAddTask.text = getString(R.string.update)
            binding.etTitle.setText(task.title)
            binding.cbState.visibility = View.VISIBLE
            binding.cbState.isChecked = task.state
        }
    }

    override fun initListener() {
        binding.btnAddTask.setOnClickListener {
            if (_task == null)
                addNewTask()
            else {
                _task!!.title = binding.etTitle.text.toString()
                _task!!.state = binding.cbState.isChecked
                updateTask()
            }
        }
    }

    private fun updateTask() {
        setFragmentResult(
            REQUIRES_KEY_ADD_TO_MAIN,
            bundleOf(REQUIRES_KEY_UPDATE_TASK to _task)
        )
        findNavController().navigateUp()
    }

    private fun addNewTask() {
        setFragmentResult(
            REQUIRES_KEY_ADD_TO_MAIN,
            bundleOf(REQUIRES_KEY_ADD_TASK to binding.etTitle.text.toString())
        )
        findNavController().navigateUp()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

}