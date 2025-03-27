package com.base.streams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Employee
{
	private String name;

	private int age;

	private int salary;

	private List<Project> projects;
}
