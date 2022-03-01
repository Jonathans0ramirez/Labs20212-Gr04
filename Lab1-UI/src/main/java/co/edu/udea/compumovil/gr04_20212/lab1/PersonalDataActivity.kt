package co.edu.udea.compumovil.gr04_20212.lab1

import android.app.DatePickerDialog
import android.content.Context
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*


class PersonalDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        setTitle(R.string.personal_data_title)

        val tilName = findViewById<TextInputLayout>(R.id.tilName)
        val etName = findViewById<EditText>(R.id.etName)
        val tilLastName = findViewById<TextInputLayout>(R.id.tilLastname)
        val etLastName = findViewById<EditText>(R.id.etLastname)
        val tilBirthDate = findViewById<TextInputLayout>(R.id.tilBirthdate)
        val etBirthDate = findViewById<EditText>(R.id.etBirthDate)

        tilName.markRequired()
        tilLastName.markRequired()
        tilBirthDate.markRequired()

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(etName.text)) {
                    tilName.setErrorMessage(getString(R.string.phone_validator))
                } else {
                    tilName.setErrorMessage("")
                }
            }
        })
        etLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(etLastName.text)) {
                    tilLastName.setErrorMessage(getString(R.string.phone_validator))
                } else {
                    tilLastName.setErrorMessage("")
                }
            }
        })
        etBirthDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (TextUtils.isEmpty(etBirthDate.text)) {
                    tilBirthDate.setErrorMessage(getString(R.string.phone_validator))
                } else {
                    tilBirthDate.setErrorMessage("")
                }
            }
        })

        val res: Resources = resources
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.dropdown_item, res.getStringArray(R.array.scolarity_array)
        )
        val scolarity = findViewById<View>(R.id.scolarityDropdown) as AutoCompleteTextView
        scolarity.setAdapter(adapter)

        etBirthDate!!.transformIntoDatePicker(this, "dd/MM/yyyy")

        val buttonClick = findViewById<Button>(R.id.btnNextPersonalData)
        buttonClick.setOnClickListener {
            if (fieldsValidation()) {
                val intent = Intent(this, ContactDataActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun fieldsValidation(): Boolean {
        if (TextUtils.isEmpty(findViewById<EditText>(R.id.etName).text) ||
            TextUtils.isEmpty(findViewById<EditText>(R.id.etLastname).text) ||
            TextUtils.isEmpty(findViewById<EditText>(R.id.etBirthDate).text)
        ) {
            Toast.makeText(
                this@PersonalDataActivity, "Faltan Campos por llenar, no puedes continuar", Toast.LENGTH_SHORT
            ).show()
            return false
        }

        var message =
            "\n**Información Personal**\n${findViewById<EditText>(R.id.etName).text} ${findViewById<EditText>(R.id.etLastname).text}"
        message = concatenateStrings(message, findViewById<RadioGroup>(R.id.radioSexGroup))
        message = concatenateStrings(message, findViewById<EditText>(R.id.etBirthDate), "Nació el ")
        message = concatenateStrings(message, findViewById<EditText>(R.id.scolarityDropdown))
        Log.i(
            "FORMS PRINT",
            message
        )
        println(message)
        return true
    }

    fun EditText.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.UK)
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
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

    private fun concatenateStrings(actual: String, radioGroup: RadioGroup, prefixText: String = ""): String {
        return if (radioGroup.checkedRadioButtonId == -1) {
            actual
        } else {
            val selectedId: Int = radioGroup.checkedRadioButtonId
            val radioSexButton = findViewById<View>(selectedId) as RadioButton
            "$actual\n${prefixText}${radioSexButton.text}"
        }
    }
}
