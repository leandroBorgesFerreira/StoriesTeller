package br.com.leandroferreira.storyteller.persistence.entity.story

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val STORY_UNIT_ENTITY: String = "STORY_UNIT_ENTITY_TABLE"

@Entity(tableName = STORY_UNIT_ENTITY)
data class StoryUnitEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "parent_id") val parentId: String? = null,
    @ColumnInfo(name = "url") val url: String? = null,
    @ColumnInfo(name = "path") val path: String? = null,
    @ColumnInfo(name = "text") val text: String? = null,
    @ColumnInfo(name = "title") val title: String? = null,
    @ColumnInfo(name = "checked") val checked: Boolean? = false,
    @ColumnInfo(name = "inner_unit_ids") val innerUnitIds: List<String>? = null,
    @ColumnInfo(name = "position") val position: Int,
    @ColumnInfo(name = "document_id") val documentId: String,
    @ColumnInfo(name = "is_group") val isGroup: Boolean,
)
