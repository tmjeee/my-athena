
package com.tmjee.myathena.ui

import android.text.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

object UIUtil {

    fun toText(s: Number?, prefix: String = ""): String {
        return s?.let {
            "${prefix}${s}"
        } ?: prefix
    }

    fun toText(s: String?, prefix: String = ""): String {
        return s?.let {
            "${prefix}${s}"
        } ?: prefix
    }

    fun toDay(date: Date?, prefix: String = ""): String {
        return date?.let {
            "${prefix}${SimpleDateFormat("EE").format(it)}"
        } ?: prefix
    }

    fun toDate(date: Date?, prefix: String = ""): String {
        return date?.let {
            "${prefix}${SimpleDateFormat("dd-MM-yyyy").format(it)}"
        } ?: prefix
    }

    fun toCurrency(a: Float?, prefix: String = ""): String {
       // return String.format("$%s", a)
       // val f = DecimalFormat("#.##").format(a)
       return a?.let {
           String.format("%s$%.2f", prefix, it)
       } ?: prefix
    }

    fun toListString(list: List<String>?, prefix: String = ""): Spanned {
        val s = list?.let {
            toList(it, prefix)
        } ?: SpannableString("")
        return s
    }

    fun <T> toList(list: List<T>,
                   prefix: String = "",
                   transform: (a: T) -> String = { a -> a.toString()}
    ): Spanned {
       val html = list
           .map(transform)
           .map({"<li>${it}</li>"})
           .reduce { acc: String, i: String -> acc + i }
       return  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
           val s = "${prefix}<ul>${html}</ul>"
           Html.fromHtml(s, Html.FROM_HTML_MODE_COMPACT)
       } else  {
           val s = "${prefix}<ul>${html}</ul>"
           Html.fromHtml(s)
       }
    }
}