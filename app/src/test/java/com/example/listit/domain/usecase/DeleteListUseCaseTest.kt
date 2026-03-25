package com.example.listit.domain.usecase

import com.example.listit.data.fake.FakeListRepository
import com.example.listit.data.fake.FakeListItemRepository
import com.example.listit.domain.model.DEFAULT_LIST_ID
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteListUseCaseTest {

    private lateinit var listRepository: FakeListRepository
    private lateinit var itemRepository: FakeListItemRepository
    private lateinit var addList: AddListUseCase
    private lateinit var deleteList: DeleteListUseCase
    private lateinit var addItem: AddItemUseCase

    @Before
    fun setup() {
        listRepository = FakeListRepository()
        itemRepository = FakeListItemRepository()
        addList = AddListUseCase(listRepository)
        deleteList = DeleteListUseCase(listRepository)
        addItem = AddItemUseCase(itemRepository)
    }

    @Test
    fun `delete list removes it from repository`() = runTest {
        addList("Work")
        val lists = listRepository.observeAll().first()
        val workList = lists.first { it.name == "Work" }

        deleteList(workList.id)

        val remaining = listRepository.observeAll().first()
        assertTrue(remaining.none { it.id == workList.id })
    }

    @Test
    fun `delete list does not affect other lists`() = runTest {
        addList("Work")
        addList("Home")

        val lists = listRepository.observeAll().first()
        val workList = lists.first { it.name == "Work" }

        deleteList(workList.id)

        val remaining = listRepository.observeAll().first()
        assertTrue(remaining.any { it.name == "Home" })
        assertTrue(remaining.any { it.id == DEFAULT_LIST_ID })
    }

    @Test
    fun `add and delete multiple lists`() = runTest {
        addList("A")
        addList("B")
        addList("C")

        assertEquals(4, listRepository.observeAll().first().size) // 3 + default

        val lists = listRepository.observeAll().first()
        val listB = lists.first { it.name == "B" }
        deleteList(listB.id)

        val remaining = listRepository.observeAll().first()
        assertEquals(3, remaining.size)
        assertTrue(remaining.none { it.name == "B" })
    }
}
