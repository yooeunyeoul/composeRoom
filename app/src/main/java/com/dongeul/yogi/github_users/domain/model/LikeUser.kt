package com.dongeul.yogi.github_users.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeUser(
    @PrimaryKey val id: Int? = null
) {
    companion object {

    }
}

class InvalidNoteException(message: String) : Exception(message) {

}