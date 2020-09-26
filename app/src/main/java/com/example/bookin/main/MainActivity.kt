package com.example.bookin.main
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.bookin.R
import com.example.bookin.entities.BookinActivity
import com.example.bookin.home.HomeFragment
import com.example.bookin.profile.ProfileFragment
import com.example.bookin.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BookinActivity() {
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.context = this

        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val bottomNavBar: BottomNavigationView = findViewById(R.id.bottomNavBar)
        var homeFragment : HomeFragment? = null
        var profileFragment : ProfileFragment? = null
        var searchFragment : SearchFragment? = null
        //ToDo estudiar la posibilidad de cambiar por un viewPager (no habria que programar el slide entre fragments)
        bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.search -> {
                    if (getCurrentContainerPosition() != 0){
                        if (searchFragment==null){
                            searchFragment = SearchFragment.createInstance()
                        }
                        loadFragment(searchFragment!!,0)

                    }
                }
                R.id.home -> {
                    if (getCurrentContainerPosition() != 1) {
                        if (homeFragment==null){
                            homeFragment = HomeFragment.createInstance()
                        }
                        loadFragment(homeFragment!!, 1)
                    }
                }
                R.id.profile -> {
                    if (getCurrentContainerPosition()!= 2){
                        if (profileFragment==null){
                            profileFragment = ProfileFragment.createInstance()
                        }
                        loadFragment(profileFragment!!, 2)
                    }
                }
            }
            true
        }
        val view: View = bottomNavBar.findViewById(R.id.home)
        view.performClick()
    }

    private fun express(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun loadFragment(fragment: Fragment, position: Int) {
        val anims = getAnimationForRelativePosition(position)
        if (anims!=null){
            val anim1 : Int = anims[0]
            val anim2 : Int = anims[1]
            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(anim1,anim2)
                .replace(R.id.mainFragmentContainer, fragment)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainFragmentContainer, fragment)
                .commit()
        }

    }

    private fun getAnimationForRelativePosition(position: Int): List<Int>? {
        val currentPosition: Int? = getCurrentContainerPosition()
        return if (currentPosition != null) {
            if (currentPosition > position) {
                //slide towards right
                val listAnim = listOf(R.anim.slide_in_left,R.anim.slide_out_right)
                listAnim
            } else {
                //Slide towards left
                val listAnim = listOf(R.anim.slide_in_right,R.anim.slide_out_left)
                listAnim
            }
        } else {
            null
        }
    }

    private fun getCurrentContainerPosition(): Int? {
        val currentFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer)
        var currentPosition: Int? = null
        when (currentFragment) {
            is SearchFragment -> currentPosition = 0
            is HomeFragment -> currentPosition = 1
            is ProfileFragment -> currentPosition = 2
        }
        return currentPosition
    }


    companion object {
        fun navigate(context: Activity) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }


}


