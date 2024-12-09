package org.connecttag.utilities.global

import java.math.BigDecimal
import java.text.NumberFormat

object CurrencyUtils {
    fun formatPrice(price: Long): String {
        return NumberFormat.getCurrencyInstance().format(
            BigDecimal(price).movePointLeft(2)
        )
    }

}