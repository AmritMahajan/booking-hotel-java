import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class RoomNotAvailable extends Exception
{
	public RoomNotAvailable(int rn) {
		System.out.println("Room no. " + rn+ " is not available");
	}
}
class Hotel{
	static Scanner sc= new Scanner(System.in);
	public static void description(int room_type) {
		switch (room_type) {
	        case 1:System.out.println("Number of single beds : 1\nAC : No\nFree breakfast : Yes\nCharge per night:1000");
	            break;
	        case 2:System.out.println("Number of double beds : 1\nAC : No\nFree breakfast : Yes\nCharge per night:2000");
	            break;
	        case 3:System.out.println("Number of single beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per night:3000");
	            break;
	        case 4:System.out.println("Number of double beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per night:4000");
	            break;
	        case 5:System.out.println("Number of single beds : 2\nAC : Yes\nFree breakfast : Yes\nCharge per night:5000");
            	break;
	        case 6:System.out.println("Number of double beds : 2\nAC : Yes\nFree breakfast : Yes\nCharge per night:7000");
            	break;
	        default:
	            System.out.println("Enter valid option");
	            break;
		}
	}

	public static void availability(int room_type) {
		try {
	         Class.forName("org.postgresql.Driver");
	         Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
	         Statement stmt = c.createStatement();
	         int count =0;
	         if(room_type>0 && room_type<7) {
		         int low = (room_type-1)*5+1;
		         int high=room_type*5;
		         String sql =( "SELECT * FROM  public.\"Guests\" where (room_no>=? AND room_no<=?) ;" );
		         PreparedStatement pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, low);
		         pstmt.setInt(2, high);
		         
		         ResultSet rs = pstmt.executeQuery();
		         
		         while ( rs.next() ) {
		             count++;
		          }
		         count =5 -count;
	         }
	         else {
	        	 System.out.println("Enter valid option");
	         }
	         System.out.println("Number of rooms available : "+count);	      
	         stmt.close();
	         c.close();
	      }catch (Exception e) {
	         e.printStackTrace();
	      }
	}

	public static void bookroom(int room_type) {
		int low = (room_type-1)*5+1;
        int high=room_type*5;
        if(room_type<1 || room_type>6) {
        	return;
        }
        System.out.println("Available room numbers are : ");
        int rn;
		try {
			Class.forName("org.postgresql.Driver");
			Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres",
					"Amrit1234");
			Statement stmt = c.createStatement();

			String sql = ("SELECT room_no FROM  public.\"RoomDetails\" where (room_no>=? AND room_no<=? AND booked =0) ;");
			PreparedStatement pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, low);
			pstmt.setInt(2, high);

			ResultSet rs = pstmt.executeQuery();
			ArrayList<Integer> available=new ArrayList<Integer>();
			while (rs.next()) {
				int b = rs.getInt("room_no");
	        	 available.add(b);
			}
			System.out.println(available);

			
			System.out.print("\nEnter room number: ");
			rn=sc.nextInt();
			if(!available.contains(rn)) {
            	throw new RoomNotAvailable(rn);
            }
			else {
				GuestDetails(rn);
				sql = ("UPDATE public.\"RoomDetails\" set booked = 1 where room_no = ?  ;");
				PreparedStatement pst = c.prepareStatement(sql);
				pst.setInt(1, rn);
				pst.execute();	
				
				stmt.close();
				c.close();
				
				System.out.println("Your room is booked. Enjoy your stay");
			}
			
			
		}catch(Exception e) {
			System.out.println(e);
			System.out.println("Enter Valid Input");
			return;
		}
		
		
        
	}

	static void GuestDetails(int rn) {
		System.out.print("\nEnter guest name: ");
		String name =sc.next();
		System.out.print("Enter gender: ");
        String gender = sc.next();
        System.out.print("Enter age: ");
        int age = sc.nextInt();
        System.out.print("Enter contact number: ");
        int contact=sc.nextInt();
        Connection c = null;
		Statement stmt = null;
		  try {
		         Class.forName("org.postgresql.Driver");
		         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
		         stmt = c.createStatement();		      
		         String sql =( "INSERT INTO  public.\"Guests\" (room_no,name,age,gender,contact) VALUES(?,?,?,?,?);" );
		         PreparedStatement pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, rn);
		         pstmt.setString(2, name);
		         pstmt.setInt(3, age);
		         pstmt.setString(4, gender);
		         pstmt.setInt(5, contact);
		         
		         pstmt.execute();
		             		      		         		    
		         
		         stmt.close();
		         c.close();
		      }catch (Exception e) {
		         e.printStackTrace();
		      }
        
		
	}

	public static void orderFood(int n) {
		int orderno,quant,booked = 0;
		char ctrl;
		Connection c = null;
		try{
			
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
	         String sql =( "SELECT booked from  public.\"RoomDetails\" WHERE room_no=?;" );
	         PreparedStatement pstmt = c.prepareStatement(sql);
	         pstmt.setInt(1, n);
	         ResultSet rs = pstmt.executeQuery();
	         
	         while ( rs.next() ) {
	             booked=rs.getInt("booked");
	          }
	         if(booked==0) {
	        	 
	        	 throw new NullPointerException();
	         }
			
       do
       {
    	   System.out.println("\n   Menu: \n\n1.Pizza\t\tRs.150\n2.Pasta\t\tRs.70\n3.Burger\tRs.50\n4.Coca cola\tRs.20\n");
           orderno = sc.nextInt();
           System.out.print("Quantity- ");
           quant=sc.nextInt();
           addFood(n,orderno,quant);
             System.out.println("Add Order? (y/n)");
             ctrl=sc.next().charAt(0); 
       }while(ctrl=='y'||ctrl=='Y');  
       }
        catch(NullPointerException e)
           {
               System.out.println("\nRoom not booked");
           }
        catch(Exception e)
        {
        	System.out.print(e);
            System.out.println("Cannot be done");
        }
		
	}

	private static void addFood(int n, int orderno, int quant) {
		int price;
		String item;
		switch(orderno) {
		case 1: item="Pizza";price =150;break;
		case 2: item="Pasta";price =70;break;
		case 3: item="Burger";price =50;break;
		case 4: item="Coca Cola";price =20;break;
		default: System.out.println("Enter valid option");return;
		}
		price=price*quant;
		
		Connection c = null;
		Statement stmt = null;
		  try {
		         Class.forName("org.postgresql.Driver");
		         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
		         stmt = c.createStatement();
		         String sql =( "INSERT INTO  public.\"Food\" (room_no,item,quantity,price) VALUES(?,?,?,?);" );
		         PreparedStatement pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, n);
		         pstmt.setString(2, item);
		         pstmt.setInt(3, quant);
		         pstmt.setInt(4, price);
		         pstmt.execute();

		         stmt.close();
		         c.close();
		      }catch (Exception e) {
		         e.printStackTrace();
		      }
		
	}

	public static void checkout(int n) {
		int booked=0;
		char ctrl;
		Connection c = null;
		try{
			
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
	         String sql =( "SELECT booked from  public.\"RoomDetails\" WHERE room_no=?;" );
	         PreparedStatement pstmt = c.prepareStatement(sql);
	         pstmt.setInt(1, n);
	         ResultSet rs = pstmt.executeQuery();
	         
	         while ( rs.next() ) {
	             booked=rs.getInt("booked");
	          }
	         if(booked==0) {
	        	 
	        	 throw new NullPointerException();
	         }
		}
        catch(NullPointerException e)
           {
               System.out.println("\nRoom already empty");
               return;
           }
		catch(Exception e)
        {
        	System.out.print(e);
            System.out.println("Cannot be done");
        }
		System.out.println(" Do you want to checkout ? (y/n)");
		ctrl=sc.next().charAt(0);
        if(ctrl=='y'||ctrl=='Y')
        {
            bill(n);
            
            System.out.println("Checkout successful \nDo visit us again");
        }
	         
		
	}

	private static void bill(int n) {
		int total_amount=0;
		Connection c = null;
		Statement stmt = null;
		  try {
		         Class.forName("org.postgresql.Driver");
		         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
		         stmt = c.createStatement();
		         String sql =( "UPDATE public.\"RoomDetails\" set booked = 0 where room_no = ?  ;" );
		         PreparedStatement pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, n);
		         pstmt.execute();
		         
		         sql =( "SELECT SUM(price) from public.\"Food\" where room_no = ?  ;" );
		         pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, n);
		         ResultSet rs = pstmt.executeQuery();
		         while ( rs.next() ) {
		             total_amount=rs.getInt(1);
		          }
		         System.out.println("Please Pay Rs."+total_amount+" at the reception");
		         
		         sql = "DELETE from public.\"Food\" where room_no = ?;";
		         pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, n);
		         pstmt.execute();
		         
		         sql = "DELETE from public.\"Guests\" where room_no = ?;";
		         pstmt = c.prepareStatement(sql);
		         pstmt.setInt(1, n);
		         pstmt.execute();
		         
		         stmt.close();
		         c.close();
		      }catch (Exception e) {
		         e.printStackTrace();
		      }
	}	
}

