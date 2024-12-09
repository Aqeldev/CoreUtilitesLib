package com.core.utils.utilities.constants



object NetworkServiceConstants {


    const val CONTENT_LENGTH = 250_000L

    const val Header_Content_Type = "Content-Type"
    const val Header_Content_Type_Json = "application/json"
    const val Header_Authorization =  "Authorization"

    const val Header_Authorization_Bearer =  "Bearer"

     const val CLIENT_TYPE = "client-type"
   const val Application_ID = "application-id"
   const val VERSION = "client-version"
 const val UNIQUE_ID = "user-id"
   const val RAPID_API_KEY = "X-RapidAPI-Key"
   const val RAPID_API_HOST = "X-RapidAPI-Host"

    const val PackegeName = "Package-Name"
    const val Device_Unique_ID = "Device-Unique-ID"

}


object Database_Constants {
    const val Database_DB = "db"

}

object Constants {
    const val UNKNOWN_ERROR = "An unknown error occurred."
    const val FILL_FIELD = "Please fill in the field."
    const val UNKNOWN_HOST = "Unable to resolve host \"api.openweathermap.org\": No address associated with hostname"




    const val URGENT = "URGENT"
    const val IMPORTANT = "IMPORTANT"
    const val NORMAL = "NORMAL"
    const val LOW = "LOW"
    const val NON = "NON"

    const val UNDO = "تراجع"
    const val REDO = "Redo"



    const val KEY_CLICK = 0
    const val KEY_STANDARD = 5
    const val KEY_INVALID = 9
    const val FOCUS_NAVIGATION = 12

    const val GRID = "GRID_LAYOUT"
    const val LIST = "LIST_LAYOUT"

}

object ExceptionTitles {
    const val GPS_DISABLED = "GPS Disabled"
    const val NO_PERMISSION = "No Permission"
    const val NO_INTERNET_CONNECTION = "No Internet Connection"
    const val UNKNOWN_ERROR = "Unknown Error"
}

object ExceptionDescriptions {
    const val GPS_DISABLED_DESCR = "Your GPS seems to be disabled, please enable it."
    const val NO_PERMISSION_DESCR = "Allow otherwise location tracking won't work."
    const val NO_INTERNET_CONNECTION_DESCR = "Please check your internet connection."
    const val UNKNOWN_ERROR_DESCR = "Something went wrong."
}

object ErrorCardConsts {
    const val BUTTON_TEXT = "OK"
}

object EnCodeConstants {
    const val S_Enc_UTF8 = "UTF-8"
    const val S_Enc_CipherTransformation = "AES/CBC/PKCS5Padding"
    const val S_Enc_AES = "AES"
    const val S_SHA_256 = "SHA-256"
}

object Ui_Constants {

}

object Order_Constants {
    const val BY_NAME = "ORDER_BY_NAME"
    const val ORDER_BY_OLDEST = "ORDER_BY_OLDEST"
    const val ORDER_BY_NEWEST = "ORDER_BY_NEWEST"
    const val ORDER_BY_PRIORITY = "ORDER_BY_PRIORITY"
    const val ORDER_BY_REMINDER = "ORDER_BY_REMINDER"
    const val BY_ID = "ORDER_BY_ID"
}

