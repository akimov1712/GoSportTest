package ru.topbun.gosporttest.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.util.zip.Inflater

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

open class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>
): Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding ?: throw RuntimeException("ViewBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
    }

    open fun setViews(){
        setListenersInView()
        setAdapters()
    }

    open fun setListenersInView(){}
    open fun setAdapters(){}

    open fun observeViewModel(){}

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}