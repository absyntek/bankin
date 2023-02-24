package com.example.bankintest.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

/*** Reference technique
 * @author Etienne
 * @since 24/02/2023
 * @see <a href="TODO">Spec</a>
 * @see <a href="TODO">CT</a>
 */

@kotlinx.serialization.Serializable
data class MainModel(
    val resources : List<Category>
)

@Entity(tableName = "category")
@kotlinx.serialization.Serializable
data class Category(
    @PrimaryKey
    val id: Long,
    @SerialName("resource_uri")
    val resourceUrl: String,
    @SerialName("resource_type")
    val resourcesType: String,
    val name: String,
    val custom: Boolean,
    val other: Boolean,
    @SerialName("is_deleted")
    val isDeleted: Boolean,
    val parent: Parent?
)

@Entity(tableName = "parent")
@kotlinx.serialization.Serializable
data class Parent(
    val id: Long,
    @SerialName("resource_uri")
    val resourceUrl: String,
    @SerialName("resource_type")
    val resourcesType: String,
)