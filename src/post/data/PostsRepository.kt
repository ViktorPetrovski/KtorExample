package post.data

interface PostsRepository {
    suspend fun createPost(postDescription: String)
    suspend fun deletePost(postId: String)
    suspend fun getPost(postId: String)
    suspend fun updatePost(postId: String, postDescription: String)
}