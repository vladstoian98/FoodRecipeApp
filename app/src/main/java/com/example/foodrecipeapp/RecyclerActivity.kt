package com.example.foodrecipeapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class RecyclerActivity : AppCompatActivity() {
    private lateinit var adapter: FoodTypeAdapter
    private lateinit var exampleList: MutableList<FoodType>
    private lateinit var recyclerView: RecyclerView
    private lateinit var drawerFragment: NavigationDrawerFragment

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        fillExampleList()
        setUpRecyclerView()
    }

    private fun fillExampleList() {
        exampleList = mutableListOf(
            FoodType(R.drawable.italy, "Italian"),
            FoodType(R.drawable.romania, "Romanian"),
            FoodType(R.drawable.china, "Chinese")
        )
    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@RecyclerActivity)
        adapter = FoodTypeAdapter(exampleList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.food_types_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView


        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return true
    }

    override fun onBackPressed() {
        if (drawerFragment.isVisible()) {
            drawerFragment.closeDrawer()
        } else {
            super.onBackPressed()
        }
    }
}