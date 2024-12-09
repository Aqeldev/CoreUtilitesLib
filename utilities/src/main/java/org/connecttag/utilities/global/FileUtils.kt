package org.connecttag.utilities.global

object FileUtils {
    fun isValidFileExtension(fileName: String, validExtensions: List<String>): Boolean {
        val extension = fileName.substringAfterLast(".", "")
        return validExtensions.contains(extension.lowercase())
    }

    fun isValidImageFile(fileName: String): Boolean {
        val validImageExtensions = listOf("jpg", "jpeg", "png", "gif", "bmp")
        return isValidFileExtension(fileName, validImageExtensions)
    }

    fun isValidVideoFile(fileName: String): Boolean {
        val validVideoExtensions = listOf("mp4", "avi", "mov", "mkv", "wmv")
        return isValidFileExtension(fileName, validVideoExtensions)
    }

    fun isValidAudioFile(fileName: String): Boolean {
        val validAudioExtensions = listOf("mp3", "wav", "ogg", "flac", "aac")
        return isValidFileExtension(fileName, validAudioExtensions)
    }

    fun isValidDocumentFile(fileName: String): Boolean {
        val validDocumentExtensions = listOf("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt")
        return isValidFileExtension(fileName, validDocumentExtensions)
    }

}