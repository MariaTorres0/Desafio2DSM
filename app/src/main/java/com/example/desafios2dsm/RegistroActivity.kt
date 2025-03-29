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
            val nombre=findViewById<EditText>(R.id.inputNombre).text.toString()
            val apellido=findViewById<EditText>(R.id.inputApellido).text.toString()
            val email=findViewById<EditText>(R.id.inputEmailRegister).text.toString()
            val password=findViewById<EditText>(R.id.inputContraRegister).text.toString()
            val repassword=findViewById<EditText>(R.id.inputRepContra).text.toString()

            if (nombre.isEmpty()||apellido.isEmpty()||email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                Toast.makeText(this, "❌ Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != repassword) {
                Toast.makeText(this, "❌ Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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
                    Toast.makeText(this, "✅ Registro exitoso", Toast.LENGTH_SHORT).show()
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
