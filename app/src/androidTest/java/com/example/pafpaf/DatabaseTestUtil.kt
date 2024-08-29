package com.example.pafpaf

import com.example.pafpaf.database.AlbumDAO
import com.example.pafpaf.database.entities.AlbumEntity

class DatabaseTestUtil(private val albumDAO: AlbumDAO){

    fun insertThreeTestAlbums(){
        //Create the data
        val album1 = AlbumEntity(title = "Album numero uno")
        albumDAO.insert(album1)
        println("inserted into db: $album1")

        val album2 = AlbumEntity(title = "Album numero dos")
        albumDAO.insert(album2)
        println("inserted into db: $album2")

        val album3 = AlbumEntity(title = "Album numero tres")
        albumDAO.insert(album3)
        println("inserted into db: $album3")
    }
}