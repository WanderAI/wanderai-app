package com.thariqzs.wanderai.ui.screens.placedetail

import android.net.Uri
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
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.home.HomeViewModel
import com.thariqzs.wanderai.ui.theme.b1
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import java.util.Locale

@Composable
fun PlaceDetailScreen(navController: NavController, hvm: HomeViewModel) {
    val TAG = "pdsthoriq"
    BackHandler(true) {
        navController.navigate(Routes.Home)
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
                navController.navigate(Routes.Home)
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
        AsyncImage(
            model = (imgUri ?: "https://upload.wikimedia.org/wikipedia/commons/3/34/Monas_2.jpg"),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(288.dp)
                .clip(RoundedCornerShape(24.dp))
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column() {
                Text("Summary", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(place.detail?.summary ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Rating Tourism", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(place.detail?.rating_tourism.toString() ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Important Facts", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                for (i in 1..3) {
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "TEST")
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("Sejarah", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(
                    place.detail?.summary?:"-",
                    style = b1
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Probability", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(String.format("%.3f", place.probability) ?: "-", style = b1)
            }
        }
    }
}

//@Preview
//@Composable
//fun Preview() {
//    PlaceDetailScreen(rememberNavController())
//}