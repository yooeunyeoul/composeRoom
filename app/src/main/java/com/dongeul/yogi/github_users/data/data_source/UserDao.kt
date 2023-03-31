package com.dongeul.yogi.github_users.data.data_source

import android.util.Log
import androidx.room.*
import com.dongeul.yogi.github_users.domain.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getUsers(): Flow<List<User>>

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("UPDATE User SET isLike =:isLike WHERE id=:id ")
    suspend fun userLikeUpdate(id: Int, isLike: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User): Long

    @Query("UPDATE User SET avatarUrl= :avatarUrl,htmlUrl= :htmlUrl,login= :login WHERE id=:id ")
    suspend fun updateUser(avatarUrl: String, htmlUrl: String, login: String, id: Int): Int

    @Transaction
    suspend fun insertOrUpdateUsers(users: List<User>) {
        users.forEach { user ->
            if (insertUser(user) < 1) {
                Log.e("asdfasdfasdf","asdfasdfsadfsadfsadfasdf")
                updateUser(
                    avatarUrl = user.avatarUrl ?: "",
                    htmlUrl = user.htmlUrl ?: "",
                    login = user.login ?: "",
                    id = user.id?:return
                )
            }
        }
    }


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

}