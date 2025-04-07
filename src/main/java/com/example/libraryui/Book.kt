package com.example.libraryui
class Book(
    id: Int,
    val title: String,
    val author: String,
    val pageCount: Int,

    ) : LibraryItem(imageID=R.drawable.book, id=id, isEnable=true, name="Book") {
}