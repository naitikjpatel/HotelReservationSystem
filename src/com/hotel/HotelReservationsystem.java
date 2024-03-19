package com.hotel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;



public class HotelReservationsystem {

	private static final String url="jdbc:mysql://localhost:3306/hotel_db";
	private static final String username="root";
	private static final String password="admin";
	private static final String Driver_Class="com.mysql.cj.jdbc.Driver";
	public static void main(String[] args) {
		
		try {
		Class.forName(Driver_Class);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		try {
			Connection connection=DriverManager.getConnection(url,username,password);
			if(connection!=null) {
			 while(true){
	                System.out.println();
	                System.out.println("HOTEL MANAGEMENT SYSTEM");
	                Scanner scanner = new Scanner(System.in);
	                System.out.println("1. Reserve a room");
	                System.out.println("2. View Reservations");
	                System.out.println("3. Get Room Number");
	                System.out.println("4. Update Reservations");
	                System.out.println("5. Delete Reservations");
	                System.out.println("0. Exit");
	                System.out.print("Choose an option: ");
	                int choice = scanner.nextInt();
	                switch (choice) {
	                    case 1:
	                        reserveRoom(connection, scanner);
	                        break;
	                    case 2:
	                        viewReservations(connection);
	                        break;
	                    case 3:
	                        getRoomNumber(connection, scanner);
	                        break;
	                    case 4:
	                        updateReservation(connection, scanner);
	                        break;
	                    case 5:
	                        deleteReservation(connection, scanner);
	                        break;
	                    case 0:
	                        exit();
	                        scanner.close();
	                        return;
	                    default:
	                        System.out.println("Invalid choice. Try again.");
	                }
	            }
			}else {
				System.out.println("Connection is Failed");
				return;
			}

		} catch (SQLException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public static void reserveRoom(Connection con,Scanner scanner) {
		
		try {
			
			System.out.println("Enter guest name: ");
			String name=scanner.next();
			name=name.toLowerCase();
			scanner.nextLine();
			System.out.println("Enter room number:");
			int roomNumber=scanner.nextInt();
			System.out.println("Enter contact number: ");
			String contactNumber=scanner.next();
			int rowsAffected=0;
			
			String sqlQuery="insert into reservations (GUEST_NAME,ROOM_NUMBER,CONTACT_NUMBER) values (?,?,?)";
			PreparedStatement pstmt=null;
			try {
			pstmt=con.prepareStatement(sqlQuery);
			pstmt.setString(1,name);
			pstmt.setInt(2,roomNumber);
			pstmt.setString(3,contactNumber);
			
			rowsAffected=pstmt.executeUpdate();
			
			if(rowsAffected>0) {
				System.out.println("Reservation successful");
			}
			else {
				System.out.println("Reservation Failed");
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void viewReservations(Connection con) {
		
		String selectQuery="select * from reservations";
		
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		try {
			pstmt=con.prepareStatement(selectQuery);
			rs=pstmt.executeQuery();
			
			System.out.println("Current Reservations:");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
            System.out.println("| Reservation ID | Guest           | Room Number   | Contact Number       | Reservation Date        |");
            System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");

			while(rs.next()) {
				
				int reservationId=rs.getInt(1);
				String guestName=rs.getString(2);
				int roomNumber=rs.getInt(3);
				String contactNumber=rs.getString(4);
				String reservationDate=rs.getTimestamp(5).toString();
				
				 System.out.printf("| %-14d | %-15s | %-13d | %-20s | %-19s   |\n",reservationId,guestName,roomNumber,contactNumber,reservationDate);
			}
			
			System.out.println("+----------------+-----------------+---------------+----------------------+-------------------------+");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	public static void getRoomNumber(Connection con,Scanner scanner) {
	
		System.out.println("Enter Reservation Id: ");
		int reservationId=scanner.nextInt();
		System.out.println("Enter guest name :");
		String name=scanner.next();
		name=name.toLowerCase();
		
		String searchQuery="select ROOM_NUMBER from reservations where RESERVATION_ID=? and GUEST_NAME=?";
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		
		try {
		pstmt=con.prepareStatement(searchQuery);
		pstmt.setInt(1, reservationId);
		pstmt.setString(2, name);
		rs=pstmt.executeQuery();
		
		if(rs.next()) {
			int roomNumber=rs.getInt(1);
			System.out.println("Room Number for Reservation ID "+reservationId+" and guest "+name +" is: "+roomNumber);
		}
		else {
			System.out.println("Reservation Not Found for the Given Id and Guest Name");
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	public static void updateReservation(Connection con,Scanner scanner) {
	
		System.out.println("Enter Reservation Id to Update :");
		int reservationId=scanner.nextInt();
		scanner.nextLine();
		if(!reservationExists(con,reservationId)) {
			System.out.println("Reservation not found for the given Id.");
			return;
		}
		
		System.out.println("Enter new Guest Name  :");
		String name=scanner.next();
		System.out.println("Enter new room number :");
		int roomNumber=scanner.nextInt();
		System.out.println("Enter new contact number :");
		String contactNumber=scanner.next();
		
		String updateQuery="update reservations set GUEST_NAME=? ,ROOM_NUMBER=?,CONTACT_NUMBER=? where RESERVATION_ID=?";
		PreparedStatement pstmt=null;
		int rowsAffected=0;
		
		try {
		pstmt=con.prepareStatement(updateQuery);
		pstmt.setString(1, name);
		pstmt.setInt(2, roomNumber);
		pstmt.setString(3, contactNumber);
		pstmt.setInt(4, reservationId);
		rowsAffected= pstmt.executeUpdate();
		
		if(rowsAffected>0) {
			System.out.println("Reservation Update Successfully");
			
		}
		else {
			System.out.println("Reservation Update Failed");
		}
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void deleteReservation(Connection con,Scanner scanner) {
	
		System.out.println("Enter Reservation ID to delete.");
		int reservationId=scanner.nextInt();
		
		if(!reservationExists(con, reservationId)) {
			System.out.println("Reservation Not found for the given Id");
			return;
		}
		
		String deleteQuery="delete from reservations where RESERVATION_ID=?";
		PreparedStatement pstmt=null;
		int rowsAffected=0;
		
		try {
			pstmt=con.prepareStatement(deleteQuery);
			pstmt.setInt(1, reservationId);
			rowsAffected= pstmt.executeUpdate();
			
			if(rowsAffected>0) {
				System.out.println("Reservation Delete Successfully");
			}
			else {
				System.out.println("Reservation deletion Failed");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public static  boolean reservationExists(Connection con,int reservationId) {
		String query="select * from reservations where RESERVATION_ID="+reservationId;
		Statement st=null;
		ResultSet rs=null;
		
		try {
			st=con.createStatement();
			rs=st.executeQuery(query);
			
			return rs.next();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	public static void exit() throws InterruptedException {
		
		System.out.println("Exiting System");
		int i=5;
		while(i!=0) {
			System.out.print(".");
			Thread.sleep(1000);
			i--;
		}
		System.out.println();
		System.out.println("Thank You For Using Hotel Reservation System!!");
	}
}
