package com.example.moviedb.utils

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class TestSpyMock {
    @Mock
    private lateinit var mockList: ArrayList<String>

    @Spy
    private lateinit var spyList: ArrayList<String>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `test mockList`() {
        mockList.add("1")
        // call method on mock object will do nothing
        assertEquals(null, mockList.getOrNull(0))
    }

    @Test
    fun `test spyList`() {
        spyList.add("1")
        // call method on spy object will execute normally
        assertEquals("1", spyList.getOrNull(0))
    }

    @Test
    fun `test mock with stub`() {
        val text = "Mock text"
        `when`(mockList[100]).thenReturn(text)

        assertEquals(text, mockList[100])
    }

    @Test
    fun `test spy with stub`() {
        val text = "Spy text"

        // this does not work
        // real method is called so spyList[100] throws IndexOutOfBoundsException (spyList is empty)
//        Mockito.`when`(spyList[100]).thenReturn(text)

        doReturn(text).`when`(spyList)[100]

        assertEquals(text, spyList[100])
    }

    @Test
    fun `test student`() {
        val student = spy<Student>()

        student.goToSchool()

        // was the method called once?
        verify(student, times(1)).goToSchool()
        verify(student, times(1)).getup()
        // This let's you check that no other methods where called on this object.
        // You call it after you have verified the expected method calls.
        verifyNoMoreInteractions(student)
    }
}
