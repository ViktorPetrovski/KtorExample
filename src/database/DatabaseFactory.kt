package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import database.table.Posts
import doc.ktor.database.table.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.ThreadLocalTransactionManager
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

private const val DB_URL = "eu-cdbr-west-03.cleardb.net"
private const val DB_NAME = "heroku_054dca9aa384a5a"
private const val DB_USERNAME = "bdb23213e8b710"
private const val DB_PASSWORD = "6c25bd8a"

object DatabaseFactory {
    fun init() {
        // Connect the DB
        Database.connect(HikariDataSource(dbConfig))

        transaction {
            SchemaUtils.create(Posts)
            SchemaUtils.create(Users)
        }
    }

    val dbConfig = HikariConfig().apply {
        jdbcUrl         = "jdbc:mysql://$DB_URL/$DB_NAME"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username        = DB_USERNAME
        password        = DB_PASSWORD
        maximumPoolSize = 1
    }
}
