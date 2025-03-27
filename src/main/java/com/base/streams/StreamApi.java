package com.base.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamApi
{
	public static void main(String[] args)
	{
		var employees = createEmployees();

		//названия проектов, сортировка по убыванию, преобразованы в верхний регистр, убраны дублирующиеся название проектов
		var projectNames = employees.stream()
				.map(Employee::getProjects)
				.flatMap(List::stream)
				.sorted(Comparator.comparingInt(Project::getDuration).reversed())
				.map(Project::getName)
				.map(String::toUpperCase)
				.distinct()
				.toList();

		//фильтрация работников по зп и возрасту, фильтрация проектов по длительности, мапа
		var projectMap = employees.stream()
				.filter(it -> it.getAge() > 30)
				.filter(it -> it.getAge() < 50)
				.filter(it -> it.getSalary() > 60000)
				.map(Employee::getProjects)
				.flatMap(List::stream)
				.filter(it -> it.getDuration() > 6)
				.collect(Collectors.groupingBy(Project::getName, Collectors.averagingInt(Project::getDuration)));

		System.out.println(projectNames);
		System.out.println(projectMap);
	}

	private static List<Employee> createEmployees()
	{
		return List.of(
				new Employee("Гена", 25, 14000, getProjectsRandom(2)),
				new Employee("Милорд", 30, 350000, getProjectsRandom(3)),
				new Employee("Мария", 37, 12000, getProjectsRandom(7)),
				new Employee("Павел", 55, 90000, getProjectsRandom(5)),
				new Employee("Ксенофонт", 12, 5000, getProjectsRandom(1)),
				new Employee("Миролюб", 49, 605000, getProjectsRandom(8)),
				new Employee("Розалин", 33, 65000, getProjectsRandom(4)),
				new Employee("Харли", 60, 120000, getProjectsRandom(10))
		);
	}

	private static List<Project> createProjects()
	{
		var projects = new ArrayList<Project>();

		projects.add(new Project("Авиасейлс", 12));
		projects.add(new Project("Рогалики", 6));
		projects.add(new Project("Чебурек", 15));
		projects.add(new Project("Чебурек", 8));
		projects.add(new Project("Компуктер", 7));
		projects.add(new Project("Новый проект", 5));
		projects.add(new Project("Тестовый проект", 2));
		projects.add(new Project("Тестовый проект", 21));
		projects.add(new Project("Ауди", 180));
		projects.add(new Project("Ауди", 9));

		return projects;
	}

	private static List<Project> getProjectsRandom(int size)
	{
		if (size == 0)
		{
			return List.of();
		}

		var projects = createProjects();
		size = Math.min(size, projects.size());

		Collections.shuffle(projects);
		return new ArrayList<>(projects.subList(0, size));
	}
}
