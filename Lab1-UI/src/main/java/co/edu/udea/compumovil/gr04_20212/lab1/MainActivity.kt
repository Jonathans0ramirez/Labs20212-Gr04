package co.edu.udea.compumovil.gr04_20212.lab1

import android.content.res.Configuration
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)

        val spinner = findViewById<Spinner>(R.id.spnStudies)

        val listStudies = listOf("Primaria", "Secundaria", "Universitaria", "Otro")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listStudies)
        spinner.adapter = adapter

        // Orientation

        var linealLayoutName = findViewById<LinearLayout>(R.id.name_area)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // landscape
            linealLayoutName.orientation = LinearLayout.HORIZONTAL;
        } else {
            // portrait
            linealLayoutName.orientation  = LinearLayout.VERTICAL;
        }
    }
}