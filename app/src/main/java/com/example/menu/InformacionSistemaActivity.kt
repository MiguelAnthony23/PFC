package com.example.menu

import android.content.Context
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.RandomAccessFile
import java.util.Locale

class InformacionSistemaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_informacion_sistema)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Información del Sistema"

        val nombreDispositivo = Build.MODEL
        val fabricanteDispositivo = Build.MANUFACTURER
        val versionSO = Build.VERSION.RELEASE
        val versionKernel = System.getProperty("os.version")
        val idioma = Locale.getDefault().displayLanguage
        val region = Locale.getDefault().country
        val tipoProcesador = Build.HARDWARE
        val velocidadReloj = "${Build.BOARD} ${Build.CPU_ABI} ${Build.CPU_ABI2}"
        val memoriaRAM = getTotalRAM()
        val almacenamientoInterno = getAvailableInternalStorage()
        val displayMetrics = resources.displayMetrics
        val resolucionPantalla = "${displayMetrics.widthPixels} x ${displayMetrics.heightPixels}"
        val densidadPantalla = displayMetrics.densityDpi.toString()
        val nivelBateria = getBatteryLevel()

        findViewById<TextView>(R.id.textViewNombreDispositivoValor).text = nombreDispositivo
        findViewById<TextView>(R.id.textViewFabricanteDispositivoValor).text = fabricanteDispositivo
        findViewById<TextView>(R.id.textViewVersionKernelValor).text = versionKernel
        findViewById<TextView>(R.id.textViewIdiomaValor).text = idioma
        findViewById<TextView>(R.id.textViewRegionValor).text = region
        findViewById<TextView>(R.id.textViewTipoProcesadorValor).text = tipoProcesador
        findViewById<TextView>(R.id.textViewVelocidadRelojValor).text = velocidadReloj
        findViewById<TextView>(R.id.textViewMemoriaRAMValor).text = memoriaRAM
        findViewById<TextView>(R.id.textViewAlmacenamientoInternoValor).text = almacenamientoInterno
        findViewById<TextView>(R.id.textViewResolucionPantallaValor).text = resolucionPantalla
        findViewById<TextView>(R.id.textViewDensidadPantallaValor).text = densidadPantalla
        findViewById<TextView>(R.id.textViewNivelBateriaValor).text = nivelBateria.toString()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getTotalRAM(): String {
        val reader = RandomAccessFile("/proc/meminfo", "r")
        val line = reader.readLine()
        val parts = line.split("\\s+".toRegex()).toTypedArray()
        val load = if (parts.isNotEmpty()) parts[1].toInt() else 0
        reader.close()
        return "${(load / 1024)} MB"
    }


    private fun getAvailableInternalStorage(): String {
        val stat = StatFs(Environment.getDataDirectory().path)
        val bytesAvailable = stat.blockSizeLong * stat.availableBlocksLong
        val megabytes = bytesAvailable / (1024 * 1024)
        return "$megabytes MB"
    }

    private fun getBatteryLevel(): Int {
        val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }
}
