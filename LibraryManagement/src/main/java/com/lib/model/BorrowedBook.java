package com.lib.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BorrowedBook {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	@Column(name = "borrow_date", nullable = false)
	private LocalDate borrowDate;

	@Column(name = "return_date")
	private LocalDate returnDate;

	@Column(name = "due_date", nullable = false)
	private LocalDate dueDate;

	@Enumerated(EnumType.STRING)
	private ReturnStatus returnStatusBook;

	public enum ReturnStatus {
		RETURNED, NOT_RETURNED, OVERDUE, RETURNED_LATE
	}

	// Constructors, getters, setters, toString
	public BorrowedBook() {
	}

	public BorrowedBook(Member member, Book book, LocalDate borrowDate, LocalDate dueDate) {
		this.member = member;
		this.book = book;
		this.borrowDate = borrowDate;
		this.dueDate = dueDate;
		this.returnStatusBook = ReturnStatus.NOT_RETURNED;
	}

	// Getters and setters

	@Override
	public String toString() {
		return "BorrowedBook [id=" + id + ", member=" + member + ", book=" + book + ", borrowDate=" + borrowDate
				+ ", returnDate=" + returnDate + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public ReturnStatus getReturnStatus() {
		return returnStatusBook;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public void returned() {
		if (LocalDate.now().isAfter(dueDate)) {
			returnStatusBook = ReturnStatus.RETURNED_LATE;
			this.returnDate = LocalDate.now();
		} else {
			returnStatusBook = ReturnStatus.RETURNED;
			this.returnDate = LocalDate.now();
		}
	}

	public void checkOverdue() {
		if (returnDate == null && LocalDate.now().isAfter(dueDate)) {
			this.returnStatusBook = ReturnStatus.OVERDUE;
		}
	}
}
