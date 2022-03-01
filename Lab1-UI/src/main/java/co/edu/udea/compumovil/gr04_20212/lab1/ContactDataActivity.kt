package co.edu.udea.compumovil.gr04_20212.lab1

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.google.android.material.textfield.TextInputLayout


class ContactDataActivity : AppCompatActivity() {
    private var cityAC: AutoCompleteTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_data)
        setTitle(R.string.contact_data_title)

        val tilPhone = findViewById<TextInputLayout>(R.id.tilPhone)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val tilEmail = findViewById<TextInputLayout>(R.id.tilEmail)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val tilCountry = findViewById<TextInputLayout>(R.id.tilCountry)
        val etCountry = findViewById<AutoCompleteTextView>(R.id.etCountry)

        tilPhone.markRequired()
        tilEmail.markRequired()
        tilCountry.markRequired()

        etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(etPhone.text)) tilPhone.setErrorMessage(getString(R.string.phone_validator))
            }
        })
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(etEmail.text)) tilEmail.setErrorMessage(getString(R.string.email_validator))
            }
        })
        etCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                print(etCountry.text)
                if (TextUtils.isEmpty(etCountry.text)) tilCountry.setErrorMessage(getString(R.string.country_validator))
            }
        })

        val buttonClick = findViewById<Button>(R.id.btnNextContactData)
        buttonClick.setOnClickListener {
            if (fieldsValidation()) {
                val intent = Intent(this, PersonalDataActivity::class.java)
                startActivity(intent)
            }
        }

        val res: Resources = resources
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.dropdown_item, res.getStringArray(R.array.latam_countries)
        )
        etCountry.setAdapter(adapter)

        val remoteData = RemoteData(this)
        remoteData.storeData

        cityAC = findViewById<View>(R.id.etCity) as AutoCompleteTextView
        cityAC!!.onItemClickListener = onItemClickListener
    }

    private fun fieldsValidation(): Boolean {
        if (TextUtils.isEmpty(findViewById<EditText>(R.id.etName).text) ||
            TextUtils.isEmpty(findViewById<EditText>(R.id.etLastname).text) ||
            TextUtils.isEmpty(findViewById<EditText>(R.id.etBirthDate).text)
        ) {
            Toast.makeText(
                this@ContactDataActivity, "Faltan Campos por llenar, no puedes continuar", Toast.LENGTH_SHORT
            ).show()
            return false
        }

        var message = "\n**Información de Contacto**\nTeléfono: ${findViewById<EditText>(R.id.tilPhone).text}"
        message = concatenateStrings(message, findViewById(R.id.etAddress), "Dirección: ")
        message = concatenateStrings(message, findViewById(R.id.etEmail), "Email:  ")
        message = concatenateStrings(message, findViewById(R.id.etCountry), "País: ")
        message = concatenateStrings(message, findViewById(R.id.etCity), "Ciudad: ")
        Log.i(
            "FORMS PRINT",
            message
        )
        println(message)
        return true
    }

    fun TextInputLayout.setErrorMessage(errorMessage: String) {
        error = errorMessage
    }

    fun TextInputLayout.markRequired() {
        hint = buildSpannedString {
            color(Color.RED) { append("* ") }
            append(hint)
        }
    }

    private fun concatenateStrings(actual: String, editText: EditText, prefixText: String = ""): String {
        return if (TextUtils.isEmpty(editText.text)) {
            actual
        } else {
            "$actual\n${prefixText}${editText.text}"
        }
    }

    private val onItemClickListener =
        OnItemClickListener { adapterView, _, i, _ ->
            Toast.makeText(
                this@ContactDataActivity, "Clicked item "
                        + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT
            ).show()
        }
}
