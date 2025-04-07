package com.example.libraryui

class Newspaper(
    id: Int,
    val title: String,
    val releaseNumber: Int

) : LibraryItem(imageID=R.drawable.newspaper, id=id, isEnable=true, name="Newspaper") {
}