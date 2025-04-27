package com.example.pruebatecnicaserti.ui.userDetailActivity

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.pruebatecnicaserti.R
import com.example.pruebatecnicaserti.databinding.ActivityUserDetailsBinding
import com.example.pruebatecnicaserti.repository.UsersRepository
import com.example.pruebatecnicaserti.ui.extensions.applyInsetsWithOriginalPadding
import com.example.pruebatecnicaserti.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var usersRepository: UsersRepository

    private lateinit var binding: ActivityUserDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        val upArrow = ContextCompat.getDrawable(this, R.drawable.arrow_back)
        upArrow?.setTint(Color.WHITE)
        supportActionBar?.setHomeAsUpIndicator(upArrow)

        val userId = intent.getIntExtra(Constants.USER_ID_SELECTED, -1)
        if (userId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val userData = usersRepository.getSingleUser(userId)
                if (userData.isSuccess) {
                    val user = userData.getOrNull()?.data
                    if (user != null) {
                        runOnUiThread {
                            supportActionBar?.title = "${user.first_name} #${user.id}"
                            supportActionBar?.setDisplayHomeAsUpEnabled(true)
                            binding.tvUserName.text = user.first_name
                            binding.tvLastName.text = user.last_name
                            binding.tvEmailLabel.text = "Si deseas comunicarte con ${user.first_name}:"
                            binding.tvEmail.text = user.email
                            Glide
                                .with(this@UserDetailsActivity)
                                .load(user.avatar)
                                .centerCrop()
                                .into(binding.ivUser)

                        }
                    }
                }else{
                    runOnUiThread {
                        Toast.makeText(this@UserDetailsActivity, "Sin conexión a internet :(", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }else{
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}