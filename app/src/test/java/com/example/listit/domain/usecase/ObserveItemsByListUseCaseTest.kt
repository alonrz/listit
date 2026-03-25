package com.example.listit.domain.usecase

import com.example.listit.data.fake.FakeListItemRepository
import com.example.listit.domain.model.DEFAULT_LIST_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ObserveItemsByListUseCaseTest {

    private lateinit var repository: FakeListItemRepository
    private lateinit var addItem: AddItemUseCase
    private lateinit var observeByList: ObserveItemsByListUseCase

    @Before
    fun setup() {
        repository = FakeListItemRepository()
        addItem = AddItemUseCase(repository)
        observeByList = ObserveItemsByListUseCase(repository)
    }

    @Test
    fun `observe returns only items for specified list`() = runTest {
        addItem("Default item")
        addItem("Work item", "work")
        addItem("Another work item", "work")

        val workItems = observeByList("work").first()
        assertEquals(2, workItems.size)
        assertTrue(workItems.all { it.listId == "work" })
    }

    @Test
    fun `observe returns empty list for list with no items`() = runTest {
        addItem("Some item", "other")

        val emptyList = observeByList("nonexistent").first()
        assertTrue(emptyList.isEmpty())
    }

    @Test
    fun `default list contains pre-seeded fake items`() = runTest {
        val defaultItems = observeByList(DEFAULT_LIST_ID).first()
        assertTrue(defaultItems.isNotEmpty())
        assertTrue(defaultItems.all { it.listId == DEFAULT_LIST_ID })
    }
}
