package com.example.libraryui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

import com.example.libraryui.databinding.FullinfoActivityBinding
import kotlin.properties.Delegates

class FullInfoActivity : Activity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = FullinfoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val et_Title = binding.etTitle
        val et_FirstVar = binding.etFirstVar
        val et_SecondVar = binding.etSecondVar
        val saveButton = binding.saveButton

        if (intent.getStringExtra(BUTTON_TEXT) == "Сохранить") {
            val image = intent.getIntExtra("image", -1)
            binding.ivFullInfo.setImageResource(image)
            saveButton.setText(intent.getStringExtra(BUTTON_TEXT))

            if (intent.hasExtra(BOOK)){
                et_SecondVar.setVisibility(View.VISIBLE)
                et_FirstVar.hint = "Введите автора"
                et_SecondVar.hint = "Введите кол-во страниц"
            }
            if (intent.hasExtra(DISK)){
                et_FirstVar.hint = "Введите тип CD или DVD"
            }
            if (intent.hasExtra(NEWSPAPER)){
                et_FirstVar.hint = "Введите номер выпуска"
            }
            et_Title.hint = "Введите название"
        }
        if (intent.getStringExtra(BUTTON_TEXT) == "Назад") {
            var image by Delegates.notNull<Int>()
            when (intent.getStringExtra("libItemType")) {
                BOOK -> {
                    val item = intent.getSerializableExtra("libItem") as Book
                    image = item.imageID
                    et_Title.setText("книга: ${item.title} автор ${item.author} (${item.pageCount} Страниц)")
                    et_FirstVar.setText("Доступна id:${item.id}")

                }

                DISK -> {
                    val item = intent.getSerializableExtra("libItem") as Disk
                    image = item.imageID
                    et_Title.setText("диск: ${item.title} Тип: ${item.diskType}")
                    et_FirstVar.setText("Доступен id:${item.id}")
                }

                NEWSPAPER -> {
                    val item = intent.getSerializableExtra("libItem") as Newspaper
                    image = item.imageID
                    et_Title.setText("газета: ${item.title} номер ${item.releaseNumber}")
                    et_FirstVar.setText("Доступна id:${item.id}")
                }

                else -> {
                    image = -1
                }

            }
            binding.ivFullInfo.setImageResource(image)
            saveButton.setVisibility(View.INVISIBLE)

            et_FirstVar.isEnabled = false
            et_FirstVar.isFocusable = false
            et_FirstVar.isClickable = false

            et_Title.isEnabled = false
            et_Title.isFocusable = false
            et_Title.isClickable = false

        }
        saveButton.setOnClickListener {
            var flag = true
            if (et_Title.text.isEmpty() || et_FirstVar.text.isEmpty())
                flag = false
            if (intent.hasExtra(BOOK) && et_SecondVar.text.isEmpty())
                flag = false
            if (intent.hasExtra(DISK) && !(et_FirstVar.text.toString() == "CD" || et_FirstVar.text.toString() == "DVD"))
                flag = false
            if (flag) {
                lateinit var item: LibraryItem
                if (intent.hasExtra(BOOK)) {
                    item = Book(
                        intent.getIntExtra(BOOK, -1),
                        et_Title.text.toString(),
                        et_FirstVar.text.toString(),
                        et_SecondVar.text.toString().toInt()
                    )
                } else if (intent.hasExtra(DISK)) {
                    item = Disk(
                        intent.getIntExtra(DISK, -1),
                        et_Title.text.toString(),
                        et_FirstVar.text.toString()
                    )
                } else if (intent.hasExtra(NEWSPAPER)) {
                    item = Newspaper(
                        intent.getIntExtra(NEWSPAPER, -1),
                        et_Title.text.toString(),
                        et_FirstVar.text.toString().toInt()
                    )
                }
                val resultData = Intent()
                resultData.putExtra("resultItem", item)
                setResult(RESULT_OK, resultData)
                finish()
            } else {
                Log.d("MainActivity", "Received result wrong")
            }

        }
    }

    companion object {
        const val BOOK = "book"
        const val DISK = "disk"
        const val NEWSPAPER = "newspaper"
        const val BUTTON_TEXT = "buttonText"

        fun createIntent(context: Context, type: String): Intent {
            return Intent(context, FullInfoActivity::class.java)
        }
    }
}