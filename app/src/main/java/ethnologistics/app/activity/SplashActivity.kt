package ethnologistics.app.activity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import ethnologistics.app.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (!isInternetConnected()) {
            val builder = AlertDialog.Builder(applicationContext)
            builder.setTitle("NoINter")
            builder.setMessage("NoINter")
            builder.setPositiveButton("yes") { dialogInterface, which ->
                finish()
                System.exit(0)
            }
            builder.setNegativeButton("check Wifi") { dialogInterface, which ->
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                finish()
                System.exit(0)
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        } else {
            handler = Handler()
            handler.postDelayed({
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)
        }


    }

    fun isConnected(): Boolean {
        var connectivityManager: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var network: NetworkInfo? = connectivityManager.activeNetworkInfo
        return network != null && network.isConnected
    }

    open fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val caps = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true || caps?.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }
}