import entities.*;

import javax.persistence.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final BufferedReader BFR = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

//        changeCasing(entityManager);
//
//        getNumberOfEmployees(entityManager);
//
//        getHighSalary(entityManager);
//
//        getByDepartment(entityManager);
//
//        setEmployeeAddress(entityManager);
//
//        printAddresses(entityManager);
//
//        getById(entityManager);
//
//        increaseSalaries(entityManager);
//
//        printLastProjects(entityManager);
//
//        getByFirstLetters(entityManager);
//
//        getGroupedSalaryResult(entityManager);
//
//        removeTown(entityManager);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private static void removeTown(EntityManager entityManager) throws IOException {
        String input = BFR.readLine();

        List<Town> resultList = entityManager
                .createQuery("FROM Town WHERE name = :name", Town.class)
                .setParameter("name", input)
                .getResultList();

        if (!resultList.isEmpty()) {
            Town town = resultList.get(0);
            List<Address> resultListAddress = entityManager.createQuery("FROM Address a JOIN a.town t WHERE t.name= :town", Address.class)
                    .setParameter("town", town.getName())
                    .getResultList();
            for (Address address : resultListAddress) {
                for (Employee employee : address.getEmployees()) {
                    employee.setAddress(null);
                }
                entityManager.remove(address);
            }
            System.out.printf("%d addresses in %s deleted%n", resultListAddress.size(), town.getName());
            entityManager.remove(town);
        }
    }

    private static void getGroupedSalaryResult(EntityManager entityManager) {
        List<Object[]> result = entityManager.createQuery("SELECT d.name, MAX(e.salary) FROM Department d " +
                "JOIN d.employees e " +
                "GROUP BY d.name " +
                "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000")
                        .getResultList();
        result.forEach(r -> {
                    System.out.printf("%s %s%n", r[0], r[1]); });
    }

    private static void getByFirstLetters(EntityManager entityManager) throws IOException {
        String input = BFR.readLine();
        entityManager.createQuery("FROM Employee e WHERE e.firstName LIKE :firstName", Employee.class )
                        .setParameter("firstName", input + "%")
                .getResultList()
                .forEach(employee -> {
                        System.out.printf("%s %s - %s - (%s)\n", employee.getFirstName(),
                                employee.getLastName(), employee.getJobTitle(), employee.getSalary());
                    }
                );
    }

    private static void printLastProjects(EntityManager entityManager) {
        entityManager.createQuery("FROM Project p ORDER BY p.startDate DESC, name", Project.class)
                .setMaxResults(10)
                .getResultList()
                .forEach(project -> {
            System.out.printf("Project name: %s\n" +
                    " \tProject Description: %s\n" +
                    " \tProject Start Date: %s\n" +
                    " \tProject End Date: %s\n", project.getName(), project.getDescription(),
                    project.getStartDate(), project.getEndDate());
        });
    }

    private static void increaseSalaries(EntityManager entityManager) {
        entityManager.createQuery("UPDATE Employee e SET e.salary = e.salary * 1.12 " +
                        "WHERE e.department.id IN (SELECT d.id FROM Department d WHERE d.name = 'Engineering' " +
                        "OR d.name = 'Tool Design' OR d.name = 'Marketing' OR d.name = 'Information Services')")
                .executeUpdate();

        entityManager.createQuery("SELECT e FROM Employee e WHERE e.department.id IN (SELECT d.id FROM Department d WHERE d.name = 'Engineering' " +
                        "OR d.name = 'Tool Design' OR d.name = 'Marketing' OR d.name = 'Information Services')", Employee.class)
                .getResultList().forEach(employee -> {
                    System.out.printf("%s %s %.2f%n",
                            employee.getFirstName(), employee.getLastName(), employee.getSalary());
                });
    }

    private static void getById(EntityManager entityManager) throws IOException {
        int id = Integer.parseInt(BFR.readLine());
        Employee employee = entityManager.createQuery(
                        "SELECT e FROM Employee e WHERE e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());
        employee.getProjects().forEach(
                project -> {
                    System.out.printf("     %s%n", project.getName());
                }
        );
    }

    private static void printAddresses(EntityManager entityManager) {
        entityManager.createQuery("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults(10)
                .getResultStream()
                .forEach(
                        address -> {
                            System.out.printf("%s, %s, %d%n",
                                    address.getText(), address.getTown().getName(), address.getEmployees().size());
                        }
                );
    }

    private static void setEmployeeAddress(EntityManager entityManager) throws IOException {
        String input = BFR.readLine();
        Address address = new Address();
        address.setText("Vitoshka 15");

        entityManager.persist(address);

        Employee employee = entityManager.createQuery("FROM Employee WHERE lastName = :lastName", Employee.class)
                .setParameter("lastName", input)
                .getSingleResult();

        employee.setAddress(address);
        entityManager.persist(employee);
    }

    private static void getByDepartment(EntityManager entityManager) {
        entityManager.createQuery("FROM Employee WHERE department.name = 'Research and Development' " +
                    "ORDER BY salary, id", Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("first name: %s, last name: %s, department name: %s, salary: %s%n",
                            employee.getFirstName(), employee.getLastName(), employee.getDepartment().getName(), employee.getSalary());
                });
    }

    private static void getHighSalary(EntityManager entityManager) {
        TypedQuery<Employee> query = entityManager
                .createQuery("FROM Employee e WHERE salary > 50000", Employee.class);
        query.getResultList().forEach(
                employee -> {
                    System.out.println(employee.getFirstName());
                }
        );
    }

    private static void getNumberOfEmployees(EntityManager entityManager) throws IOException {
        String[] name = BFR.readLine().split(" ");
        List<Employee> resultList = entityManager.createQuery("FROM Employee WHERE firstName = :firstName AND lastName = :lastName", Employee.class)
                .setParameter("firstName", name[0])
                .setParameter("lastName", name[1])
                .getResultList();
        if (resultList.isEmpty()) {
            System.out.println("No");
        } else {
            System.out.println("Yes");
        }
    }

    private static void changeCasing(EntityManager entityManager) {
        entityManager.createQuery("UPDATE Town SET name = upper(name) WHERE length(name) > 5")
                .executeUpdate();
//        List<Town> resultList = entityManager.createQuery("FROM Town WHERE LENGTH('name') > 5", Town.class).getResultList();
//        resultList.forEach(
//                town -> {
//                    town.setName(town.getName().toUpperCase());
//                    entityManager.persist(town);
//                }
//        );
    }
}
