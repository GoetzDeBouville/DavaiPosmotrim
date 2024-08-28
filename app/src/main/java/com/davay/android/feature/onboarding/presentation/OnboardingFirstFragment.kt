package com.davay.android.feature.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.davay.android.R

class OnboardingFirstFragment : Fragment() {

   // private var contentIds: IntArray? = null
   private var onboardingItem: OnboardingItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
           // contentIds = it.getIntArray(ARG_CONTENT_IDS)
           // onboardingItem = OnboardingFirstFragmentArgs.fromBundle(it).onboardingItem
            //onboardingItem = OnboardingDataProvider.OnboardingItem()
            val args = OnboardingFirstFragmentArgs.fromBundle(it)
            onboardingItem = args.onboardingItem
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_onboarding_first,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // contentIds?.let {
//            view.findViewById<TextView>(R.id.tv_top_title).setText(it[0])
//            view.findViewById<ImageView>(R.id.iv_main_image).setImageResource(it[1])
//            view.findViewById<TextView>(R.id.tv_bottom_title).setText(it[2])
//        }
        onboardingItem?.let { item ->
            item.textResId?.let { view.findViewById<TextView>(R.id.tv_top_title).setText(it) }
            item.imageResId?.let { view.findViewById<ImageView>(R.id.iv_main_image).setImageResource(it) }
            item.descriptionResId?.let { view.findViewById<TextView>(R.id.tv_bottom_title).setText(it) }
        }
    }

    companion object {
        //private const val ARG_CONTENT_IDS = "content_ids"

        @JvmStatic
//        fun newInstance(contentIds: IntArray) =
//            OnboardingFirstFragment().apply {
//                arguments = Bundle().apply {
//                    putIntArray(ARG_CONTENT_IDS, contentIds)
//                }
//            }
//        fun newInstance(onboardingItem: OnboardingDataProvider.OnboardingItem) =
//            OnboardingFirstFragment().apply {
//                arguments = OnboardingFirstFragmentArgs(onboardingItem).toBundle()
//            }
        fun newInstance(onboardingItem: OnboardingItem) =
            OnboardingFirstFragment().apply {
                arguments = OnboardingFirstFragmentArgs(onboardingItem).toBundle()
            }
    }
}