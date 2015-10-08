package com.mvc.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A simple POJO representing a person
 * @author Jerome
 */
public class PersonDto {
	
    @NotNull(message="{error.person.id.not.null}")
    @Min(value=1, message="{error.person.id.min}")
    private Integer id;

    @NotNull(message="{error.person.first.name.not.null}")
    @Size(min=2, max=30, message="{error.person.first.name.size}")
    private String firstName;

    @NotNull(message="{error.person.last.name.not.null}")
    @Size(min=2, max=30, message="{error.person.last.name.size}")
    private String lastName;

	public PersonDto() {}

	public PersonDto(Integer id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}