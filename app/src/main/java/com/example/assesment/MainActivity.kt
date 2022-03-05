package com.example.assesment

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection


class MainActivity : AppCompatActivity() {

    var users: ArrayList<User> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       JSONDownloader("https://jsonplaceholder.typicode.com/users",1).execute()


    }


    inner class JSONDownloader(val jsonURL: String, val  parsing: Int ) :
        AsyncTask<Void?, Void?, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void?): String {
            return download()
        }

        override fun onPostExecute(jsonData: String) {
            super.onPostExecute(jsonData)

            if (jsonData.startsWith("Error")) {
                Toast.makeText(this@MainActivity, jsonData, Toast.LENGTH_SHORT).show()
            } else {
                //PARSE
                  when (parsing) {
                     1-> JSONParserUsers(jsonData).execute()
                      2->JSONParserPosts(jsonData).execute()
                  }

            }
        }


        private fun download(): String {
            val connection = Connector.connect(jsonURL)
            if (connection.toString().startsWith("Error")) {
                Log.e("download", connection.toString())
                return connection.toString()
            } else return try {
                //ESTABLISH CONNECTION
                val con = connection as HttpURLConnection
                if (con.responseCode == HttpURLConnection.HTTP_OK) {
                    //GET INPUT FROM STREAM
                    val `is`: InputStream = BufferedInputStream(con.inputStream)
                    val br = BufferedReader(InputStreamReader(`is`))
                    var line: String = ""
                    val jsonData = StringBuffer()

                    //READ


                    while (br.readLine().also {
                            if (it != null) {
                                line = it
                            }
                        } != null) {
                        jsonData.append(line + "\n")
                    }

                    //CLOSE RESOURCES
                    br.close()
                    `is`.close()

                    //RETURN JSON
                    jsonData.toString()
                } else {
                    "Error " + con.responseMessage
                }
            } catch (e: IOException) {
                e.printStackTrace()
                "Error " + e.message
            }
        }


    }


    inner class JSONParserUsers(var jsonData: String) :
        AsyncTask<Void?, Void?, Boolean>() {
        var pd: ProgressDialog? = null

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(this@MainActivity)
            pd!!.setTitle("Parse JSON")
            pd!!.setMessage("Parsing...Please wait")
            pd!!.show()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
            return parse()
        }

        override fun onPostExecute(isParsed: Boolean) {
            super.onPostExecute(isParsed)
            pd!!.dismiss()
            if (isParsed) {
                //BIND

                JSONDownloader("https://jsonplaceholder.typicode.com/posts",2 ).execute()

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Unable To Parse,Check Your Log output",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        private fun parse(): Boolean {
            return try {
                val ja = JSONArray(jsonData)
                var jo: JSONObject
                users.clear()
                for (i in 0 until ja.length()) {
                    jo = ja.getJSONObject(i)
                   var user = User()
                     user.user= jo.getString("name")
                     user.id = jo.getString("id").toInt()
                    users.add(user)
                }
                true
            } catch (e: JSONException) {
                e.printStackTrace()
                false
            }
        }

    }



    inner class JSONParserPosts(var jsonData: String) :
        AsyncTask<Void?, Void?, Boolean>() {
        var pd: ProgressDialog? = null
        var posts: ArrayList<String> = ArrayList()
        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(this@MainActivity)
            pd!!.setTitle("Parse JSON")
            pd!!.setMessage("Parsing...Please wait")
            pd!!.show()
        }

        override fun doInBackground(vararg params: Void?): Boolean {
            return parse()
        }

        override fun onPostExecute(isParsed: Boolean) {
            super.onPostExecute(isParsed)
            pd!!.dismiss()
            if (isParsed) {
                //BIND
                val adapter = mAdapter(
                    this@MainActivity,
                    users
                )
                rv.adapter = adapter

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Unable To Parse,Check Your Log output",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        private fun parse(): Boolean {
            return try {
                val ja = JSONArray(jsonData)
                var jo: JSONObject
                posts.clear()
                for (i in 0 until ja.length()) {
                    jo = ja.getJSONObject(i)
                    val userId = jo.getString("userId").toInt()
                    for (user in users) {

                        if (user.id== userId){
                            user.body =jo.getString("body")
                            user.title =jo.getString("title")
                        }

                    }
                }
                true
            } catch (e: JSONException) {
                e.printStackTrace()
                false
            }
        }

    }


}

class User {
    var user=""
    var id: Int? = null
    var title="no match"
    var body="no match"

}
