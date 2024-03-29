package net.bouzuya.sample5.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Bookmark::class, BookmarkTag::class, Tag::class],
    version = BookmarkDatabase.version
)
abstract class BookmarkDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao

    abstract fun tagDao(): TagDao

    companion object {
        const val version = 3

        @Volatile
        private var database: BookmarkDatabase? = null

        fun getDatabase(context: Context): BookmarkDatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database"
                )
                    .addMigrations(BookmarkDatabaseMigration1to2())
                    .addMigrations(BookmarkDatabaseMigration2to3())
                    .build().also { db ->
                        database = db
                    }
            }
        }
    }
}
