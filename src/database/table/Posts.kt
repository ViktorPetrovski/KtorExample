package database.table

import org.jetbrains.exposed.sql.Table

object Posts : Table() {
    val id = integer("id").autoIncrement() // Column<Int>
    val title = varchar("title", 100) // Column<String>
    override val primaryKey = PrimaryKey(id, name = "PK_Cities_ID")
}