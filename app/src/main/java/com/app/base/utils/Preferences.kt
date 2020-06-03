
import android.content.Context
import com.app.base.utils.security.EncryptedPreferences

class Preferences {
    companion object {

        var prefs: EncryptedPreferences? = null
        fun initPreferences(context: Context) {
            prefs = EncryptedPreferences(context)
        }
    }
}