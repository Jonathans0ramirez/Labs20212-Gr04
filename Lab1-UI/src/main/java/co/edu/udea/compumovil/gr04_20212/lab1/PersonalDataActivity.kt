package co.edu.udea.compumovil.gr04_20212.lab1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class PersonalDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        val spinner = findViewById<Spinner>(R.id.spnStudies)

        val listStudies = listOf("Primaria", "Secundaria", "Universitaria", "Otro")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listStudies)
        spinner.adapter = adapter
    }
}