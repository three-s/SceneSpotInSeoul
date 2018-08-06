package com.threes.scenespotinseoul

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.threes.scenespotinseoul.ui.gallery.GalleryFragment
import com.threes.scenespotinseoul.ui.main.MainFragment
import com.threes.scenespotinseoul.ui.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        view_pager.adapter = MainPagerAdapter(supportFragmentManager, listOf(
            MainFragment(),
            MapFragment(),
            GalleryFragment()
        ))

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                bottom_nav.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
                // No-op
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // No-op
            }
        })

        bottom_nav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    view_pager.currentItem = 0
                    true
                }
                R.id.navigation_map -> {
                    view_pager.currentItem = 1
                    true
                }
                R.id.navigation_gallery -> {
                    view_pager.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    inner class MainPagerAdapter(
        fragmentManager: FragmentManager,
        private val fragments: List<Fragment>
    ) :
        FragmentPagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size
    }
}
