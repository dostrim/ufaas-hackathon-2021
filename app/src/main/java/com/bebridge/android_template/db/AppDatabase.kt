package com.bebridge.android_template.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bebridge.android_template.db.dao.ProductDao
import com.bebridge.android_template.db.dao.UserDao
import com.bebridge.android_template.db.entity.ProductEntity
import com.bebridge.android_template.db.entity.ProductCategoryEntity
import com.bebridge.android_template.db.entity.ProductProductTagEntity
import com.bebridge.android_template.db.entity.ProductTagEntity
import com.bebridge.android_template.db.entity.UserEntity

@Database(
  entities = arrayOf(
    ProductEntity::class,
    ProductCategoryEntity::class,
    ProductTagEntity::class,
    ProductProductTagEntity::class,
    UserEntity::class
  ), version = 2
)
abstract class AppDatabase : RoomDatabase() {

  abstract fun productDao(): ProductDao

  abstract fun userDao(): UserDao
}

val MIGRATION_2_3 = object : Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {
    //database.execSQL("")
  }
}