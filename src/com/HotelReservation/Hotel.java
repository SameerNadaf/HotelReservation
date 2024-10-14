package com.HotelReservation;

import java.sql.*;
import java.util.Scanner;

public class Hotel {

    public static void reserveRoom(Connection conn, Scanner scanner) {
        System.out.println("Enter guest name : ");
        String name = scanner.next();
        scanner.nextLine();
        System.out.println("Enter room number : ");
        int roomNum = scanner.nextInt();
        System.out.println("Enter contact number : ");
        String num = scanner.next();

        String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?,?,?)";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, roomNum);
            ps.setString(3, num);
            ps.executeUpdate();
            System.out.println("Reservation has been successfully added");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void viewReservations(Connection conn, Scanner scanner) {
        String sql = "SELECT * FROM reservations";
        try (Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Reservation ID : " + rs.getInt(1));
                System.out.println("Guest Name : " + rs.getString(2));
                System.out.println("Room Number : " + rs.getInt(3));
                System.out.println("Contact Number : " + rs.getString(4));
                System.out.println("Reservation Date : " + rs.getTimestamp(5).toString());
                System.out.println("-------------------------------------------------------------------------");
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getRoomNumber(Connection conn, Scanner scanner) {
        System.out.println("Enter reservation ID : ");
        int reservationID = scanner.nextInt();
        System.out.println("Enter guest name : ");
        String guestName = scanner.next();
        String sql = "SELECT room_number FROM reservations WHERE res_id=? AND guest_name=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reservationID);
            ps.setString(2, guestName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int roomNumber = rs.getInt(1);
                System.out.println("Room Number for reservation ID " + reservationID + " And guest name " + guestName + " is " + roomNumber);
            }
            else {
                System.out.println("Room Number for reservation ID " + reservationID + " is not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateReservation(Connection conn, Scanner scanner) {
        System.out.println("Enter reservation ID to update : ");
        int reservationID = scanner.nextInt();

        if (!reservationExists(conn, reservationID)){
            System.out.println("Reservation not found for the given ID.");
            return;
        }

        System.out.println("Enter guest name to update : ");
        String guestName = scanner.next();
        scanner.nextLine();
        System.out.println("Enter room number to update : ");
        int roomNumber = scanner.nextInt();
        System.out.println("Enter contact number to update : ");
        String contactNumber = scanner.next();

        String sql = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE res_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, guestName);
            ps.setInt(2, roomNumber);
            ps.setString(3, contactNumber);
            ps.setInt(4, reservationID);
            ps.executeUpdate();
            System.out.println("Reservation has been successfully updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection conn, int reservationID) {
        boolean exists = false;
        try {
            String sql = "SELECT * FROM reservations WHERE res_id="+reservationID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public static void deleteReservation(Connection conn, Scanner scanner) {
        System.out.println("Enter reservation ID : ");
        int reservationID = scanner.nextInt();
        if (!reservationExists(conn, reservationID)){
            System.out.println("Reservation not found for the given ID.");
            return;
        }
        String sql = "DELETE FROM reservations WHERE res_id="+reservationID;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Reservation has been successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void exit() throws InterruptedException {
        System.out.print("Exiting Hotel Reservation");
        int i = 5;
        while (i!=0) {
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel Reservation System!!!");
    }
}
