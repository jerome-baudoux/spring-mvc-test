package com.mvc.dto;

public class BillDto {

	private Integer id;
	private PersonDto person;
	private Integer price;
    
    public BillDto() {}

	public BillDto(Integer id, PersonDto person, Integer price) {
		super();
		this.id = id;
		this.person = person;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PersonDto getPerson() {
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
