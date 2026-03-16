package com.example.mylist.domain.usecase

import com.example.mylist.data.fake.FakeListItemRepository
import com.example.mylist.domain.model.DEFAULT_LIST_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoveItemUseCaseTest {

    private lateinit var repository: FakeListItemRepository
    private lateinit var addItem: AddItemUseCase
    private lateinit var moveItem: MoveItemUseCase

    @Before
    fun setup() {
        repository = FakeListItemRepository()
        addItem = AddItemUseCase(repository)
        moveItem = MoveItemUseCase(repository)
    }

    @Test
    fun `move item changes its list id`() = runTest {
        addItem("Movable task")
        val item = repository.observeAll().first().last()
        assertEquals(DEFAULT_LIST_ID, item.listId)

        moveItem(item.id, "work")

        val updated = repository.findById(item.id)
        assertEquals("work", updated.listId)
    }

    @Test
    fun `move item preserves title and status`() = runTest {
        addItem("Important task")
        val item = repository.observeAll().first().last()
        repository.updateStatus(item.id, true)

        moveItem(item.id, "archive")

        val updated = repository.findById(item.id)
        assertEquals("Important task", updated.title)
        assertEquals(true, updated.isDone)
        assertEquals("archive", updated.listId)
    }

    @Test
    fun `move item between custom lists`() = runTest {
        addItem("Task", "list-a")
        val item = repository.observeAll().first().last()

        moveItem(item.id, "list-b")

        val updated = repository.findById(item.id)
        assertEquals("list-b", updated.listId)
    }

    @Test
    fun `move does not affect other items`() = runTest {
        addItem("Stay put", "list-a")
        addItem("Moving", "list-a")

        val items = repository.observeAll().first()
        val stayItem = items[items.size - 2]
        val moveTarget = items.last()

        moveItem(moveTarget.id, "list-b")

        val stayUpdated = repository.findById(stayItem.id)
        assertEquals("list-a", stayUpdated.listId)
    }
}
