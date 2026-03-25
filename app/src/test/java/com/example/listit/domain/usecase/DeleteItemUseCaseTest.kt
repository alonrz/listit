package com.example.listit.domain.usecase

import com.example.listit.data.fake.FakeListItemRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteItemUseCaseTest {

    private lateinit var repository: FakeListItemRepository
    private lateinit var addItem: AddItemUseCase
    private lateinit var deleteItem: DeleteItemUseCase

    @Before
    fun setup() {
        repository = FakeListItemRepository()
        addItem = AddItemUseCase(repository)
        deleteItem = DeleteItemUseCase(repository)
    }

    @Test
    fun `delete item removes it from repository`() = runTest {
        addItem("To delete")
        val items = repository.observeAll().first()
        val target = items.last()

        deleteItem(target.id)

        val remaining = repository.observeAll().first()
        assertTrue(remaining.none { it.id == target.id })
    }

    @Test
    fun `delete item does not affect other items`() = runTest {
        addItem("Keep me")
        addItem("Delete me")

        val items = repository.observeAll().first()
        val toDelete = items.last()
        val sizeBefore = items.size

        deleteItem(toDelete.id)

        val remaining = repository.observeAll().first()
        assertEquals(sizeBefore - 1, remaining.size)
        assertTrue(remaining.none { it.id == toDelete.id })
    }

    @Test
    fun `delete item from specific list does not affect other lists`() = runTest {
        addItem("Work task", "work")
        addItem("Home task", "home")

        val all = repository.observeAll().first()
        val workItem = all.first { it.listId == "work" }

        deleteItem(workItem.id)

        val remaining = repository.observeAll().first()
        assertTrue(remaining.none { it.listId == "work" && it.title == "Work task" })
        assertTrue(remaining.any { it.listId == "home" && it.title == "Home task" })
    }
}
