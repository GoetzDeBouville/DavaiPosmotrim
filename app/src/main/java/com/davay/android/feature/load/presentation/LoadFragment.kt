package com.davay.android.feature.load.presentation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davay.android.R
import com.davay.android.app.AppComponentHolder
import com.davay.android.app.MainActivity
import com.davay.android.base.BaseFragment
import com.davay.android.databinding.FragmentLoadBinding
import com.davay.android.di.ScreenComponent
import com.davay.android.feature.load.di.DaggerLoadFragmentComponent

class LoadFragment : BaseFragment<FragmentLoadBinding, LoadViewModel>(
    FragmentLoadBinding::inflate
) {

    override val viewModel: LoadViewModel by injectViewModel<LoadViewModel>()
    override fun diComponent(): ScreenComponent = DaggerLoadFragmentComponent.builder()
        .appComponent(AppComponentHolder.getComponent())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { _ ->
            viewModel.navigate(R.id.action_loadFragment_to_mainFragment)
        }
        binding.button2.setOnClickListener { _ ->
            viewModel.navigate(R.id.action_loadFragment_to_registrationFragment)
        }

        binding.btnTestBlur.setOnClickListener {
            buildDialog()
        }
    }


    private fun buildDialog() {
        (requireActivity() as MainActivity).applyBlurEffect()

        val alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle("Title")
            .setMessage("blureffect test")
            .setPositiveButton("yes") { _: DialogInterface, _: Int ->
                (requireActivity() as MainActivity).clearBlurEffect()
            }
            .setNegativeButton("no") { _: DialogInterface, _: Int ->
                (requireActivity() as MainActivity).clearBlurEffect()
            }
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.setOnCancelListener {
            (requireActivity() as MainActivity).clearBlurEffect()
        }
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(resources.getColor(R.color.black, null))
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(com.davai.uikit.R.color.error, null))
    }
}