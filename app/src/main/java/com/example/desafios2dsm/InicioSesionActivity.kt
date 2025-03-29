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

class InicioSesionActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    private lateinit var buttonlogin:Button
    private lateinit var textRegister:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio_sesion)

        auth=FirebaseAuth.getInstance()

        buttonlogin=findViewById(R.id.btnLogin)
        buttonlogin.setOnClickListener{
            val email =findViewById<EditText>(R.id.inputEmail).text.toString()
            val password=findViewById<EditText>(R.id.inputContra).text.toString()
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            this.login(email,password)
        }
        textRegister=findViewById(R.id.textCuentaNueva)
        textRegister.setOnClickListener{this.goToRegister()}

    }
    private fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    Toast.makeText(this, "Inicio de Sesión exitoso", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this,ListadoActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener{exception->
                Toast.makeText(applicationContext,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG

                ).show()
            }
    }

    private fun goToRegister(){
        val intent=Intent(this,RegistroActivity::class.java)
        startActivity(intent)
    }
}
