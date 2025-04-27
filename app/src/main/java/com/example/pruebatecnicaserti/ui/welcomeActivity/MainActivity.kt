package com.example.pruebatecnicaserti.ui.welcomeActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebatecnicaserti.R
import com.example.pruebatecnicaserti.data.realm.entity.VerifiedUserEntity
import com.example.pruebatecnicaserti.databinding.ActivityMainBinding
import com.example.pruebatecnicaserti.ui.extensions.applyInsetsWithOriginalPadding
import com.example.pruebatecnicaserti.ui.homeActivity.HomeScreenActivity
import com.example.pruebatecnicaserti.ui.loginActivity.LoginActivity
import com.example.pruebatecnicaserti.ui.registerActivity.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.main.applyInsetsWithOriginalPadding()

        CoroutineScope(Dispatchers.Main).launch {
            val isLoggedIn = isUserLoggedIn()
            if (isLoggedIn) {
                startActivity(Intent(this@MainActivity, HomeScreenActivity::class.java))
                finish()
            } else {
                setupView()
            }
        }
    }

    private fun setupView() {



        binding.btRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
        }

        binding.btLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
        }
    }

    private suspend fun isUserLoggedIn(): Boolean = withContext(Dispatchers.IO) {
        val realm = Realm.getDefaultInstance()
        try {
            val user = realm.where(VerifiedUserEntity::class.java)
                .equalTo("id", 1)
                .findFirst()
            user != null
        } finally {
            realm.close()
        }
    }
}
