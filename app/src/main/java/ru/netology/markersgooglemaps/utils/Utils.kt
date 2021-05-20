package ru.netology.markersgooglemaps.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import ru.netology.markersgooglemaps.dto.Marker
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Utils {
    object EmptyMarker {
        val empty = Marker(
            id = 0,
            markerTitle = "",
            markerDescription = "",
            publishedDate = "",
            latitude = 0.0,
            longitude = 0.0
        )
    }

    object StringArg: ReadWriteProperty<Bundle, String?> {

        override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String?) {
            thisRef.putString(property.name, value)
        }

        override fun getValue(thisRef: Bundle, property: KProperty<*>): String? =
            thisRef.getString(property.name)
    }

    companion object {
        fun hideKeyboard(view: View) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
