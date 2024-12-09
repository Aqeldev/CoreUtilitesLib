package org.connecttag.utilities.global

import org.connecttag.utilities.global.PhoneNumberUtils.clearYemenCodeFromNumber
import org.connecttag.utilities.global.SystemInteractionsUtils.OpenWebAppUtils.openUrlByUri
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.Toast

object SystemInteractionsUtils {

    object OpenWebAppUtils {
        fun openWeb(context:Context,url: String) {
            try {



                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )






                context.startActivity(intent)

            } catch (_: ActivityNotFoundException) {

            }
        }

        fun openUrlByUri(context:Context,uri: Uri) {
            try {

                val intent = Intent(Intent.ACTION_VIEW, uri)



                context.startActivity(intent)

            } catch (e: ActivityNotFoundException) {

            }
        }
    }



    object AppPackageUtils {

        public fun Context.openSystemSettings() {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                val uri: Uri = Uri.fromParts("package", packageName, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                data = uri
            }
            startActivity(intent)
        }
        fun openChatWhatsAppWithNumber(context :Context, number:String) {


            try {

                val whatsAppNumber = clearYemenCodeFromNumber(number)
                //     var app = Intent()

                val uriForApp: Uri = Uri.parse("https://api.whatsapp.com/send?phone=$whatsAppNumber")


                openUrlByUri(context,uriForApp)
                //    app = Intent(Intent.ACTION_VIEW, uriForApp)


                //     val packageManager = Context.packageManager
                //    val packageInfo =
                //       packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES)
                //      val i = Intent(Intent.ACTION_VIEW)
                //       i.data = Uri.parse(url)
                //    Context.startActivity(app)


            } catch (e: PackageManager.NameNotFoundException) {
                //Toast.makeText(Context, "الواتساب الرسمي غير مثبت لديك.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

        }

        fun openInstagramPage(context:Context, url: String) {
            //   var formBrowser = Intent()
            //   var formApp = Intent()

            val uriForApp: Uri = Uri.parse("http://instagram.com/_u/$url")
            val uriForBrowser: Uri = Uri.parse("http://instagram.com/$url")


            //    formApp = Intent(Intent.ACTION_VIEW, uriForApp)
            //   formBrowser = Intent(Intent.ACTION_VIEW, uriForBrowser)

            try {

                //     if (isAppInstalled(context,"instgram")) {
                //    formApp.component = ComponentName("com.instagram.android","com.instagram.android.activity.UrlHandlerActivity")
                //  context.startActivity(formApp)

                openUrlByUri(context,uriForApp)

                openUrlByUri(context,uriForBrowser)
                //  } else {
                //    context.startActivity(formBrowser)
                // }


            } catch (e: PackageManager.NameNotFoundException) {
                openUrlByUri(context,uriForBrowser)
            }
        }

        fun openFacebookPage(context: Context) {


            val packageManager: PackageManager? = context.packageManager
            // FacebookページのID
            val facebookPageID = "dempagumi.inc"

            // URL
            val facebookUrl = "https://www.facebook.com/$facebookPageID"

            // URLスキーム
            val facebookUrlScheme = "fb://page/$facebookPageID"
            try {
                // Facebookアプリのバージョンを取得
                val versionCode: Int =

                    packageManager!!.getPackageInfo("com.facebook.katana", 0).versionCode
                if (versionCode >= 3002850) {
                    // Facebook アプリのバージョン 11.0.0.11.23 (3002850) 以上の場合
                    val uri = Uri.parse("fb://facewebmodal/f?href=$facebookUrl")
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                } else {
                    // Facebook アプリが古い場合
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrlScheme)))
                }
            } catch (e: PackageManager.NameNotFoundException) {
                // Facebookアプリがインストールされていない場合は、ブラウザで開く
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)))
            }
        }

        fun openTelegram(context: Context, phoneNumber: String, message: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://t.me/share/url?text=$message&phone=$phoneNumber")

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // تطبيق Telegram غير مثبت على الجهاز
                // يمكنك إدراج رمز هنا للتعامل مع هذا الحالة على حسب احتياجات التطبيق الخاص بك
            }
        }

        fun openGooglePlay(context:Context) {
            try {

              //  OpenWebAppUtils.openWeb(context, Apps_Info.GooglePlay.AppUrl+ V_App_PackageName)

            } catch (ex: Exception) {
            //    OpenWebAppUtils.openWeb(context, Apps_Info.GooglePlay.WebUrl+ V_App_PackageName)
              //  MyLog(ex.message.toString())
            }
        }

    }



    object ClipboardUtils {



        fun copyToClipboard(context: Context, value: String, label: String = "label") {
            val   clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (value != "" || value.isNotEmpty()) {
                val clipData: ClipData = ClipData.newPlainText(label, value)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context, " تم النسخ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    object ShareUtils {




        fun shareText(context: Context, text: String) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"

            }
            context.startActivity(
                Intent.createChooser(sendIntent,
                    "Share via"))
        }


        //
        val shareApp: (Context, String) -> Unit = { ctx, txt ->
            ctx.startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, txt)
                        type = "text/plain"
                    },
                    null
                )
            )
        }

        //
        val shareNote: (Context, String, String, then: () -> Unit) -> Unit = { ctx, title, description, then ->
            ctx.startActivity(
                Intent.createChooser(
                    Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "$title\n\n$description")
                        type = "text/plain"
                    },
                    null
                )
            )
            then.invoke()
        }

        fun shareWithWhatsapp(context: Context, message: String) {
            // Create a new intent to open the WhatsApp app.
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.setPackage("com.whatsapp")
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)

            try {
                context.startActivity(shareIntent)
            } catch (_: Exception) {


            }
        }

        fun shareWithTelegram(context: Context, message: String) {

            val nhhIntent = Intent(Intent.ACTION_SEND)
            nhhIntent.type = "text/plain"
            nhhIntent.setPackage("org.telegram.messenger")
            nhhIntent.putExtra(Intent.EXTRA_TEXT, message)
            try {
                context.startActivity(nhhIntent)
            } catch (_: Exception) {


            }

        }


        fun shareWithFacebook(context: Context, message: String) {

            val nhhIntent = Intent(Intent.ACTION_SEND)
            nhhIntent.type = "text/plain"
            nhhIntent.setPackage("com.facebook.orca")
            nhhIntent.putExtra(Intent.EXTRA_TEXT, message)
            try {
                context.startActivity(nhhIntent)
            } catch (_: Exception) {


            }

        }

    }

    object EmailUtils {

        fun sendEmailByGmail(context:Context, email: String) {
            try {


                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$email")
                }
                context.startActivity(
                    Intent.createChooser(
                        emailIntent,"مشاركة عبر"
                    )
                )

            } catch (_: Exception) {

            }
            /*    Intent(Intent.ACTION_SEND).apply {
                    // The intent does not have a URI, so declare the "text/plain" MIME type
                    type = "text/plain"
                    putExtra(Intent.EXTRA_EMAIL, arrayOf("jan@example.com")) // recipients
                    putExtra(Intent.EXTRA_SUBJECT, "Email subject")
                    putExtra(Intent.EXTRA_TEXT, "Email message text")
                    putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"))
                    // You can also attach multiple items by passing an ArrayList of Uris
                }*/


        }

        fun sendEmail(context: Context, to: String, subject: String = "", body: String = "") {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Handle case where no email app is found
                Toast.makeText(context, "No email app found!", Toast.LENGTH_SHORT).show()
            }
        }

        fun openEmailDraft(context: Context, to: String, subject: String = "", body: String = "") {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, body)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                // Handle case where no email app is found
                Toast.makeText(context, "No email app found!", Toast.LENGTH_SHORT).show()
            }
        }

        val mailTo: (Context, String) -> Unit = { ctx, to ->
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL,
                arrayOf(to))
            intent.putExtra(Intent.EXTRA_SUBJECT, "")
            intent.putExtra(Intent.EXTRA_TEXT, "")
            if (intent.resolveActivity(ctx.packageManager) != null) {
                ctx.startActivity(intent)
            }
        }
    }

    object CallUtils {
        fun makeCall(context: Context, phoneNumber: String) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            if (intent.resolveActivity(context.packageManager)
                != null) {
                context.startActivity(intent)

            }
        }

        fun makeCallToNumber(context: Context, number: String) {

            val phoneIntent = Intent(
                Intent.ACTION_DIAL, Uri.fromParts(
                    "tel", number, null
                )
            )
            context.startActivity(phoneIntent)
        }
        val callNumber: (Context, String) -> Unit = { ctx, number ->
            ctx.startActivity(
                Intent.createChooser(
                    Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$number")
                    },
                    null
                )
            )
        }
    }
    object ContactsUtils {
        fun addNewContacts(context: Context, number: String, name: String) {
            try {
                val intent = Intent(Intent.ACTION_INSERT)
                intent.type = ContactsContract.Contacts.CONTENT_TYPE
                intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, number)
                context.startActivity(intent)
            } catch (ex: Exception) {
                //Toast.makeText(Context, "الواتساب الرسمي غير مثبت لديك.", Toast.LENGTH_SHORT).show()


            }
        }
    }

    object SMSUtils {


        fun sendSMSToNumberWithMessage(context: Context, phoneNumber: String, message: String) {

            try {
                val smsUri = Uri.parse("smsto:$phoneNumber")
                val smsIntent = Intent(Intent.ACTION_SENDTO, smsUri)
                smsIntent.putExtra("sms_body", message)
                context.startActivity(smsIntent)
            } catch (_: Exception) {


            }
        }

        fun sendSMS(context : Context,textMessage: String) {

            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textMessage)
                type = "text/plain"
            }

// Try to invoke the intent.
            try {
                context.startActivity(sendIntent)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
            }
        }

    }

}