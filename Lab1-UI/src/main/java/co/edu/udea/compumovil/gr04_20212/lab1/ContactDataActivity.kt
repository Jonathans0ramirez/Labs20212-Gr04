package co.edu.udea.compumovil.gr04_20212.lab1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ContactDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_data)
        setTitle(R.string.contact_data_title)
        val buttonClick = findViewById<Button>(R.id.btnNextContactData)
        buttonClick.setOnClickListener {
            val intent = Intent(this, PersonalDataActivity::class.java)
            startActivity(intent)
        }
    }
}
