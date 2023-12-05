package com.example.lab4

import androidx.room.Entity
import java.util.Date
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@Entity(tableName = "holidays")
data class Holiday (
    @PrimaryKey
    @JsonProperty("id")
    var id: Long,
    @JsonProperty("date") var date: Date,
    @JsonProperty("localName") var localName: String?,
    @JsonProperty("name") var name: String?,
    @JsonProperty("countryCode") var countryCode: String?,
    @JsonProperty("fixed") var fixed: Boolean = false,
    @JsonProperty("global") var global: Boolean = false,
    @JsonProperty("counties") var counties: Array<String>?,
    @JsonProperty("launchYear") var launchYear: Int?,
    @JsonProperty("types") var types: Array<String>?
): Serializable