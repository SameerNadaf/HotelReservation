package com.HotelReservation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class HotelRunner {
    public static String url = "jdbc:mysql://localhost:3306/hosteldb";
    public static String user = "root";
    public static String password = "6204";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            while (true) {
                System.out.println();
                System.out.println("Hotel Reservation System");
                Scanner scanner = new Scanner(System.in);
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get a room number");
                System.out.println("4. Update a reservation");
                System.out.println("5. Delete a reservation");
                System.out.println("6. Exit");
                System.out.println("Choose an option: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        Hotel.reserveRoom(conn, scanner);
                        break;
                    case 2:
                        Hotel.viewReservations(conn, scanner);
                        break;
                    case 3:
                        Hotel.getRoomNumber(conn, scanner);
                        break;
                    case 4:
                        Hotel.updateReservation(conn, scanner);
                        break;
                    case 5:
                        Hotel.deleteReservation(conn, scanner);
                        break;
                    case 6:
                        Hotel.exit();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                        break;
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
