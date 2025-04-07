package com.example.libraryui

class Disk(
    id: Int,
    val title: String,
    val diskType: String

) : LibraryItem(imageID=R.drawable.disk, id=id, isEnable=true, name="Disk") {
}