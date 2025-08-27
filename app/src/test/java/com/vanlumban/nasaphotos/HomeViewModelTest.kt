package com.vanlumban.nasaphotos

import com.vanlumban.nasaphotos.data.models.NasaPhoto
import com.vanlumban.nasaphotos.data.repository.NasaRepository
import com.vanlumban.nasaphotos.ui.screen.HomeViewModel
import com.vanlumban.nasaphotos.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: NasaRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRandomPhotos emits Success when repository returns data`() = runTest {
        val fakePhotos = listOf(
            NasaPhoto(
                date = "2017-12-11",
                title = "Title 1",
                imageUrl = "https://apod.nasa.gov/apod/image/9710/brightstar_hst.jpg",
                author = "Author 1"
            ),
            NasaPhoto(
                date = "2018-12-11",
                title = "Title 2",
                imageUrl = "https://apod.nasa.gov/apod/image/9710/brightstar_hst.jpg",
                author = "Author 2"
            ),
            NasaPhoto(
                date = "2019-12-11",
                title = "Title 3",
                imageUrl = "https://apod.nasa.gov/apod/image/9710/brightstar_hst.jpg",
                author = "Author 3"
            )
        )

        coEvery { repository.getRandomPhotos() } returns fakePhotos

        viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.state.value
        assert(state is UiState.Success)
        assertEquals(fakePhotos, (state as UiState.Success).data)
    }

    @Test
    fun `fetchRandomPhotos emits Error when repository throws exception`() = runTest {
        val errorMessage = "Network error"
        coEvery { repository.getRandomPhotos() } throws Exception(errorMessage)

        viewModel = HomeViewModel(repository)

        advanceUntilIdle()

        val state = viewModel.state.value
        assert(state is UiState.Error)
        assertEquals(errorMessage, (state as UiState.Error).message)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        coEvery { repository.getRandomPhotos() } returns emptyList()

        viewModel = HomeViewModel(repository)

        // Immediately after init
        val initialState = viewModel.state.value
        assert(initialState is UiState.Loading)
    }
}