public class Main{
	public static void main(String[] args) {
		Connection c = null;
		Statement stmt = null;
		
		Scanner sc = new Scanner(System.in);
		int e,room_type,room_no,n;
        char ctrl;
        x:
        do{

        System.out.println("\nSelect your choice :\n1.View room description\n2.View room availability \n3.Book a Room\n4.Order food\n5.Checkout\n6.Exit\n");
        e = sc.nextInt();
        switch(e){
            case 1: System.out.println("\nSelect room type :\n1.Single Room \n2.Double Room \n3.Deluxe Single Room \n4.Deluxe Double Room \n5.Twin Room \n6.Suite Room \n");
                    room_type = sc.nextInt();
                    Hotel.description(room_type);
                break;
            case 2:System.out.println("\nSelect room type :\n1.Single Room \n2.Double Room \n3.Deluxe Single Room \n4.Deluxe Double Room \n5.Twin Room \n6.Suite Room \n");
            		room_type = sc.nextInt();
                     Hotel.availability(room_type);
                break;
            case 3:System.out.println("\nSelect room type :\n1.Single Room \n2.Double Room \n3.Deluxe Single Room \n4.Deluxe Double Room \n5.Twin Room \n6.Suite Room \n");
            		room_type = sc.nextInt();
                     Hotel.bookroom(room_type);                     
                break;
            case 4:
                 System.out.print("Enter Room Number -");
                     n = sc.nextInt();
                     if(n>30 || n<0)
                         System.out.println("Room doesn't exist");                  
                     else
                    	 Hotel.orderFood(n);
                     break;
            case 5:                 
                System.out.print("Enter Room Number -");
                     n = sc.nextInt();
                     if(n>30 || n<0)
                         System.out.println("Room doesn't exist");                  
                     else
                    	 Hotel.checkout(n);
                     break;
            case 6:break x;
                
        }
           
            System.out.println("\nContinue : (y/n)");
            ctrl=sc.next().charAt(0); 
            if(!(ctrl=='y'||ctrl=='Y'||ctrl=='n'||ctrl=='N'))
            {
                System.out.println("Invalid Option");
                System.out.println("\nContinue : (y/n)");
                ctrl=sc.next().charAt(0); 
            }
            
        }while(ctrl=='y'||ctrl=='Y');   
		/*
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres", "Amrit1234");
	         stmt = c.createStatement();
	         String sql = "";
	         stmt.executeUpdate(sql);
	         
	         
	         
	         
	         
	         
	         stmt.close();
	         c.close();
	      }catch (Exception e) {
	         e.printStackTrace();
	      }*/
	      
	}
}
