package com.example.foodrecipeapp


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


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
        drawerFragment = supportFragmentManager.findFragmentById(R.id.navigation_drawer_fragment) as NavigationDrawerFragment

    }

    private fun fillExampleList() {
        exampleList = mutableListOf(
            FoodType(R.drawable.pasta, "Pasta"),
            FoodType(R.drawable.pizza, "Pizza"),
            FoodType(R.drawable.kung_pao_chicken, "Kung Pao Chicken"),
            FoodType(R.drawable.dim_sum, "Dim sum"),
            FoodType(R.drawable.sarmale, "Sarmale"),
            FoodType(R.drawable.soup, "Soup"),
        )
    }

    private fun setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@RecyclerActivity, LinearLayoutManager.VERTICAL, false)
        adapter = FoodTypeAdapter(exampleList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.isVerticalScrollBarEnabled = true
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