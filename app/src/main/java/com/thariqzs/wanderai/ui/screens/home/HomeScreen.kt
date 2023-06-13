package com.thariqzs.wanderai.ui.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.thariqzs.wanderai.R
import com.thariqzs.wanderai.ui.Routes
import com.thariqzs.wanderai.ui.theme.BlueLight
import com.thariqzs.wanderai.ui.theme.BlueNormal
import com.thariqzs.wanderai.ui.theme.Gray200
import com.thariqzs.wanderai.ui.theme.OrangeLight
import com.thariqzs.wanderai.ui.theme.OrangeNormal
import com.thariqzs.wanderai.ui.theme.PinkNormal
import com.thariqzs.wanderai.ui.theme.a
import com.thariqzs.wanderai.ui.theme.b2
import com.thariqzs.wanderai.ui.theme.h3
import com.thariqzs.wanderai.ui.theme.h4
import com.thariqzs.wanderai.ui.theme.sh2
import com.thariqzs.wanderai.utils.CoroutinesErrorHandler
import com.thariqzs.wanderai.utils.TokenViewModel
import com.thariqzs.wanderai.utils.createImageFile
import java.io.File
import java.io.InputStream

@Composable
fun HomeScreen(navController: NavController, vm: TokenViewModel, hvm: HomeViewModel) {
    val TAG = "hsthoriq"

    HomeScreenBody(navController = navController, vm, hvm)
    if (hvm.showDialog) {
        val context = LocalContext.current

        val file = createImageFile(context)
        val uri = FileProvider.getUriForFile(
            context,
            "com.thariqzs.wanderai", file
        )


        val pickPictureLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.GetContent()
        ) { imageUri ->
            if (imageUri != null) {
//                val file = File(imageUri.path)
//                if (file != null) {

                    hvm.imageUri = imageUri

                    hvm.sendImage(object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            Log.d("hsthoriq senimage", "onError: $message")
                        }
                    })
                    hvm.printUri()
                    hvm.showDialog = false
//                }
            }
        }

        val cameraLauncher =
            rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
                hvm.imageUri = uri
    //            hvm.sendImage(object : CoroutinesErrorHandler {
    //                override fun onError(message: String) {
    //                    Log.d("hsthoriq senimage", "onError: $message")
    //                }
    //            })
                hvm.printUri()
                hvm.showDialog = false
            }

        val permissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                cameraLauncher.launch(uri)
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

        Dialog(onDismissRequest = { hvm.showDialog = false }) {
            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 100.dp),
                shape = RoundedCornerShape(16.dp),
                color = Gray200,
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(text = "Travel Recognition", style = h4, color = Color.Black)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "ic_close",
                            tint = PinkNormal,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable {
                                    hvm.showDialog = false
                                }
                        )
                    }
                    TakeImageCard(R.drawable.ic_camera, "Camera") {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(uri)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    TakeImageCard(
                        R.drawable.ic_photo_library,
                        "Gallery"
                    ) { pickPictureLauncher.launch("image/*") }
                }
            }
        }
    }
}

