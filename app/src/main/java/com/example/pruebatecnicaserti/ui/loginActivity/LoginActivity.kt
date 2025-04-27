package com.example.pruebatecnicaserti.ui.loginActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.pruebatecnicaserti.data.realm.entity.VerifiedUserEntity
import com.example.pruebatecnicaserti.databinding.ActivityLoginBinding
import com.example.pruebatecnicaserti.model.remote.login.LoginRequest
import com.example.pruebatecnicaserti.repository.LoginRepository
import com.example.pruebatecnicaserti.ui.extensions.applyInsetsWithOriginalPadding
import com.example.pruebatecnicaserti.ui.homeActivity.HomeScreenActivity
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginRepository: LoginRepository


    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.main.applyInsetsWithOriginalPadding()

        binding.btBack.setOnClickListener {
            finish()
        }

        binding.btLogin.setOnClickListener {

            showDialog()

            val request = LoginRequest(
                email = binding.etUser.text.toString().trim(),
                password = binding.etPassword.text.toString().trim()
            )
            CoroutineScope(Dispatchers.IO).launch {
                val result = loginRepository.loginUser(request)
                if (result.isSuccess) {

                    val verifiedUserEntity = VerifiedUserEntity().apply {
                        id = 1
                        email = request.email
                        token = result.getOrNull()?.token ?: ""
                    }
                    val realm = Realm.getDefaultInstance()

                    realm.beginTransaction()

                    realm.copyToRealmOrUpdate(verifiedUserEntity)

                    realm.commitTransaction()

                    realm.close()

                    Log.d("LoginActivity", "Usuario verificado: $verifiedUserEntity")

                    val intent = Intent(this@LoginActivity, HomeScreenActivity::class.java)
                    startActivity(intent)
                } else {
                    authFailDialog()
                    val error = result.exceptionOrNull()
                    error?.printStackTrace()
                    Log.e("LoginActivity", "Error", error)
                }
            }
        }
    }

    fun showDialog(){
        runOnUiThread {
            binding.mainLayerLoading.loadingView.visibility = View.VISIBLE
            binding.mainLayerLoading.progressBar.visibility = View.VISIBLE
            binding.mainLayerLoading.ivError.visibility = View.GONE
            binding.mainLayerLoading.btnRetry.visibility = View.GONE
            binding.mainLayerLoading.tvMessage.text = "Validando..."
        }
    }

    fun authFailDialog(){
        runOnUiThread {
            binding.mainLayerLoading.progressBar.visibility = View.INVISIBLE
            binding.mainLayerLoading.ivError.visibility = View.VISIBLE
            binding.mainLayerLoading.btnRetry.visibility = View.VISIBLE
            binding.mainLayerLoading.tvMessage.text = "Usuario o contraseña incorrectos :("
            binding.mainLayerLoading.btnRetry.setOnClickListener {
                dismissDialog()
            }
        }
    }

    fun dismissDialog(){
        runOnUiThread {
            binding.mainLayerLoading.loadingView.visibility = View.GONE
        }
    }
}