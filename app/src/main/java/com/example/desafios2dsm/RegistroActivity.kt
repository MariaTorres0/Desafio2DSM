package com.example.desafios2dsm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var textLogin:TextView
    private lateinit var buttonRegister:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth=FirebaseAuth.getInstance()

        buttonRegister=findViewById(R.id.btnRegister)
        buttonRegister.setOnClickListener {
            val email=findViewById<EditText>(R.id.inputEmailRegister).text.toString()
            val password=findViewById<EditText>(R.id.inputContraRegister).text.toString()
            this.register(email,password)
        }
        textLogin=findViewById(R.id.textAlreadyAccount)
        textLogin.setOnClickListener{
            this.goToLogin()
        }
    }

    private fun register(email:String,password:String){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    val intent= Intent(this,InicioSesionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener{exception->
                Toast.makeText(applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG).show()
            }
    }
    private fun goToLogin(){
        val intent=Intent(this,InicioSesionActivity::class.java)
        startActivity(intent)
    }
}