@Composable
fun HomeScreenBody(navController: NavController, vm: TokenViewModel, hvm: HomeViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .drawWithContent {
                drawContent()
            }
    ) {

        Header(name = "Rey", navController = navController, vm = vm)
        Body(navController, hvm)
        ListPlan(navController)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_bottom_van),
            contentDescription = "ic_bottom_van",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(280.dp)
                .align(Alignment.BottomEnd),

            )

        if (hvm.imageUri.path?.isNotEmpty() == true) {
            Image(
                modifier = Modifier
                    .padding(16.dp, 8.dp),
                painter = rememberImagePainter(hvm.imageUri),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Header(name: String, navController: NavController, vm: TokenViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Text("WanderAI", style = a, color = BlueNormal)
            Text("\uD83D\uDC4B Halo, $name!", style = h4)
        }
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(color = BlueNormal, shape = RoundedCornerShape(20.dp))
                .clickable {
                    navController.navigate(Routes.Profile)
                },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_user_circle),
                contentDescription = "ic_user_circle",
                tint = Color.White,
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun Body(navController: NavController, hvm: HomeViewModel) {
    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        FeatureCard(
            icon = "✈️",
            label = "Travel\n" +
                    "Planning",
            "Travel-bot siap membantu untuk membuat rencana yang kamu mau!",
            image = R.drawable.ic_robot,
            btnColor = BlueNormal,
            handleNavigate = { navController.navigate(Routes.TravelPlan) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        FeatureCard(
            icon = "\uD83D\uDE82",
            label = "Travel\n" +
                    "Recognition",
            "Buat rencana perjalanan sesuai keinginanmu secara otomatis!",
            image = R.drawable.ic_phone,
            btnColor = OrangeNormal, handleNavigate = {
                hvm.showDialog = true

            }
        )
    }
}

@Composable
fun FeatureCard(
    icon: String,
    label: String,
    description: String,
    image: Int,
    btnColor: Color,
    handleNavigate: () -> Unit
) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)
    val orangeLightWithOpacity = OrangeLight.copy(alpha = 0.2f)

    val backgroundColor = if (btnColor == BlueNormal) {
        blueLightWithOpacity
    } else {
        orangeLightWithOpacity
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(all = 16.dp)
            .height(IntrinsicSize.Min)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(icon, style = h3, modifier = Modifier.padding(bottom = 8.dp))
            Text(
                label, style = h3, color = btnColor, modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(description, style = a)
        }
        Spacer(modifier = Modifier.width(30.dp))
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .size(85.dp)
                    .background(Color.White, RoundedCornerShape(60.dp))
                    .padding(bottom = 4.dp),
            ) {
                Image(
                    painter = painterResource(image),
                    contentDescription = "ic_user_circle",
                    modifier = Modifier
                        .align(
                            Alignment.Center
                        )
                        .size(60.dp),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(btnColor, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { handleNavigate() }

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "ic_chevron_right",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
        }
    }
}

@Composable
fun ListPlan(navController: NavController) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)

    Column(
        modifier = Modifier.background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("List Plan", style = h4)
            Row(
                Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { navController.navigate(Routes.ListPlan) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Lihat Selengkapnya", style = a, color = BlueNormal)
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "ic_chevron_right",
                    tint = BlueNormal,
                    modifier = Modifier
                        .size(16.dp)
                )
            }
        }
        LazyRow(
            modifier = Modifier.background(Color.Transparent),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(5) {
                Box(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(24.dp))
                        .clip(RoundedCornerShape(24.dp))
                        .clickable { navController.navigate(Routes.PlanDetail) }
                        .border(
                            BorderStroke(1.dp, blueLightWithOpacity),
                            RoundedCornerShape(24.dp)
                        )
                        .padding(24.dp)
                ) {
                    Column() {
                        Text("Bandung", style = sh2)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row() {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "ic_calendar",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("24 Mei 2023", style = b2)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TakeImageCard(icon: Int, text: String, onPressButton: () -> Unit) {
    val blueLightWithOpacity = BlueLight.copy(alpha = 0.2f)

    Card(
        Modifier
            .border(1.dp, blueLightWithOpacity, RoundedCornerShape(24.dp))
            .background(Color.White, RoundedCornerShape(24.dp))
            .padding(24.dp)
    ) {
        Row(Modifier.background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Column() {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "icon",
                    modifier = Modifier.size(32.dp),
                    tint = BlueNormal
                )
                Text("Recognition from", style = b2)
                Text(text, style = sh2)
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(OrangeNormal, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onPressButton() }

            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_right),
                    contentDescription = "ic_chevron_right",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 380)
//@Composable
//fun Preview() {
//    HomeScreen(rememberNavController())
//}
