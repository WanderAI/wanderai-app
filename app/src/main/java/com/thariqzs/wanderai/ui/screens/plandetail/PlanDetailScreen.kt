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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.data.api.model.ApiResponse
import com.thariqzs.wanderai.data.api.model.HistoryDetail
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.screens.home.HomeViewModel
import com.thariqzs.wanderai.ui.screens.shared.components.Accordion
import com.thariqzs.wanderai.ui.screens.shared.components.AccordionModel
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.BlueOld
import com.thariqzs.wanderai.ui.theme.b1
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
//            Log.d(TAG, "res: ${response}")
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
                .clickable { navController.navigate(Routes.ListPlan) }
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

    val modelTechStocks = AccordionModel(
        header = "Technology Stocks",
        rows = mutableListOf(
            AccordionModel.Row(security = "AAPL", "$328.00"),
            AccordionModel.Row(security = "GOOGL", "$328.00"),
            AccordionModel.Row(security = "NFLX", "$198.00"),
            AccordionModel.Row(security = "META", "$200.00"),
            AccordionModel.Row(security = "TSLA", "$769.16"),
        )
    )

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(horizontal = 24.dp)

        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(Modifier
            ) {
                Text("\uD83D\uDDD3️ Date", style = h4, modifier = Modifier.padding(bottom = 4.dp))
                Text(formattedDateRange ?: "-", style = b1)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    "\uD83D\uDCDD Deskripsi",
                    style = h4,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text("-", style = b1)
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
        Spacer(Modifier.height(16.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(40.dp))
                .background(blueNormalWithOpacity)
                .padding(vertical = 12.dp)
        ) {
            Text("Rencana Day 1", style = h4, color = BlueNormal, modifier = Modifier.align(
                Alignment.Center))
        }
        Spacer(Modifier.height(16.dp))
        Text("Tourism List", style = sh2, color = BlueNormal)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Accordion(model = modelTechStocks)
        Text("Tourism List", style = sh2, color = BlueNormal)
    }
}

//@Preview(showBackground = true, widthDp = 360)
//@Composable
//fun Preview() {
//    PlanDetailScreen(rememberNavController())
//}
