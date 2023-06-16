package com.thariqzs.wanderai.ui.screens.plandetail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.data.api.model.AccomodationData
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.HistoryDetail
import com.thariqzs.wanderai.data.api.model.RestaurantData
import com.thariqzs.wanderai.data.api.model.TourismData
import com.thariqzs.wanderai.ui.screens.home.HomeViewModel
import com.thariqzs.wanderai.ui.screens.shared.components.Accordion
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.b1
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import com.thariqzs.wanderai.utils.formatAmountRange
import com.thariqzs.wanderai.utils.formatDateRange

@Composable
fun PlanDetailScreen(navController: NavController, docId: String, hvm: HomeViewModel) {
    val TAG = "pdsthoriq"
    val context = LocalContext.current
    val historyDetailRes by hvm._historyDetailResponse.observeAsState()

    LaunchedEffect(docId) {
        if (docId != null) {
            hvm.getHistoryDetail(object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    Log.d(TAG, "onError: $message")
                }
            }, docId)
        }
    }

    when (val response = historyDetailRes) {
        is ApiResponse.Success -> {
            val data = response.data.data
//            Log.d(TAG, "res: ${response.data.data.description}")
//            Log.d(TAG, "data1212: ${hvm.navigationCompleted}")
            if (data != null && !hvm.navigationCompleted) {
                hvm.historyDetail = data
            }
        }

        is ApiResponse.Failure -> {
            val errorMessage = response.errorMessage
            Toast.makeText(context, "${errorMessage}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "failure: ${errorMessage}")
        }

        is ApiResponse.Loading -> {
            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "loading... ")
        }

        else -> {
//            Log.d(TAG, "else: ${response}")
        }
    }
    PlanDetailBody(navController, hvm)
}

@Composable
fun PlanDetailBody(navController: NavController, hvm: HomeViewModel) {
    Image(
        painter = painterResource(id = R.drawable.plan_detail_banner),
        contentDescription = "plan_detail_banner"
    )
    Column(
        Modifier.fillMaxSize()
    ) {
        PlanDetailScreenHeader(navController, hvm.historyDetail.city ?: "")
        PlanDetailScreenBody(hvm.historyDetail)
    }
}

@Composable
fun PlanDetailScreenHeader(navController: NavController, title: String) {
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
                .clickable { navController.popBackStack() }
        )
        Text(text = title, style = h4, color = Color.White)
    }
}

