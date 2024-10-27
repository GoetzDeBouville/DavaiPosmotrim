package com.davay.android.utils

import com.davay.android.core.domain.models.User

class SorterList {
    fun sortStringUserList(users: List<String>, userName: String): List<String> {
        val userList = users.toMutableList()
        val iterator = userList.iterator()

        while (iterator.hasNext()) {
            val user = iterator.next()
            if (user == userName) {
                iterator.remove()
                userList.add(0, user)
                break
            }
        }
        return userList
    }

    fun sortUserList(users: List<User>, userName: String): List<User> {
        val userList = users.toMutableList()
        val iterator = userList.iterator()
        while (iterator.hasNext()) {
            val user = iterator.next()
            if (user.name == userName) {
                iterator.remove()
                userList.add(0, user)
                break
            }
        }
        return userList
    }
}