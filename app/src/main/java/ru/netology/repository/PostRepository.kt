package ru.netology.repository

import androidx.lifecycle.LiveData
import ru.netology.dbo.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
}