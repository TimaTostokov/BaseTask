package com.example.my.basetask.presentation.main

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.my.basetask.R
import com.example.my.basetask.corebase.BaseFragment
import com.example.my.basetask.databinding.FragmentMainBinding
import com.example.my.basetask.model.TaskModel
import com.example.my.basetask.utils.ToDisplay
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_ADD_TASK
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_ADD_TO_MAIN
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_MAIN_TO_ADD
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_SET_TASK
import com.example.my.basetask.utils.Сonstants.REQUIRES_KEY_UPDATE_TASK

class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    private val adapter = MainAdapter(this::onClickItem, this::showAlertDialog, this::updateTask)
    override val viewModel: MainViewModel
        get() = ViewModelProvider(this)[MainViewModel::class.java]

    override fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMainBinding.inflate(inflater, container, false)

    override fun initMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.show_all -> {
                        viewModel.setSortList(ToDisplay.ALL)
                        return true
                    }

                    R.id.show_active -> {
                        viewModel.setSortList(ToDisplay.ACTIVE)
                        return true
                    }

                    R.id.show_not_active -> {
                        viewModel.setSortList(ToDisplay.COMPLETED)
                        return true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun initStart() {
        setFragmentResultListener(REQUIRES_KEY_ADD_TO_MAIN) { _, bundle ->
            bundle.getString(REQUIRES_KEY_ADD_TASK)?.let { viewModel.addNewTask(it) }
            bundle.getSerializable(REQUIRES_KEY_UPDATE_TASK)
                ?.let { viewModel.updateTask(it as TaskModel) }
        }
    }

    override fun initListener() {
        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
    }

    override fun initRecyclerView() {
        viewModel.liveData.observe(viewLifecycleOwner) { list ->
            adapter.addDataInList(list)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun onClickItem(task: TaskModel) {
        viewModel.setTaskDone(task)
    }

    private fun updateTask(task: TaskModel) {
        setFragmentResult(
            REQUIRES_KEY_MAIN_TO_ADD,
            bundleOf(REQUIRES_KEY_SET_TASK to task)
        )
        findNavController().navigate(R.id.addTaskFragment)
    }

    private fun showAlertDialog(task: TaskModel) {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(getString(R.string.delete))
            .setMessage(getString(R.string.delete_message)).setCancelable(true)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteTask(task)
            }.setNegativeButton(getString(R.string.no)) { _, _ -> }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}