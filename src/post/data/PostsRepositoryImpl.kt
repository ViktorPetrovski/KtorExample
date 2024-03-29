package post.data
import database.table.Posts
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import post.data.model.Post
import java.lang.IllegalStateException
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class PostsRepositoryImpl : PostsRepository {

    override suspend fun createPost(postDescription: String): Post {
        var insertedRow: InsertStatement<Number>? = null

        transaction {
            insertedRow = Posts.insert { post ->
                post[title] = postDescription
            }
        }
        val insertedPostRow = insertedRow?.resultedValues?.get(0) ?: throw IllegalStateException("Unable to create post.")
        return Post(insertedPostRow)
    }

    override suspend fun deletePost(postId: Int) {
        transaction {
            Posts.deleteWhere { Posts.id.eq(postId) }
        }
    }

    override suspend fun getPost(postId: Int) = transaction {
        Posts.select { Posts.id.eq(postId) }.map { Post(it) }.singleOrNull()
    }

    override suspend fun updatePost(postId: Int, postDescription: String) {
        transaction {
            Posts.update({ Posts.id.eq(postId) }) { it[title] = postDescription }
        }
    }
}