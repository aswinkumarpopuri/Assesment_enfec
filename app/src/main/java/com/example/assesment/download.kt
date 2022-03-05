package com.example.assesment

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

object Connector {
    fun connect(jsonURL: String?): Any {
        return try {
            val url = URL(jsonURL)
            val con = url.openConnection() as HttpURLConnection

            //CON PROPS
            con.requestMethod = "GET"
            con.connectTimeout = 15000
            con.readTimeout = 15000
            con.doInput = true
            con
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            "Error " + e.message
        } catch (e: IOException) {
            e.printStackTrace()
            "Error " + e.message
        }
    }
}