package exomind.online.jpmpottertask.data.characters.network
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    @SerializedName("actor")
    val actor: String,
    @SerializedName("alive")
    val alive: Boolean,
    @SerializedName("alternate_actors")
    val alternateActors: List<String>,
    @SerializedName("alternate_names")
    val alternateNames: List<String>,
    @SerializedName("ancestry")
    val ancestry: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("eyeColour")
    val eyeColour: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("hairColour")
    val hairColour: String,
    @SerializedName("hogwartsStaff")
    val hogwartsStaff: Boolean,
    @SerializedName("hogwartsStudent")
    val hogwartsStudent: Boolean,
    @SerializedName("house")
    val house: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("patronus")
    val patronus: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("wand")
    val wand: Wand,
    @SerializedName("wizard")
    val wizard: Boolean,
    @SerializedName("yearOfBirth")
    val yearOfBirth: Int,
) {
    @Serializable
    data class Wand(
        @SerializedName("core")
        val core: String,
        @SerializedName("length")
        val length: Double,
        @SerializedName("wood")
        val wood: String,
    )
}
