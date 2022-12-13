package com.example.moviedb.compose.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.moviedb.R
import com.example.moviedb.compose.ui.base.ErrorEvent
import com.example.moviedb.compose.ui.base.ErrorType
import com.example.moviedb.compose.ui.base.StateViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxContent(
//    loading: Boolean = false,
//    refreshing: Boolean = false,
//    onRefresh: () -> Unit = {},
//    errorEvent: ErrorEvent? = null,
//    onClickErrorDialog: () -> Unit,
    enableRefresh: Boolean = false,
    backgroundColor: Color = Color.Black,
    viewModel: StateViewModel,
    content: @Composable () -> Unit,
) {
    val loading by viewModel.loading.collectAsState()
    val refreshing by viewModel.refreshing.collectAsState()
    val errorEvent by viewModel.errorEvent.collectAsState()

    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = { viewModel.doRefresh() }
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .pullRefresh(state = refreshState, enabled = enableRefresh)
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                content()
            }
        }
        if (loading) {
            CircularProgressIndicator()
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
        errorEvent?.let { error ->
            ErrorDialog(
                errorEvent = error,
                onClick = { viewModel.hideError() }
            )
        }
    }
}

@Composable
fun ErrorDialog(errorEvent: ErrorEvent, onClick: () -> Unit) {
    when (errorEvent.type) {
        ErrorType.NETWORK -> {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("Network error")
                },
                text = {
                    Text(stringResource(id = R.string.no_internet_connection))
                },
                confirmButton = {
                    Button(onClick = onClick) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
            )
        }
        ErrorType.TIMEOUT -> {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("Timeout error")
                },
                text = {
                    Text(stringResource(id = R.string.no_internet_connection))
                },
                confirmButton = {
                    Button(onClick = onClick) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
            )
        }
        ErrorType.HTTP_UNAUTHORIZED -> {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("HTTP_UNAUTHORIZED error")
                },
                text = {
                    Text(stringResource(id = R.string.no_internet_connection))
                },
                confirmButton = {
                    Button(onClick = onClick) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
            )
        }
        ErrorType.FORCE_UPDATE -> {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("FORCE_UPDATE error")
                },
                text = {
//                    Text(stringResource(id = R.string.no_internet_connection))
                },
                confirmButton = {
                    Button(onClick = onClick) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
            )
        }
        ErrorType.UNKNOWN -> {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("UNKNOWN error")
                },
                text = {
//                    Text(stringResource(id = R.string.no_internet_connection))
                },
                confirmButton = {
                    Button(onClick = onClick) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
            )
        }
    }
}