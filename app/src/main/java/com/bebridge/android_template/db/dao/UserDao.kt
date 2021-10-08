package com.bebridge.android_template.db.dao


import androidx.room.*
import com.bebridge.android_template.db.entity.UserEntity
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM user")
    fun selectUsers(): Single<List<UserEntity>>

    @Query("SELECT * FROM user WHERE userId = :id")
    fun selectById(id: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserEntity>)

    @Delete
    fun delete(user: UserEntity)
}