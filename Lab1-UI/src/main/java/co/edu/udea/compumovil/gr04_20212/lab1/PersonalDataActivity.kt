package co.edu.udea.compumovil.gr04_20212.lab1

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*


class PersonalDataActivity : AppCompatActivity() {
    var editText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_data)
        setTitle(R.string.personal_data_title)

        val res: Resources = resources
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, R.layout.dropdown_item, res.getStringArray(R.array.scolarity_array)
        )
        val textView = findViewById<View>(R.id.scolarityDropdown) as AutoCompleteTextView
        textView.setAdapter(adapter)

        editText = findViewById<View>(R.id.etBirthDate) as EditText
        editText!!.transformIntoDatePicker(this, "dd/MM/yyyy")

        val buttonClick = findViewById<Button>(R.id.btnNextPersonalData)
        buttonClick.setOnClickListener {
            val intent = Intent(this, ContactDataActivity::class.java)
            startActivity(intent)
        }
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
}
