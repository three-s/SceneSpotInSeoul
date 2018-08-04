package com.threes.scenespotinseoul

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.threes.scenespotinseoul.ui.gallery.GalleryFragment
import com.threes.scenespotinseoul.ui.main.MainFragment
import com.threes.scenespotinseoul.ui.map.MapFragment
import com.threes.scenespotinseoul.utilities.addFragment
import com.threes.scenespotinseoul.utilities.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(R.id.container, MainFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                replaceFragment(R.id.container, MapFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_gallery -> {
                replaceFragment(R.id.container, GalleryFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        addFragment(R.id.container, MainFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
