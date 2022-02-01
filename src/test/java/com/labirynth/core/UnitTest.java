package com.labirynth.core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;

public class UnitTest {
	@Test
	public void insertTest() throws InvalidDataException {
		System.out.println("Insertion test");
		Queue q = new Queue();
		assertEquals(0, q.getSize());
		Book nb1 = new Book("aaa", "sadas", 1998);
		q.insert(nb1);
		assertEquals(1, q.getSize());
		assertEquals(null, q.getLast());
		assertEquals(nb1, q.getFirst());
		Book nb2 = new Book("aaa", "sadas", 1996);
		q.insert(nb2);
		assertEquals(2, q.getSize());
		assertEquals(nb1, q.getLast());
		assertEquals(nb2, q.getFirst());
		q.insert(new Book("aaa", "sadas", 1997));
		assertEquals(3, q.getSize());
		assertEquals(nb1, q.getLast());
		assertEquals(nb2, q.getFirst());
	}
	
	@Test
	public void readFileTest() throws InvalidDataException {
		System.out.println("Reading from existing file test");
		Queue q = new Queue();
		assertTrue(q.readFromFile("ListOfBooks.txt"));
		assertEquals(12, q.getSize());
	}
	
	@Test
	public void readFileTest2() throws InvalidDataException {
		System.out.println("Reading from non-existing file test");
		Queue q = new Queue();
		assertFalse(q.readFromFile("aaa.txt"));
		assertEquals(0, q.getSize());
	}
	
	@Test
	public void popFrontTest() throws InvalidDataException {
		System.out.println("Pop front test");
		Queue q = new Queue();
		Book nb1 = new Book("aaa", "sadas", 1994);
		Book nb2 = new Book("aaa", "sadas", 1993);
		Book nb3 = new Book("aaa", "sadas", 1992);
		Book nb4 = new Book("aaa", "sadas", 1995);
		Book nb5 = new Book("aaa", "sadas", 1991);
		q.insert(nb1);
		q.insert(nb2);
		q.insert(nb3);
		q.insert(nb4);
		q.insert(nb5);
		assertEquals(q.getFirst(), nb5);
		assertEquals(q.getLast(), nb4);
		assertEquals(q.getSize(), 5);
		assertEquals(q.popFront(), nb5);
		assertEquals(q.getFirst(), nb3);
		assertEquals(q.getLast(), nb4);
		assertEquals(q.getSize(), 4);
		assertEquals(q.popFront(), nb3);
		assertEquals(q.getFirst(), nb2);
		assertEquals(q.getLast(), nb4);
		assertEquals(q.getSize(), 3);
		assertEquals(q.popFront(), nb2);
		assertEquals(q.getFirst(), nb1);
		assertEquals(q.getLast(), nb4);
		assertEquals(q.getSize(), 2);
		assertEquals(q.popFront(), nb1);
		assertEquals(q.getFirst(), nb4);
		assertEquals(q.getLast(), null);
		assertEquals(q.getSize(), 1);
		assertEquals(q.popFront(), nb4);
		assertEquals(q.getFirst(), null);
		assertEquals(q.getLast(), null);
		assertEquals(q.getSize(), 0);
	}
	
	@Test
	public void popBackTest() throws InvalidDataException {
		System.out.println("Pop back test");
		Queue q = new Queue();
		Book nb1 = new Book("aaa", "sadas", 1994);
		Book nb2 = new Book("aaa", "sadas", 1993);
		Book nb3 = new Book("aaa", "sadas", 1992);
		Book nb4 = new Book("aaa", "sadas", 1995);
		Book nb5 = new Book("aaa", "sadas", 1991);
		q.insert(nb1);
		q.insert(nb2);
		q.insert(nb3);
		q.insert(nb4);
		q.insert(nb5);
		assertEquals(q.getFirst(), nb5);
		assertEquals(q.getLast(), nb4);
		assertEquals(q.getSize(), 5);
		assertEquals(q.popBack(), nb4);
		assertEquals(q.getFirst(), nb5);
		assertEquals(q.getLast(), nb1);
		assertEquals(q.getSize(), 4);
		assertEquals(q.popBack(), nb1);
		assertEquals(q.getFirst(), nb5);
		assertEquals(q.getLast(), nb2);
		assertEquals(q.getSize(), 3);
		assertEquals(q.popBack(), nb2);
		assertEquals(q.getFirst(), nb5);
		assertEquals(q.getLast(), nb3);
		assertEquals(q.getSize(), 2);
		assertEquals(q.popBack(), nb3);
		assertEquals(q.getFirst(), nb5);
		assertEquals(q.getLast(), null);
		assertEquals(q.getSize(), 1);
		assertEquals(q.popBack(), nb5);
		assertEquals(q.getFirst(), null);
		assertEquals(q.getLast(), null);
		assertEquals(q.getSize(), 0);
	}
	
	@Test
	public void getTest() throws InvalidDataException {
		System.out.println("Get test");
		Queue q = new Queue();
		Book nb1 = new Book("aaa", "sadas", 1994);
		Book nb2 = new Book("aaa", "sadas", 1993);
		Book nb3 = new Book("aaa", "sadas", 1992);
		Book nb4 = new Book("aaa", "sadas", 1995);
		Book nb5 = new Book("aaa", "sadas", 1991);
		q.insert(nb1);
		q.insert(nb2);
		q.insert(nb3);
		q.insert(nb4);
		q.insert(nb5);
		Book books[] = q.getAllWithKey(1993);
		assertEquals(1, books.length);
		assertEquals(books[0], nb2);
		books = q.getAllGreaterThanKey(1993);
		assertEquals(2, books.length);
		assertEquals(books[0], nb1);
		assertEquals(books[1], nb4);
		books = q.getAllLessThanKey(1993);
		assertEquals(2, books.length);
		assertEquals(books[0], nb5);
		assertEquals(books[1], nb3);
		int keys[] = {1991, 1993, 1995};
		books = q.getAllWithKey(keys);
		assertEquals(3, books.length);
		assertEquals(books[0], nb5);
		assertEquals(books[1], nb2);
		assertEquals(books[2], nb4);
	}
	
	@Test
	public void deleteTest() throws InvalidDataException {
		System.out.println("Delete test");
		Queue q = new Queue();
		Book nb1 = new Book("aaa", "sadas", 1994);
		Book nb2 = new Book("aaa", "sadas", 1993);
		Book nb3 = new Book("aaa", "sadas", 1992);
		Book nb4 = new Book("aaa", "sadas", 1995);
		Book nb5 = new Book("aaa", "sadas", 1991);
		q.insert(nb1);
		q.insert(nb2);
		q.insert(nb3);
		q.insert(nb4);
		q.insert(nb5);
		assertEquals(1, q.deleteAllWithKey(1993));
		assertEquals(4, q.getSize());
		assertEquals(2, q.deleteAllGraterThanKey(1993));
		assertEquals(2, q.getSize());
		assertEquals(2, q.deleteAllLessThanKey(1993));
		assertEquals(0, q.getSize());
		assertEquals(q.getFirst(), null);
		assertEquals(q.getLast(), null);
		q.insert(nb1);
		q.insert(nb2);
		q.insert(nb3);
		q.insert(nb4);
		q.insert(nb5);
		assertEquals(5, q.getSize());
		int keys[] = {1991, 1993, 1995};
		assertEquals(3, q.deleteAllWithKey(keys));
		assertEquals(2, q.getSize());
		assertEquals(q.getFirst(), nb3);
		assertEquals(q.getLast(), nb1);
	}
}
