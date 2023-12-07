package com.example.my.basetask.corebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    var _binding: VB? = null
    protected abstract val viewModel: VM
    val binding get() = _binding!!
    protected abstract fun inflaterViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflaterViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStart()
        initRecyclerView()
        initMenu()
        initListener()
    }

    open fun initStart() {}

    open fun initRecyclerView() {}

    open fun initMenu() {}

    open fun initListener() {}

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}