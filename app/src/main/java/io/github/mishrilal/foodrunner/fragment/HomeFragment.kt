package io.github.mishrilal.foodrunner.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import io.github.mishrilal.foodrunner.R
import io.github.mishrilal.foodrunner.adapter.RestaurantRecyclerAdapter
import io.github.mishrilal.foodrunner.model.Restaurant
import io.github.mishrilal.foodrunner.util.ConnectionManager
import org.json.JSONException
import kotlin.Comparator
import kotlin.collections.HashMap

class HomeFragment : Fragment() {
    lateinit var recyclerRestaurant: RecyclerView

    lateinit var layoutManager: RecyclerView.LayoutManager

    lateinit var recyclerAdapter: RestaurantRecyclerAdapter

    lateinit var progressBar: ProgressBar

    lateinit var progressLayout: RelativeLayout

    val restaurantsInfoList = arrayListOf<Restaurant>()

    val ratingComparator = Comparator<Restaurant> { res1, res2 ->

        if (res1.resRating.compareTo(res2.resRating, true) == 0) {
            res1.resName.compareTo(res2.resName, true)
        } else {
            res1.resRating.compareTo(res2.resRating, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        setHasOptionsMenu(true)

        recyclerRestaurant = view.findViewById(R.id.recyclerHome)

        progressBar = view.findViewById(R.id.progressBar)
        progressLayout = view.findViewById(R.id.progressLayout)

//        progressLayout.visibility = View.VISIBLE

        layoutManager = LinearLayoutManager(activity)


        val queue = Volley.newRequestQueue(activity as Context)

        val url = "http://13.235.250.119/v2/restaurants/fetch_result/"

        println("Working outside if connectivity")

        if (ConnectionManager().checkConnectivity(activity as Context)) {
            println("Working inside if connectivity")
            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {

                    // Here we will handle the request
                    try {
                        progressLayout.visibility = View.GONE
                        val success = it.getBoolean("success")

                        if (success) {

                            val data = it.getJSONArray("data")

                            for (i in 0 until data.length()) {
                                val resJSONObject = data.getJSONObject(i)
                                val resObject = Restaurant(
                                    resJSONObject.getString("id"),
                                    resJSONObject.getString("name"),
                                    resJSONObject.getString("rating"),
                                    resJSONObject.getString("cost_for_one"),
                                    resJSONObject.getString("image_url")
                                )
                                restaurantsInfoList.add(resObject)

                                recyclerAdapter =
                                    RestaurantRecyclerAdapter(activity as Context, restaurantsInfoList)

                                recyclerRestaurant.adapter = recyclerAdapter

                                recyclerRestaurant.layoutManager = layoutManager

                            }
                        } else {
                            Toast.makeText(
                                activity as Context,
                                "Some Error Occurred!!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            activity as Context,
                            "Some unexpected Error Occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                }, Response.ErrorListener {
                    // here we will handle the errors
                    Toast.makeText(
                        activity as Context,
                        "Volley Error Occurred!!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }) {

                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "b352d28b99e7f3"
                        return headers
                    }

                }

            queue.add(jsonObjectRequest)

        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is Not Found")
            dialog.setPositiveButton("Open Settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }

            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }


        return view
    }

//override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//    inflater.inflate(R.menu.menu_dashboard, menu)
//}

//override fun onOptionsItemSelected(item: MenuItem): Boolean {
//    val id = item.itemId
//    if(id == R.id.action_sort) {
//        Collections.sort(bookInfoList, ratingComparator)
//        bookInfoList.reverse()
//    }
//
//    recyclerAdapter.notifyDataSetChanged()
//
//    return super.onOptionsItemSelected(item)
//}

}