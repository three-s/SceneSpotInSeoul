package com.threes.scenespotinseoul.utilities

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.addFragment(contentViewId: Int, fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.add(contentViewId, fragment)
    transaction.commit()
}

fun AppCompatActivity.replaceFragment(contentViewId: Int, fragment: Fragment) {
    val transaction = supportFragmentManager.beginTransaction()
    transaction.replace(contentViewId, fragment)
    transaction.commit()
}