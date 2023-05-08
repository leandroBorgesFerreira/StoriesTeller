package br.com.leandroferreira.storyteller.model.story

/**
 * The model defining the information that can be draw in the screen. This is the most basic
 * building block of the library and can have many types like image, message, audio, video,
 * button, empty space, etc.
 */
data class StoryStep(
    override val id: String,
    override val type: String,
    override val parentId: String? = null,
    val url: String? = null,
    val path: String? = null,
    val text: String? = null,
    val title: String? = null,
    val checked: Boolean? = false,
    val steps: List<StoryUnit> = emptyList()
): StoryUnit {

    override val key: Int = hashCode()

    override fun copyWithNewParent(parentId: String?): StoryUnit = copy(parentId = parentId)

    override fun copyWithNewId(id: String): StoryUnit = copy(id = id)

    override val isGroup: Boolean = false
}
