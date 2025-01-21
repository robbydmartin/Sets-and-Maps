package assignment1;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Robby Martin
 * Assignment 1.
 *
 * This program reads Employee.txt and creates a hash map from the employee
 * records in the file. The rest of the program then allows user to add
 * employees, search the map for a specific employee, and display all employees.
 * @since January 28, 2023
 * I have followed the UNCG Academic Integrity policy on this assignment.
 */
public class Assignment1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        HashMap<Employee, Integer> hashMap = createHashMap();

        displayMenu(hashMap);

    }

    /**
     * Method to display menu and get user input.
     *
     * @param hashMap The map in which the menu will pass to other methods.
     */
    public static void displayMenu(HashMap<Employee, Integer> hashMap) {

        System.out.println("----- Menu -----");
        System.out.println("1. Display all employees");
        System.out.println("2. Add an employee");
        System.out.println("3. Search for employee");
        System.out.println("4. Exit program");
        System.out.println();
        System.out.print("Please enter menu choice: ");

        Scanner input = new Scanner(System.in);
        int userChoice = input.nextInt();

        if (userChoice == 1) {
            displayAllEmployees(hashMap);
        } else if (userChoice == 2) {
            addEmployee(hashMap);
        } else if (userChoice == 3) {
            searchEmployee(hashMap);
        } else if (userChoice == 4) {
            return;
        } else {
            System.out.println("Please select a number from the choices on the menu (1-4)");
            System.out.println();
            displayMenu(hashMap);
        }
    }

    /**
     * Method that creates hash map from employee file.
     *
     * @return hash map of employees in file.
     */
    public static HashMap<Employee, Integer> createHashMap() {

        HashMap<Employee, Integer> hashMap = new HashMap<Employee, Integer>();
        Integer attendanceCount = 1;

        try {
            File file = new File("employee.txt");
            Scanner readFile = new Scanner(file);
            readFile.useDelimiter(",|\\n");
            String employeeID, firstName, lastName, salary;

            // While loop the checks if file has information based on the delimiter.
            while (readFile.hasNext()) {
                employeeID = readFile.next();
                firstName = readFile.next();
                lastName = readFile.next();
                salary = readFile.next();

                Employee employee = new Employee(employeeID.trim(), firstName.trim(), lastName.trim(), Double.parseDouble(salary));

                // If the employee is not in the hash map, create an entry, 
                // if they are in the map, increase their attendance count by one.
                if (!(hashMap.containsKey(employee))) {
                    hashMap.put(employee, attendanceCount);
                } else {
                    hashMap.put(employee, hashMap.get(employee) + 1);
                }
            }
            readFile.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return hashMap;
    }

    /**
     * Method in which all employees will be displayed in a readable format.
     *
     * @param hashMap The hash map of all the employees.
     */
    public static void displayAllEmployees(HashMap<Employee, Integer> hashMap) {

        Iterator<Employee> iterator = hashMap.keySet().iterator();

        while (iterator.hasNext()) {
            Employee key = iterator.next();
            System.out.println("Employee ID: " + key.getEmployeeID()
                    + "\nName: " + key.getLastName() + ", " + key.getFirstName()
                    + "\nSalary: $" + key.getSalary() + "\n"
                    + "Attendance: " + hashMap.get(key) + "\n");
        }
        displayMenu(hashMap);
    }

    /**
     * Method to add employee to hash map.
     *
     * @param hashMap The hash map in which to add employee to.
     */
    public static void addEmployee(HashMap<Employee, Integer> hashMap) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter the employee ID, first name, last name, and salary: ");
        String employeeID = input.next();
        String firstName = input.next();
        String lastName = input.next();
        double salary = input.nextDouble();

        Employee employeeToAdd = new Employee(employeeID, firstName, lastName, salary);

        // Check if employee is in the hashmap, if not, add the employee, 
        // otherwise increase attendance count by one.
        if (!(hashMap.containsKey(employeeToAdd))) {
            hashMap.put(employeeToAdd, 1);
        } else {
            hashMap.put(employeeToAdd, hashMap.get(employeeToAdd) + 1);
        }

        // Allow user to enter another employee or return to main menu.
        System.out.print("Do you want to add another employee? Y or N: ");
        String addAnother = input.next();
        if (addAnother.toLowerCase().equals("y")) {
            addEmployee(hashMap);
        } else {
            displayMenu(hashMap);
        }
    }

    /**
     * Method to search hash map for specific employee using their employee ID.
     *
     * @param hashMap The hash map in which the search is performed.
     */
    public static void searchEmployee(HashMap<Employee, Integer> hashMap) {

        Scanner idToSearch = new Scanner(System.in);
        System.out.print("Please enter employee ID to search: ");
        String employeeID = idToSearch.next();
        boolean found = false;

        // Iterate through the keys in the hash map, checking if user input matches a key's employee ID.
        for (Employee key : hashMap.keySet()) {
            if (key.getEmployeeID().equals(employeeID)) {
                System.out.println("Employee ID: " + key.getEmployeeID()
                        + "\nName: " + key.getLastName() + ", " + key.getFirstName()
                        + "\nSalary: $" + key.getSalary() + "\n"
                        + "Attendance: " + hashMap.get(key) + "\n");
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("Employee not found.");
        }

        // Allow user to input another search or return to main menu.
        System.out.print("Do you want to search for another employee? Y or N: ");
        String searchAgain = idToSearch.next();
        if (searchAgain.toLowerCase().equals("y")) {
            searchEmployee(hashMap);
        } else {
            displayMenu(hashMap);
        }
    }
}
