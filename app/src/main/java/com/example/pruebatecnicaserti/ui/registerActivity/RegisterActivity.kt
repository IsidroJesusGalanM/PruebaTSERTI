package com.example.pruebatecnicaserti.ui.registerActivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.pruebatecnicaserti.R
import com.example.pruebatecnicaserti.databinding.ActivityRegisterBinding
import com.example.pruebatecnicaserti.model.remote.register.RegisterRequest
import com.example.pruebatecnicaserti.repository.RegisterRepository
import com.example.pruebatecnicaserti.ui.extensions.applyInsetsWithOriginalPadding
import com.example.pruebatecnicaserti.ui.loginActivity.LoginActivity
import com.example.pruebatecnicaserti.ui.welcomeActivity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    @Inject
    lateinit var registerRepository: RegisterRepository

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.main.applyInsetsWithOriginalPadding()

        binding.btBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btRegister.setOnClickListener {
            val request = RegisterRequest(
                email = binding.etUserRegister.text.toString().trim(),
                password = binding.etPasswordRegister.text.toString().trim()
            )

            showDialog()

            CoroutineScope(Dispatchers.IO).launch {
                val result = registerRepository.registerUser(request)
                if (result.isSuccess){
                    authSuccessDialog()
                }else{
                    authFailDialog()
                    val errorMessage = result.exceptionOrNull()?.message ?: "Error desconocido"
                    Log.d("APITEST",errorMessage)
                }
            }
        }

    }


    fun showDialog(){
        runOnUiThread {
            binding.loadingRegisterLayer.loadingView.visibility = View.VISIBLE
            binding.loadingRegisterLayer.progressBar.visibility = View.VISIBLE
            binding.loadingRegisterLayer.ivError.visibility = View.GONE
            binding.loadingRegisterLayer.btnRetry.visibility = View.GONE
            binding.loadingRegisterLayer.tvMessage.text = "Registrando..."
        }
    }

    fun authFailDialog(){
        runOnUiThread {
            binding.loadingRegisterLayer.progressBar.visibility = View.INVISIBLE
            binding.loadingRegisterLayer.ivError.visibility = View.VISIBLE
            binding.loadingRegisterLayer.ivSuccess.visibility = View.GONE
            binding.loadingRegisterLayer.btnRetry.visibility = View.VISIBLE
            binding.loadingRegisterLayer.tvMessage.text = "Usuario o contraseña incorrectos :("
            binding.loadingRegisterLayer.btnRetry.setOnClickListener {
                dismissDialog()
            }
        }
    }

    fun authSuccessDialog(){
        runOnUiThread {
            binding.loadingRegisterLayer.progressBar.visibility = View.INVISIBLE
            binding.loadingRegisterLayer.ivError.visibility = View.GONE
            binding.loadingRegisterLayer.ivSuccess.visibility = View.VISIBLE
            binding.loadingRegisterLayer.btnRetry.visibility = View.VISIBLE
            binding.loadingRegisterLayer.tvMessage.text = "Usuario Registrado :)"
            binding.loadingRegisterLayer.btnRetry.text = "Ir a Login"
            binding.loadingRegisterLayer.btnRetry.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    fun dismissDialog(){
        runOnUiThread {
            binding.loadingRegisterLayer.loadingView.visibility = View.GONE
        }
    }
}