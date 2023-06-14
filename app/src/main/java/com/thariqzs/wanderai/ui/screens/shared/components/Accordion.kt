package com.thariqzs.wanderai.ui.screens.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.theme.*

data class AccordionModel(
    val header: String,
    val rows: List<Row>
) {
    data class Row(
        val security: String,
        val price: String
    )
}
//
//@Composable
//fun AccordionGroup(modifier: Modifier = Modifier, group: List<AccordionModel>) {
//    Column(modifier = modifier) {
//        group.forEach {
//            Accordion(model = it, title)
//        }
//    }
//}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Accordion(modifier: Modifier = Modifier, header: String, children: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, BlueLight.copy(0.2f), RoundedCornerShape(24.dp))) {
        AccordionHeader(title = header, isExpanded = expanded) {
            expanded = !expanded
        }
        AnimatedVisibility(visible = expanded) {
            Surface(
                color = White
//                shape = RoundedCornerShape(24.dp),
//                modifier = Modifier.padding(top = 8.dp)
            ) {
                Column (Modifier.fillMaxWidth().padding(20.dp)) {
                    children()
                }
            }
        }
    }
}

@Preview
@Composable
private fun AccordionHeader(
    title: String = "Header",
    isExpanded: Boolean = false,
    onTapped: () -> Unit = {}
) {
    val degrees = if (isExpanded) 180f else 0f

    Surface(
//        Modifier.border(1.dp, BlueLight.copy(0.2f), RoundedCornerShape(isExpanded ? 24.dp)),
        color = White,
//        shape = RoundedCornerShape(24.dp),
//        border = BorderStroke(1.dp, BlueLight.copy(0.2f)),
//        elevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .clickable { onTapped() }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, Modifier.weight(1f), style = sh2, color = Gray600, maxLines = 1)
            Surface(shape = RoundedCornerShape(8.dp), color = OrangeNormal) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_bottom),
                    contentDescription = "arrow-down",
                    modifier = Modifier.rotate(degrees),
                    tint = White
                )
            }
        }
    }
}

@Preview
@Composable
private fun AccordionRow(
    model: AccordionModel.Row = AccordionModel.Row("AAPL", "$328.89")
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(model.security, Modifier.weight(1f), style = h1, color = Gray300)
        Surface(color = YellowNormal, shape = RoundedCornerShape(8.dp)) {
            Text(
                text = model.price,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                style = h2,
                color = White
            )
        }
    }
}