package org.connecttag.utilities.global

import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONObject

object JsonUtils {

    inline fun <reified T> T.toJson(): String {
        return try {
            Gson().toJson(this)
        } catch (ex: Exception) {
            ""
        }
    }

    inline fun <reified T> String.fromJson(): T? {
        return try {
            Gson().fromJson(this, T::class.java)
        } catch (ex: Exception) {
            null
        }
    }


    fun removeUnwantedCharacters(jsonObject: JsonObject?): JSONObject {
        // Convert the JSONObject to a string
        val jsonString = jsonObject.toString()

        // Convert the JSONArray to a string
        //  val jsonString = jsonArray.toString()

        // Remove unwanted characters using a regular expression
        val cleanJsonString = jsonString.replace("[^\\p{ASCII}]".toRegex(), "")

        // Convert the cleaned string back to a JSONObject
        return JSONObject(cleanJsonString)
    }



    fun removeUnwantedCharactersNew(jsonObject: JsonObject) {
        for ((key, value) in jsonObject.entrySet()) {
            if (value.isJsonArray) {
                val jsonArray = value.asJsonArray
                for (i in 0 until jsonArray.size()) {
                    val element = jsonArray[i]
                    if (element.isJsonPrimitive) {
                        var elementValue = element.asString
                        elementValue = elementValue.replace("[^\\p{ASCII}]".toRegex(), "")
                        jsonArray.set(i, Gson().fromJson("\"$elementValue\"", element.javaClass))
                    }
                }
            } else if (value.isJsonObject) {
                removeUnwantedCharactersNew(value.asJsonObject)
            }
        }
    }
}