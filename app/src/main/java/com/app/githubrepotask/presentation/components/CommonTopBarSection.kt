package com.app.githubrepotask.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.githubrepotask.R
import com.app.githubrepotask.ui.theme.DarkGrayBlack
@Composable
fun CommonTopBarSection(
    backButtonRequired: Boolean = true,
    navController: NavController,
    searchQuery: String,
    context : Context,
    submitButton : Boolean = false,
    onQueryChanged: (String) -> Unit,
    onClickSearch : (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 6.dp)
            .background(colorResource(id = R.color.white))
            .padding(top = 60.dp, start = 16.dp, end = 16.dp)
    ) {
        CommonTopBar(
            backButtonRequired = backButtonRequired,
            title = stringResource(R.string.txt_repositories),
            navController = navController
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchBar(query = searchQuery, onQueryChanged = onQueryChanged)
        Spacer(modifier = Modifier.height(12.dp))
        if(submitButton) {
            CommonButton(text = stringResource(R.string.txt_search)) {
                if (searchQuery.isBlank()) {
                    Toast.makeText(context, "Please Enter Username", Toast.LENGTH_SHORT).show()
                } else {
                    onClickSearch(searchQuery.trim())
                }
            }
        }
    }
}
@Composable
fun CommonTopBar(
    title: String,
    backButtonRequired : Boolean = true,
    navController: NavController,
    modifier: Modifier = Modifier,

    ) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (backButtonRequired) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back), // Use your back icon here
                    contentDescription = stringResource(R.string.txt_back),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)

                        .clickable {
                            navController.popBackStack()
                        }
                )
                Spacer(modifier = Modifier.padding(end = 20.dp))
            }

            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.W600 // or W500 depending on your design
                ),
                color = DarkGrayBlack
            )
        }
    }
}