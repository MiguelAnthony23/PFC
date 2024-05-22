package com.example.menu.ui.test

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.menu.databinding.FragmentTestBinding

class TesteoFragment : Fragment() {

    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_EXTERNAL_STORAGE_PERMISSION = 200


    private var usbConnected = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonScanUSB.setOnClickListener {
            if (checkPermissions()) {
                verifierAccessorise()
            } else {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_EXTERNAL_STORAGE_PERMISSION)
            }
        }

        binding.fabToggleUSB.setOnClickListener {
            toggleUSBConnection()
        }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    verifierAccessorise()
                } else {
                    Toast.makeText(requireContext(), "Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun verifierAccessorise() {
        if (usbConnected) {

            val usbManager = requireContext().getSystemService(Context.USB_SERVICE) as UsbManager
            val deviceList = usbManager.deviceList

            if (deviceList.isNotEmpty()) {

                val device = deviceList.values.first()


                val deviceInfo = """
                    Nombre del Dispositivo: ${device.deviceName}
                    Fabricante: ${device.manufacturerName ?: "N/A"}
                    Nombre del Producto: ${device.productName ?: "N/A"}
                    ID del Producto: ${device.productId}
                    ID del Dispositivo: ${device.deviceId}
                """.trimIndent()

                showDeviceDialog(deviceInfo)
            } else {
                showNoDeviceDialog()
            }
        } else {
            showNoDeviceDialog()
        }
    }

    private fun showDeviceDialog(deviceInfo: String) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Información del Dispositivo USB")
            .setMessage(deviceInfo)
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }

    private fun showNoDeviceDialog() {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("No se detectó ningún dispositivo")
            .setMessage("No hay ningún dispositivo USB conectado.")
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }

    private fun toggleUSBConnection() {
        usbConnected = !usbConnected
        Toast.makeText(requireContext(), "Simulando USB ${if (usbConnected) "Conectado" else "Desconectado"}", Toast.LENGTH_SHORT).show()
    }
}
