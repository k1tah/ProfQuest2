package com.example.profquest2.ui.screens.test

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.profquest2.R
import com.example.profquest2.ui.view.icon.Icon
import com.example.profquest2.ui.view.text.BodyText
import com.example.profquest2.ui.view.text.TitleText

@Composable
fun SelectTestScreen(navController: NavController) {
    val uriHandler = LocalUriHandler.current
    val tests = listOf(
        TestItem(R.drawable.test_1, { navController.navigate("test") }, R.string.golland_test),
        TestItem(R.drawable.test_2, { navController.navigate("secondTest") }, R.string.second_test),
        TestItem(
            R.drawable.test_3,
            { uriHandler.openUri("https://bvbinfo.ru/suits") },
            R.string.test_ticket_in_future
        ),
        TestItem(R.drawable.test_1, { navController.navigate("test") }, R.string.golland_test),
        TestItem(R.drawable.test_2, { navController.navigate("secondTest") }, R.string.second_test),
        TestItem(
            R.drawable.test_3,
            { uriHandler.openUri("https://bvbinfo.ru/suits") },
            R.string.test_ticket_in_future
        ),
        TestItem(R.drawable.test_1, { navController.navigate("test") }, R.string.golland_test),
        TestItem(R.drawable.test_2, { navController.navigate("secondTest") }, R.string.second_test),
        TestItem(
            R.drawable.test_3,
            { uriHandler.openUri("https://bvbinfo.ru/suits") },
            R.string.test_ticket_in_future
        )
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon = R.drawable.ic_arrow_left,
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            )
            Spacer(modifier = Modifier.weight(1f))

            TitleText(text = stringResource(id = R.string.select_test))
            Spacer(modifier = Modifier.width(16.dp))

            Spacer(modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(tests) { testItem ->
                TestImage(testItem)
            }
        }
    }
}

data class TestItem(@DrawableRes val image: Int, val onClick: () -> Unit, @StringRes val title: Int)

@Composable
fun TestImage(testItem: TestItem) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = testItem.image),
            contentDescription = null,
            modifier = Modifier
                .size(164.dp)
                .clickable { testItem.onClick() }
        )
        Spacer(modifier = Modifier.height(8.dp))

        BodyText(text = stringResource(id = testItem.title), textAlign = TextAlign.Center)
    }
}