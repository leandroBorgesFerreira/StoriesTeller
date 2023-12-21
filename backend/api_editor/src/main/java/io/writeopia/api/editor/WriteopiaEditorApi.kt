package io.writeopia.api.editor

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import io.writeopia.sdk.models.document.Document
import io.writeopia.sdk.persistence.core.repository.DocumentRepository
import io.writeopia.sdk.persistence.sqldelight.dao.DocumentSqlDao
import io.writeopia.sdk.persistence.sqldelight.dao.sql.SqlDelightDocumentRepository
import io.writeopia.sdk.serialization.data.DocumentApi
import io.writeopia.sdk.serialization.extensions.toApi
import io.writeopia.sdk.serialization.extensions.toModel
import io.writeopia.sdk.sql.WriteopiaDb
import java.util.Properties

class WriteopiaEditorApi(
    private val documentRepository: DocumentRepository
) {

    suspend fun saveDocument(document: DocumentApi) {
        documentRepository.saveDocument(document.toModel())
    }

    suspend fun getDocument(id: String): DocumentApi? =
        documentRepository.loadDocumentById(id)?.toApi()

    companion object {
        private fun createDatabase(
            url: String = JdbcSqliteDriver.IN_MEMORY
        ): WriteopiaDb {
            val driver = JdbcSqliteDriver(url, Properties(), WriteopiaDb.Schema)
            return WriteopiaDb(driver)
        }

        fun create(): WriteopiaEditorApi {
            val database: WriteopiaDb = createDatabase()
            val documentSqlDao = DocumentSqlDao(
                database.documentEntityQueries,
                database.storyStepEntityQueries
            )
            val documentRepository = SqlDelightDocumentRepository(documentSqlDao)
            return WriteopiaEditorApi(documentRepository)
        }
    }
}