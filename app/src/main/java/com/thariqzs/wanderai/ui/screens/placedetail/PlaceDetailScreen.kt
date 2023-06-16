package com.thariqzs.wanderai.ui.screens.placedetail

import android.net.Uri
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.data.api.model.Place
import com.thariqzs.wanderai.data.api.model.PlaceRestaurantData
import com.thariqzs.wanderai.data.api.model.RestaurantData
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.home.HomeViewModel
import com.thariqzs.wanderai.ui.screens.shared.components.Accordion
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueSky
import com.thariqzs.wanderai.ui.theme.Gray300
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b1
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2
import com.thariqzs.wanderai.utils.formatAmountRange
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.Locale

@Composable
fun PlaceDetailScreen(navController: NavController, hvm: HomeViewModel) {
    val TAG = "pdsthoriq"
    BackHandler(true) {
        navController.popBackStack()
        hvm.place = Place()
    }

    Image(
        painter = painterResource(id = R.drawable.plan_detail_banner),
        contentDescription = "plan_detail_banner"
    )
    Column(Modifier.fillMaxSize()) {
        PlaceDetailHeader(
            navController,
            onPressBack = {
                navController.popBackStack()
                hvm.place = Place()
            },
            title = hvm.place.nama?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                ?: ""
        )
        if (hvm.place.nama != null) {
            PlaceDetailBody(hvm.place, hvm.imageUri)
        }
    }
}

@Composable
fun PlaceDetailHeader(navController: NavController, onPressBack: () -> Unit, title: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(all = 12.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = "ic_chevron_left",
            tint = Color.White,
            modifier = Modifier
                .size(52.dp)
                .clickable {
                    onPressBack()
                }
        )
        Text(title, style = h4, color = Color.White)
    }
}

@Composable
fun PlaceDetailBody(place: Place, imgUri: Uri) {
    val TAG = "pdsthoriq"
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 4.dp),

        ) {
        Text("Created on 28 May 2023", style = b2, color = Color.White)
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .background(BlueSky, RoundedCornerShape(24.dp))
                .padding(4.dp)
                .height(200.dp)
                , verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = (imgUri
                    ?: "https://upload.wikimedia.org/wikipedia/commons/3/34/Monas_2.jpg"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
//                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
            )
            Spacer(Modifier.width(4.dp))
            AsyncImage(
                model = (place.detail?.image_url
                    ?: "https://upload.wikimedia.org/wikipedia/commons/3/34/Monas_2.jpg"),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
//                    .fillMaxWidth()
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(24.dp)
                    )
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column() {
                Text("Probability", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text((String.format("%.3f", place.probability) + "%") ?: "-", style = b1)
                Spacer(modifier = Modifier.height(2.dp))
                val probability = place.probability
                val accuracyText = when {
                    probability!! > 0.98 -> "Sangat Akurat"
                    probability > 0.96 -> "Cukup Akurat"
                    else -> "Tidak Akurat"
                }
                Text(
                    text = accuracyText,
                    style = a,
                    color = when {
                        place.probability > 0.98 -> Color.Green
                        place.probability > 0.96 -> Color.Yellow
                        else -> Color.Red
                    },
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    "(Angka ini menandakan seberapa yakin sistem dalam memprediksi gambar anda)",
                    style = a,
                    color = Gray300,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Summary", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(place.detail?.summary ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Rating Tourism", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(place.detail?.rating_tourism.toString() ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Important Facts", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                if (place.detail?.important_facts?.isNotEmpty() == true) {
                    for (i in 0..(place.detail.important_facts.size.minus(1) ?: 0)) {
                        Row(Modifier.fillMaxWidth()) {
                            Text("-")
                            Text(
                                place.detail.important_facts[i],
                                style = b1,
                                modifier = Modifier.padding(start = 2.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Sejarah", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(
                    place.detail?.summary ?: "-",
                    style = b1
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Restaurant Recommendation", style = sh2)
                Log.d(TAG, "place.detail?.restaurant: ${place.detail?.restaurant} , amount ${place.detail?.restaurant?.size}")
                for (i in 0..((place.detail?.restaurant?.size?.minus(1)) ?: 0)) {
                    val restaurant = place.detail?.restaurant?.get(i)
                    Log.d(TAG, "restaurant: $restaurant")
                    if (restaurant?.name?.isNotEmpty() == true) {
                        Accordion(header = restaurant?.name ?: "") {
                            PlaceRestaurantItem(restaurant)
                        }
                    } else {
                        Text("-")
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceRestaurantItem(resto: PlaceRestaurantData) {
    Text("Name", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(resto.name ?: "-", style = b2)
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Rating", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(resto.rating.toString() ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("Review", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text((resto.user_ratings_total.toString() + " reviews") ?: "-", style = b2)
        }
    }
    Spacer(Modifier.height(12.dp))
    Text("Estimated Distance to Tourist Place", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text((String.format("%.3f", resto.distance_part_of_cluster) + " km") ?: "", style = b2)
    Spacer(Modifier.height(12.dp))
    Text("Address", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(resto.vicinity ?: "", style = b2)
}

//@Preview
//@Composable
//fun Preview() {
//    PlaceDetailScreen(rememberNavController())
//}