@Composable
fun PlanDetailScreenBody(data: HistoryDetail) {
    val TAG = "pdsthoriq"
    val blueNormalWithOpacity = BlueNormal.copy(alpha = 0.25f)

    val formattedDateRange = data.date_start?.let { it1 ->
        data.date_end?.let { it2 ->
            formatDateRange(
                it1,
                it2
            )
        }
    }

    val formattedBudget = data.data?.total_cost_minimum?.let {
        data.data.total_cost_maximum?.let { it1 ->
            formatAmountRange(
                it,
                it1
            )
        }
    }

    val formattedPrsn = data.data?.cost_minimum_per_person?.let {
        data.data?.total_cost_minimum?.div(
            it
        )
    }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                Modifier
            ) {
                Text("\uD83D\uDDD3️ Date", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(formattedDateRange ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "\uD83D\uDCDD Description",
                    style = h4,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(data.description ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text("\uD83D\uDCB5 Budget", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(formattedBudget ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "\uD83D\uDC64 Jumlah orang",
                    style = h4,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    formattedPrsn.toString() ?: "-",
                    style = b1
                )
            }
        }
        if (data.data != null) {
            if (data.data.tourism_lists_each_day != null) {
                for (i in 0..((data.data.tourism_lists_each_day.size - 1) ?: 0)) {
                    Spacer(Modifier.height(16.dp))
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(40.dp))
                            .background(blueNormalWithOpacity)
                            .padding(vertical = 12.dp)
                    ) {
                        Text(
                            "Rencana Day ${i + 1}",
                            style = h4,
                            color = BlueNormal,
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Tourism List", style = sh2, color = BlueNormal)
                    for (j in 0..((data.data.tourism_lists_each_day[i].size - 1) ?: 0)) {
                        val tourism = data.data.tourism_lists_each_day[i][j]
                        Accordion(header = tourism.name ?: "") {
                            TourismItem(tourism)
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    if (data.data.restaurants_recommendations_each_day != null) {
                        Text("Restaurant Recommendation", style = sh2, color = BlueNormal)
                        for (j in 0..((data.data.restaurants_recommendations_each_day[i].size - 1)
                            ?: 0)) {
                            val restaurant = data.data.restaurants_recommendations_each_day[i][j]
                            Accordion(header = restaurant.name ?: "") {
                                RestaurantItem(restaurant)
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
                    .background(blueNormalWithOpacity)
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    "Accomodation",
                    style = h4,
                    color = BlueNormal,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
            Spacer(Modifier.height(16.dp))
            if (data.data.accommodations_recommendations != null) {
                for (i in 0..((data.data.accommodations_recommendations.size - 6) ?: 0)) {
                    val accomodation = data.data.accommodations_recommendations[i]
                    Accordion(header = accomodation.name ?: "") {
                        AccomodationItem(accomodation)
                    }
                }
            }
        }
    }
}

@Composable
fun TourismItem(tourism: TourismData) {
    if (tourism.image_link != null) {
        AsyncImage(
            model = (tourism.image_link ?: "-"),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
//                .height(288.dp)
                .clip(RoundedCornerShape(24.dp))
        )
    }
    Spacer(Modifier.height(12.dp))
    Text("Description", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(tourism.description ?: "-", style = b2)
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Category", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(tourism.category ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("City", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(tourism.city ?: "-", style = b2)
        }
    }
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Rating", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(tourism.rating.toString() ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("Cost Range", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(
                formatAmountRange(
                    tourism.cost_range_min ?: 0,
                    tourism.cost_range_max ?: tourism.cost_range_min ?: 0
                ) ?: "-", style = b2
            )
        }
    }
    Spacer(Modifier.height(12.dp))
    Text("Address", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(tourism.formatted_address ?: "-", style = b2)
}

@Composable
fun RestaurantItem(resto: RestaurantData) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(resto.link_restaurant)
            addStringAnnotation(
                tag = "URL",
                annotation = resto.link_restaurant ?: "-",
                start = 0,
                end = length
            )
        }
    }

    val urlHandler = LocalUriHandler.current

    Text("Link", style = sh2)
    Spacer(Modifier.height(4.dp))
    if (resto.link_restaurant?.isNotEmpty() == true) {
        ClickableText(
            text = annotatedString,
            onClick = { urlHandler.openUri(resto.link_restaurant) },
            style = b2
        )
    } else {
        Text("-", style = b2)
    }
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Type", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(resto.tipe_makanan ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("Level Price", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(resto.level_price.toString() ?: "-", style = b2)
        }
    }
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Rating", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(resto.rating.toString() ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("Cost Range", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(
                formatAmountRange(
                    resto.cost_range_min ?: 0,
                    resto.cost_range_max ?: resto.cost_range_min ?: 0
                ) ?: "-", style = b2
            )

        }
    }
    Spacer(Modifier.height(12.dp))
    Text("Estimated Distance to Tourist Place", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text((String.format("%.3f", resto.distance_part_of_cluster) + " km") ?: "-", style = b2)
    Spacer(Modifier.height(12.dp))
    Text("Address", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(resto.formatted_address ?: "-", style = b2)
}


@Composable
fun AccomodationItem(acc: AccomodationData) {
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Rating", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(acc.rating.toString() ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("Rating Level", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(acc.rate_level ?: "-", style = b2)
        }
    }
    Spacer(Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth()) {
        Column() {
            Text("Type", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text(acc.acommodation_type ?: "-", style = b2)
        }
        Spacer(Modifier.weight(1f))
        Column() {
            Text("Total Review", style = sh2)
            Spacer(Modifier.height(4.dp))
            Text((acc.num_of_reviews.toString() + " reviews") ?: "-", style = b2)
        }
    }
    Spacer(Modifier.height(12.dp))
    Text("Price per Night", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(("Rp" + acc.price_per_night?.replace(",", ".") + "/kamar/malam") ?: "-", style = b2)
    Spacer(Modifier.height(12.dp))
    Text("Address", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text(acc.formatted_address ?: "-", style = b2)
    Spacer(Modifier.height(12.dp))
    Text("Average Distance to Tourism Place", style = sh2)
    Spacer(Modifier.height(4.dp))
    Text((String.format("%.2f", acc.distance_avg) + " km") ?: "-", style = b2)
}

//@Preview(showBackground = true, widthDp = 360)
//@Composable
//fun Preview() {
//    PlanDetailScreen(rememberNavController())
//}
