package br.com.leandroferreira.storyteller.persistence.repository

import br.com.leandroferreira.storyteller.persistence.parse.toEntity
import br.com.leandroferreira.storyteller.persistence.parse.toModel
import br.com.leandroferreira.storyteller.model.document.Document
import br.com.leandroferreira.storyteller.persistence.dao.DocumentDao
import br.com.leandroferreira.storyteller.persistence.dao.StoryUnitDao
import br.com.leandroferreira.storyteller.persistence.entity.document.DocumentEntity

/**
 * Evaluate to move this class to persistence module
 */
class DocumentRepository(
    private val documentDao: DocumentDao,
    private val storyUnitDao: StoryUnitDao
) {

    suspend fun loadDocuments(): List<DocumentEntity> = documentDao.loadAllDocuments()

    suspend fun loadDocumentBy(id: String): Document? {
        return documentDao.loadDocumentWithContent(id)?.map { (documentEntity, storyEntity) ->
            val content = storyEntity
                .filter { entity -> entity.parentId == null }
                .sortedBy { entity -> entity.position } //Todo: Move this to the SQL query
                .associateBy { entity -> entity.position }
                .mapValues { (_, entity) ->
                    if(entity.hasInnerSteps) {
                        val innerSteps = storyUnitDao.queryInnerSteps(entity.id)

                        entity.toModel(innerSteps)
                    } else {
                        entity.toModel()
                    }
                }

            documentEntity.toModel(content)
        }?.firstOrNull()
    }

    suspend fun saveDocument(document: Document) {
        documentDao.insertDocuments(document.toEntity())

        document.content?.toEntity(document.id)?.let { data ->
            storyUnitDao.deleteDocumentContent(documentId = document.id)
            storyUnitDao.insertStoryUnits(*data.toTypedArray())
        }
    }

}
