# Hotel Reservation System

This is a simple hotel reservation system implemented in Java using JDBC (Java Database Connectivity). The system allows users to perform various operations related to hotel room reservations.

## Functionality

The system provides the following functionality:

1. **Reserve a Room**: Allows users to reserve a room by providing necessary details such as guest name, check-in date, check-out date, etc.

2. **View Reservations**: Displays all existing reservations along with their details such as guest name, room number, check-in date, check-out date, etc.

3. **Get Room Number**: Retrieves the room number for a given guest name or reservation ID.

4. **Update Reservations**: Allows users to update existing reservations, such as modifying check-in/out dates or guest information.

5. **Delete Reservations**: Allows users to cancel or delete existing reservations.

6. **Exit**: Terminates the program.

## Technologies Used

- Java
- JDBC (Java Database Connectivity)

## How to Run

1. Clone the repository to your local machine.
2. Make sure you have Java and a compatible JDBC driver installed.
3. Configure the database connection details in the source code.
4. Compile the Java files.
5. Run the main program file.

## Database Schema

The system interacts with a relational database with the following schema:

```
CREATE TABLE Reservations (
    reservation_id INT PRIMARY KEY AUTO_INCREMENT,
    guest_name VARCHAR(255),
    room_number INT,
    contact_number varchar(10),
    reservation_date CURRENT_TIMESTAMP
    -- Add other necessary columns
);
```

Feel free to customize the content as needed to fit your project and add any additional sections or information.
