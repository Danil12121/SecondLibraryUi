package com.example.libraryui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libraryui.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var adapter: LibAdapter
    private lateinit var viewModel: MainViewModel
    var countOfItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
        initViewModel()

        val rcView = binding.rcvLibraryItems
        rcView.layoutManager = LinearLayoutManager(this)
        rcView.adapter = adapter
    }

    private fun initUi() {
        val spinner = binding.spinner
        val items = resources.getStringArray(R.array.library_items)
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            items
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        var firstOpen = true
        spinner.adapter = spinnerAdapter
        adapter = LibAdapter()
        adapter.submitList(adapter.currentList.plus(Book(100, "dsasd", "String", 1)) as List<LibraryItem?>?)
        adapter.submitList(adapter.currentList.plus(Newspaper(1500, "news2", 19)) as List<LibraryItem?>?)


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                /*if (firstOpen && position == 0) {
                    firstOpen = false
                    return
                }*/
                val selected = parent?.getItemAtPosition(position).toString()
                val intent = Intent(this@MainActivity, FullInfoActivity::class.java)
                when (selected){

                    "Book" ->{
                        intent.putExtra("image", R.drawable.book)
                        intent.putExtra(FullInfoActivity.BOOK, ++countOfItem)
                    }
                    "Disk" -> {
                        intent.putExtra("image", R.drawable.disk)
                        intent.putExtra(FullInfoActivity.DISK, ++countOfItem)
                    }
                    "Newspaper" -> {
                        intent.putExtra("image", R.drawable.newspaper)
                        intent.putExtra(FullInfoActivity.NEWSPAPER, ++countOfItem)
                    }
                    else -> intent.putExtra("image", -1)
                }
                intent.putExtra(FullInfoActivity.BUTTON_TEXT, "Сохранить")
                startActivityForResult(intent, countOfItem)
                adapter.setOnItemClickListener { item ->
                    val intent = Intent(this@MainActivity, FullInfoActivity::class.java).apply{
                        putExtra("libItem", item)
                        when (item){
                            is Book -> putExtra("libItemType", FullInfoActivity.BOOK)
                            is Disk -> putExtra("libItemType", FullInfoActivity.DISK)
                            is Newspaper -> putExtra("libItemType", FullInfoActivity.NEWSPAPER)
                        }
                        putExtra(FullInfoActivity.BUTTON_TEXT, "Назад")
                    }
                    startActivity(intent)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == countOfItem && resultCode == RESULT_OK) {
            val result = data?.getSerializableExtra("resultItem")
//            adapter.add(result as LibraryItem)
            // adapter.submitList(adapter.libList)
            adapter.submitList(adapter.currentList.plus(result) as List<LibraryItem?>?)
            /*Log.d("MainActivity", "Received result: $result")
            for ((index, item) in adapter.libList.withIndex()) {
                Log.d("LIST_LOG", "Element at index $index: $item")
            }*/
        }
    }

    private fun initViewModel(){
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.notes.observe(this){
            notes-> adapter.submitList(notes as List<LibraryItem?>?)
        }
    }
}