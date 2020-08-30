package com.arun.testapp.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    companion object {

        private const val PREF_KEY_PACK_SORT_ORDER = "PREF_KEY_PACK_SORT_ORDER"
        private const val PREF_KEY_PACK_PIECES_LIMIT = "PREF_KEY_PACK_PIECES_LIMIT"
        private const val PREF_KEY_PACK_WEIGHT_LIMIT = "PREF_KEY_PACK_WEIGHT_LIMIT"

        fun customPreference(context: Context, name: String): SharedPreferences =
            context.getSharedPreferences(name, Context.MODE_PRIVATE)

        private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
            val editMe = edit()
            operation(editMe)
            editMe.apply()
        }

        var SharedPreferences.packSortOrder
            get() = getString(PREF_KEY_PACK_SORT_ORDER, "")
            set(value) {
                editMe {
                    it.putString(PREF_KEY_PACK_SORT_ORDER, value)
                }
            }

        var SharedPreferences.packPieceLimit
            get() = getInt(PREF_KEY_PACK_PIECES_LIMIT, 0)
            set(value) {
                editMe {
                    it.putInt(PREF_KEY_PACK_PIECES_LIMIT, value)
                }
            }

        var SharedPreferences.packWeightLimit
            get() = getString(PREF_KEY_PACK_WEIGHT_LIMIT, "0")
            set(value) {
                editMe {
                    it.putString(PREF_KEY_PACK_WEIGHT_LIMIT, value)
                }
            }

        var SharedPreferences.clearValues
            get() = { }
            set(value) {
                editMe {
                    it.clear()
                }
            }
    }

}