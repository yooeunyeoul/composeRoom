package com.dongeul.yogi.github_users.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dongeul.yogi.github_users.domain.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "users_db"
    }
}