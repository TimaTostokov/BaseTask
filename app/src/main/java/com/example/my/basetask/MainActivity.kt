package com.example.my.basetask

import android.view.MenuItem
import androidx.navigation.findNavController
import com.example.my.basetask.corebase.BaseActivity
import com.example.my.basetask.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun inflaterViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController(R.id.nav_host_fragment).navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

}