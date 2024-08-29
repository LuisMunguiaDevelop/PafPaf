
package com.example.pafpaf
/*
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pafpaf.database.AlbumDAO
import com.example.pafpaf.database.AppDatabase
import com.example.pafpaf.database.entities.AlbumEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: AlbumDAO


    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // Only for testing, disable in production
            .build()

        dao = db.albumDao()
    }

    @Test
    @Throws(Exception::class)
    @Ignore("luego")
    fun readAlbumsTest() = runBlocking {
        val dbUtil = DatabaseTestUtil(dao)

        dbUtil.insertThreeTestAlbums()

        //Read the albums data
        val albums = dao.getAll()


        println("albums in database: $albums")
        assert(1 == 1)
    }

    @Test
    @Throws(Exception::class)
    @Ignore("luego")
    fun getAlbumByIdTest() = runBlocking {
        val dbUtil = DatabaseTestUtil(dao)

        dbUtil.insertThreeTestAlbums()

        //get the album with id "2"
        val album = dao.getAlbumById(2)
        println("album found: ${album}")
        assert(album.id == 2)
    }

    @Test
    @Throws(Exception::class)
    fun deleteAlbumTest(){
        val dbUtil = DatabaseTestUtil(dao)

        dbUtil.insertThreeTestAlbums()

        //get the album with id "2"
        val album = dao.getAlbumById(2)

        //delete album founded
        dao.delete(album)
        println(dao.getAll())

        assert(1 == 2)
    }

    @Test
    @Throws(Exception::class)
    @Ignore("luego")
    fun insertAlbumTest() = runBlocking {
        val album = AlbumEntity(title = "Album numero uno")
        dao.insert(album)
        val albums = dao.getAll()
        val albumInserted = albums.first().size
        println(albumInserted.toString())
        assert(1 == 3)
    }




    @After
    @Throws(IOException::class)
    fun closeDb() {
        // ... your cleanup logic here ...
        if (::db.isInitialized) { // Check if db is initialized
            db.close()
            println("Database closed!")
        } else {
            println("Database not initialized yet!")
        }
    }
}*/
