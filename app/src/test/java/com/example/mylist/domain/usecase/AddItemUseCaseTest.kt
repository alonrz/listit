package com.example.mylist.domain.usecase

import com.example.mylist.data.fake.FakeListItemRepository
import com.example.mylist.domain.model.DEFAULT_LIST_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddItemUseCaseTest {

    private lateinit var repository: FakeListItemRepository
    private lateinit var addItem: AddItemUseCase

    @Before
    fun setup() {
        repository = FakeListItemRepository()
        addItem = AddItemUseCase(repository)
    }

    @Test
    fun `add item defaults to default list`() = runTest {
        val initialSize = repository.observeAll().first().size

        addItem("New task")

        val items = repository.observeAll().first()
        assertEquals(initialSize + 1, items.size)

        val added = items.last()
        assertEquals("New task", added.title)
        assertEquals(DEFAULT_LIST_ID, added.listId)
    }

    @Test
    fun `add item to specific list`() = runTest {
        val customListId = "grocery-list"

        addItem("Buy milk", customListId)

        val items = repository.observeAll().first()
        val added = items.last()
        assertEquals("Buy milk", added.title)
        assertEquals(customListId, added.listId)
    }

    @Test
    fun `add multiple items to different lists`() = runTest {
        addItem("Default task")
        addItem("Work task", "work")
        addItem("Home task", "home")

        val all = repository.observeAll().first()
        val workItems = all.filter { it.listId == "work" }
        val homeItems = all.filter { it.listId == "home" }

        assertEquals(1, workItems.size)
        assertEquals("Work task", workItems[0].title)
        assertEquals(1, homeItems.size)
        assertEquals("Home task", homeItems[0].title)
    }

    @Test
    fun `added items have unique ids`() = runTest {
        addItem("Task A")
        addItem("Task B")

        val items = repository.observeAll().first()
        val ids = items.map { it.id }.toSet()
        assertEquals(items.size, ids.size)
    }
}
