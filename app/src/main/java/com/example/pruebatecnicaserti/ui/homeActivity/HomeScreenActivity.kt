package com.example.pruebatecnicaserti.ui.homeActivity

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pruebatecnicaserti.R
import com.example.pruebatecnicaserti.data.realm.entity.MyEntityList
import com.example.pruebatecnicaserti.data.realm.entity.VerifiedUserEntity
import com.example.pruebatecnicaserti.databinding.ActivityHomeScreenBinding
import com.example.pruebatecnicaserti.model.remote.users.UserData
import com.example.pruebatecnicaserti.repository.UsersRepository
import com.example.pruebatecnicaserti.ui.extensions.applyInsetsWithOriginalPadding
import com.example.pruebatecnicaserti.ui.userDetailActivity.UserDetailsActivity
import com.example.pruebatecnicaserti.ui.welcomeActivity.MainActivity
import com.example.pruebatecnicaserti.utils.Constants
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var usersRepository: UsersRepository


    lateinit var usersList: List<UserData>

    private lateinit var binding: ActivityHomeScreenBinding
    private lateinit var toogle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.drawerLayout.applyInsetsWithOriginalPadding()

        setup()

        getAndSetUsers()
    }

    private fun fakeRecyclerView() {

        usersList = listOf(
            UserData("https://randomuser.me/api/portraits/women/1.jpg", "alice@example.com", "Alice", 1, "Johnson"),
            UserData("https://randomuser.me/api/portraits/men/2.jpg", "bob@example.com", "Bob", 2, "Smith"),
            UserData("https://randomuser.me/api/portraits/women/3.jpg", "carol@example.com", "Carol", 3, "Williams"),
            UserData("https://randomuser.me/api/portraits/men/4.jpg", "dave@example.com", "Dave", 4, "Brown"),
            UserData("https://randomuser.me/api/portraits/women/5.jpg", "eve@example.com", "Eve", 5, "Davis"),
            UserData("https://randomuser.me/api/portraits/men/6.jpg", "frank@example.com", "Frank", 6, "Miller"),
            UserData("https://randomuser.me/api/portraits/women/7.jpg", "grace@example.com", "Grace", 7, "Wilson"),
            UserData("https://randomuser.me/api/portraits/men/8.jpg", "henry@example.com", "Henry", 8, "Moore"),
            UserData("https://randomuser.me/api/portraits/women/9.jpg", "irene@example.com", "Irene", 9, "Taylor"),
            UserData("https://randomuser.me/api/portraits/men/10.jpg", "jack@example.com", "Jack", 10, "Anderson"),
            UserData("https://randomuser.me/api/portraits/women/11.jpg", "karen@example.com", "Karen", 11, "Thomas"),
            UserData("https://randomuser.me/api/portraits/men/12.jpg", "leo@example.com", "Leo", 12, "Jackson"),
            UserData("https://randomuser.me/api/portraits/women/13.jpg", "mia@example.com", "Mia", 13, "White"),
            UserData("https://randomuser.me/api/portraits/men/14.jpg", "nick@example.com", "Nick", 14, "Harris"),
            UserData("https://randomuser.me/api/portraits/women/15.jpg", "olivia@example.com", "Olivia", 15, "Martin"),
            UserData("https://randomuser.me/api/portraits/men/16.jpg", "peter@example.com", "Peter", 16, "Thompson"),
            UserData("https://randomuser.me/api/portraits/women/17.jpg", "quinn@example.com", "Quinn", 17, "Garcia"),
            UserData("https://randomuser.me/api/portraits/men/18.jpg", "ryan@example.com", "Ryan", 18, "Martinez"),
            UserData("https://randomuser.me/api/portraits/women/19.jpg", "sophia@example.com", "Sophia", 19, "Robinson"),
            UserData("https://randomuser.me/api/portraits/men/20.jpg", "tom@example.com", "Tom", 20, "Clark"),
            UserData("https://randomuser.me/api/portraits/women/21.jpg", "uma@example.com", "Uma", 21, "Rodriguez"),
            UserData("https://randomuser.me/api/portraits/men/22.jpg", "victor@example.com", "Victor", 22, "Lewis"),
            UserData("https://randomuser.me/api/portraits/women/23.jpg", "wendy@example.com", "Wendy", 23, "Lee"),
            UserData("https://randomuser.me/api/portraits/men/24.jpg", "xavier@example.com", "Xavier", 24, "Walker"),
            UserData("https://randomuser.me/api/portraits/women/25.jpg", "zoe@example.com", "Zoe", 25, "Hall")
        )

        val adapter = UsersAdapter(usersList){
            navToDetails(it.id)
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter
    }

    private fun getAndSetUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = usersRepository.getUsers()
            if (result.isSuccess){
                Log.d("APITEST",result.getOrNull().toString())
                val users = result.getOrNull()?.data
                if (users != null) {
                    withContext (Dispatchers.Main) {
                        usersList = users
                        val adapter = UsersAdapter(users) {
                            navToDetails(it.id)
                        }
                        binding.rvUsers.layoutManager = LinearLayoutManager(this@HomeScreenActivity)
                        binding.rvUsers.adapter = adapter
                    }
                }
            }else{
                val errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido"
                Log.d("APITEST",errorMessage)
            }
        }
    }

    private fun setup() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        supportActionBar?.title ="Bienvenido"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))

        toogle = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        binding.drawerLayout.addDrawerListener(toogle)

        toogle.drawerArrowDrawable.color = Color.WHITE


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        toogle.syncState()

        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(this)


        val realm = Realm.getDefaultInstance()
        val user = realm.where(VerifiedUserEntity::class.java)
            .equalTo("id", 1)
            .findFirst()

        binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_verified_user_name).text = "${user?.email}"


        realm.close()

        recoverAndSetRecentSearch()

        binding.shUser.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val trimedQuery = query?.trim()
                if (trimedQuery != null) {
                    binding.shUser.clearFocus()
                    val newList = filterUsers(trimedQuery)
                    if (newList.isNotEmpty()){
                        saveQuery(trimedQuery)
                        val adapter = UsersAdapter(newList) {
                            navToDetails(it.id)
                        }
                        binding.rvUsers.layoutManager = LinearLayoutManager(this@HomeScreenActivity)
                        binding.rvUsers.adapter = adapter
                    }else{
                        Toast.makeText(this@HomeScreenActivity, "No se encontraron resultados :(", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    val adapter = UsersAdapter(usersList) {
                        navToDetails(it.id)
                    }
                    binding.rvUsers.layoutManager = LinearLayoutManager(this@HomeScreenActivity)
                    binding.rvUsers.adapter = adapter
                }
                return true
            }


            private fun saveQuery(query: String) {
                CoroutineScope(Dispatchers.IO).launch {
                    val realm = Realm.getDefaultInstance()
                    realm.executeTransaction { transactionRealm ->
                        var entityList = transactionRealm.where(MyEntityList::class.java)
                            .equalTo("id", 1)
                            .findFirst()
                        if (entityList == null) {
                            entityList = transactionRealm.createObject(MyEntityList::class.java, 1)
                        }
                        if (!entityList.items.contains(query)) {
                            entityList.items.add(query)
                        }
                    }
                    realm.close()

                    withContext(Dispatchers.Main) {
                        recoverAndSetRecentSearch()
                    }
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    val adapter = UsersAdapter(usersList) {
                        navToDetails(it.id)
                    }
                    binding.rvUsers.layoutManager = LinearLayoutManager(this@HomeScreenActivity)
                    binding.rvUsers.adapter = adapter
                }
                return false
            }

        })
    }

    private fun filterUsers(query: String): List<UserData>  {
        val lowerCaseQuery = query.lowercase()
        val copyOfList = usersList.toList()
        val filterList = copyOfList.filter {
            it.first_name.lowercase().contains(lowerCaseQuery) || it.last_name.lowercase().contains(lowerCaseQuery) || it.email.lowercase().contains(lowerCaseQuery) || it.id.toString().contains(lowerCaseQuery)
        }
        return filterList
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                val realm = Realm.getDefaultInstance()
                realm.beginTransaction()
                val verifiedUserEntity = realm.where(VerifiedUserEntity::class.java).findFirst()
                verifiedUserEntity?.deleteFromRealm()
                realm.commitTransaction()
                realm.close()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun recoverAndSetRecentSearch() {
        CoroutineScope(Dispatchers.IO).launch {
            var recentSearchList: List<String> = emptyList()
            val realm = Realm.getDefaultInstance()
            val entityList = realm.where(MyEntityList::class.java)
                .equalTo("id", 1)
                .findFirst()

            recentSearchList = entityList?.items?.toList() ?: emptyList()

            realm.close()


            withContext(Dispatchers.Main) {
                if (recentSearchList.isNotEmpty()) {
                    val revesedList = recentSearchList.reversed()
                    val adapter = RecentSearchAdapter(revesedList) {
                        binding.shUser.setQuery(it, true)
                        binding.shUser.clearFocus()
                    }
                    binding.rvRecentSearch.layoutManager =
                        LinearLayoutManager(this@HomeScreenActivity, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvRecentSearch.adapter = adapter
                    binding.rvRecentSearch.visibility = android.view.View.VISIBLE

                    binding.tvRecentSearchLabel.visibility = android.view.View.VISIBLE
                    binding.searchSpace.visibility = android.view.View.VISIBLE
                }
            }
        }
    }

    fun navToDetails(userID: Int){
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(Constants.USER_ID_SELECTED, userID)
        startActivity(intent)
    }
}