package post.data

import post.model.Post

interface PostsRepository {
    suspend fun createPost(postDescription: String): Post
    suspend fun deletePost(postId: Int)
    suspend fun getPost(postId: Int): Post?
    suspend fun updatePost(postId: String, postDescription: String)
}