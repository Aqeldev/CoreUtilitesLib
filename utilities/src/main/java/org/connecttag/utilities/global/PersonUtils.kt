package org.connecttag.utilities.global


object PersonUtils {

    fun removeSpaseFromNameToPart(value: String): String {
        val output = value.replace("عبد ".toRegex(), "عبد")
        return output
    }

    fun isNameValidLength(name: String): Boolean {
        val compoundWords = listOf(
            "عبد",
            "أبو",
            "أم",
            "ابن",
            "بن",
            "بنت",
            "ذو",
            "ذي",
            "عبد الله",
            "عبد الرحمن",
            "عبد العزيز",
            "عبد الكريم",
            "عبد المجيد",
            "عبد الملك",
            "عبد الله",
            "أبو بكر",
            "أبو ذر",
            "أم كلثوم",
            "ابن سينا",
            "ابن خلدون",
            "بنت الزبير",
            "ذو النون",
            "ذي القرنين",
            "نجم الدين",
            "شرف الدين"
        ) // أضف الكلمات المركبة الأخرى هنا

        val words = name.trim().split("\\s+".toRegex())
        val filteredWords = words.filter { !compoundWords.contains(it) } // تجاهل الكلمات المركبة

        return filteredWords.size in 2..6 // تحقق مما إذا كان عدد الكلمات بين 2 و 6
    }


    fun removeSpacesWithCompoundWords(text: String): String {
        val compoundWords = setOf(
            "عبد الله", "عبد الرحمن", "عبد العزيز", "عبد الكريم", "عبد المجيد", "عبد الملك",
            "أبو بكر", "أبو ذر", "أبو هريرة", "أبو لهب", "أبو جهل",
            "أم كلثوم", "أم المؤمنين", "أم سلمة", "أم حبيبة",
            "ابن سينا", "ابن خلدون", "ابن بطوطة", "ابن رشد", "ابن الهيثم",
            "بنت الزبير", "بنت عثمان", "بنت أبي بكر",
            "ذو النون", "ذو القرنين", "ذو الفقار",
            "ذي يزن", "ذي النورين",
            "نجم الدين", "شرف الدين", "صلاح الدين", "نور الدين", "عز الدين"
            // أضف الكلمات المركبة الأخرى هنا
        )

        val words = text.trim().split("\\s+".toRegex())
        val result = StringBuilder()
        for (i in words.indices) {
            if (i > 0 && compoundWords.contains(words[i - 1] + " " + words[i])) {
                continue // تجاهل المسافة بين الكلمات المركبة
            }
            result.append(words[i])
        }
        return result.toString()
    }

    fun removeSpacesAfterSpecificLetters(text: String): String {
        val compoundWords = setOf(
            "عبد الله", "عبد الرحمن", "عبد العزيز", "عبد الكريم", "عبد المجيد", "عبد الملك",
            "أبو بكر", "أبو ذر", "أبو هريرة", "أبو لهب", "أبو جهل",
            "أم كلثوم", "أم المؤمنين", "أم سلمة", "أم حبيبة",
            "ابن سينا", "ابن خلدون", "ابن بطوطة", "ابن رشد", "ابن الهيثم",
            "بنت الزبير", "بنت عثمان", "بنت أبي بكر",
            "ذو النون", "ذو القرنين", "ذو الفقار",
            "ذي يزن", "ذي النورين",
            "نجم الدين", "شرف الدين", "صلاح الدين", "نور الدين", "عز الدين"
            // أضف الكلمات المركبة الأخرى هنا
        )

        val lettersToRemoveSpacesAfter = setOf('و', 'ر', 'ز', 'د', 'ذ', 'ء', 'ؤ')

        val words = text.trim().split("\\s+".toRegex())
        val result = StringBuilder()
        for (i in words.indices) {
            if (i > 0 && compoundWords.contains(words[i - 1] + " " + words[i])) {
                result.append(words[i - 1]).append(" ") // الحفاظ على المسافة بين الكلمات المركبة
            } else if (i > 0 && lettersToRemoveSpacesAfter.contains(words[i - 1].last())) {
                result.append(words[i]) // إزالة المسافة بعد الحروف المحددة
            } else {
                result.append(words[i])
                if (i < words.lastIndex) result.append(" ") // إضافة مسافة بين الكلمات الأخرى
            }
        }
        return result.toString().trim() // إزالة أي مسافات زائدة في البداية والنهاية
    }

}