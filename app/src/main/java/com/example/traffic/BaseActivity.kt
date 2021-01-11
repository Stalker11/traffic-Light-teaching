package com.example.traffic

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

abstract class BaseActivity: AppCompatActivity() {
    fun replaceFragment(
        viewResId: Int,
        newFragment: Fragment,
        tag: String?,
        addToBackStack: Boolean
    ) {
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(viewResId, newFragment, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }

    fun addFragment(viewResId: Int, newFragment: Fragment?, tag: String?, addToBackStack: Boolean) {
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
        transaction.add(viewResId, newFragment!!, tag)
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commitAllowingStateLoss()
    }
}