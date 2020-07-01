package post.data.model


import database.table.Posts
import org.jetbrains.exposed.sql.ResultRow

data class Post(
        val id: Int,
        val description: String
) {
    constructor(row: ResultRow) : this(
            id = row[Posts.id],
            description = row[Posts.title]
    )
}
