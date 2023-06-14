package com.jdbc.example;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieControl {
 
	private static final String url = "jdbc:mysql://localhost:3306/imdb";
    private static final String user = "root";
    private static final String pwd = "root";

    private static Connection connection;
	
	 public static void main(String args[]) throws ShorterMovieNameException, ClassNotFoundException
	 {
		 try {
			    //load the driver
			   new com.mysql.cj.jdbc.Driver();
			 
			    //connecting to IMDB database
	            connection = DriverManager.getConnection(url, user, pwd);
	            System.out.println("Connected to the database.");

	            insertMovie(); //inserting movie details 

	            // Testing the methods
	            getAllMovies(); // to display all the movies

	            String movieName = "The Dark Knight";
	            getMovie(movieName); // to display movie details based on movieName

	            double productionCost = 100.0;
	            getMovie(productionCost);  //to display details of a movie based on production cost of any movie

	            String releaseDate = "1997-12-19";
	            getMovie1(releaseDate);    //to display details of a movie based on released date



	            String movieToDelete = "Titanic";
                deleteMovie(movieToDelete); // to delete the movie data based on passed movieName as a parameter

	            getAllMovies(); // Verify the deletion after deletion

	            connection.close(); // closing the database connection
	            System.out.println("Disconnected from the database.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	 }
	 
	 //	inserting values
	 public static void insertMovie() throws SQLException, ShorterMovieNameException {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("\nEnter movie name: ");
	        String movieName = scanner.nextLine();
	        
	        //to throw exception when movie name will be less than 3 characters
	        if (movieName.length() < 3) {
	            throw new ShorterMovieNameException("Movie name should be a minimum of 3 characters.");
	        }

	        System.out.print("Enter release date (YYYY-MM-DD): ");
	        String releaseDateStr = scanner.nextLine();
	        java.sql.Date releaseDate = java.sql.Date.valueOf(releaseDateStr);

	        System.out.print("Enter production cost (in crores): ");
	        double productionCost = scanner.nextDouble();
	        
	        //to throw exception when movie production cost will be less than 10cr
	        if (productionCost <= 10.0) {
	            throw new LesserProductionCostException("Production cost should be more than 10 crores.");
	        }

	        System.out.print("Enter number of screens released: ");
	        int noOfScreensReleased = scanner.nextInt();

	        scanner.nextLine(); // Consume the newline character

	        System.out.print("Enter director's name: ");
	        String directedBy = scanner.nextLine();

	        System.out.print("Enter producer's name: ");
	        String producedBy = scanner.nextLine();

	        System.out.print("Enter status (true/false): ");
	        boolean status = scanner.nextBoolean();

	        String insertQuery = "INSERT INTO Movies (movieName, releaseDate, productionCost, " +
	                "noOfScreensReleased, directedBy, producedBy, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	            preparedStatement.setString(1, movieName);
	            preparedStatement.setDate(2, releaseDate);
	            preparedStatement.setDouble(3, productionCost);
	            preparedStatement.setInt(4, noOfScreensReleased);
	            preparedStatement.setString(5, directedBy);
	            preparedStatement.setString(6, producedBy);
	            preparedStatement.setBoolean(7, status);

	            int rowsAffected = preparedStatement.executeUpdate();
	            System.out.println("\nMovie inserted successfully. Rows affected: " + rowsAffected);
	        }
	    }
	 
	 //displaying the list of all the movies
	 public static void getAllMovies() throws SQLException {
	        String selectQuery = "SELECT * FROM Movies";

	        try (Statement statement = connection.createStatement();
	             ResultSet resultSet = statement.executeQuery(selectQuery)) {

	            List<Movie> movies = new ArrayList<>();
	            while (resultSet.next()) {
	                Movie movie = new Movie();
	                movie.setMovieName(resultSet.getString("movieName"));
	                movie.setReleaseDate(resultSet.getString("releaseDate"));
	                movie.setProductionCost(resultSet.getDouble("productionCost"));
	                movie.setNoOfScreensReleased(resultSet.getInt("noOfScreensReleased"));
	                movie.setDirectedBy(resultSet.getString("directedBy"));
	                movie.setProducedBy(resultSet.getString("producedBy"));
	                movie.setStatus(resultSet.getBoolean("status"));
	                movies.add(movie);
	            }

	            System.out.println("\nAll movies:");
	            for (Movie movie : movies) {
	                System.out.println(movie.getMovieName());
	            }
	        }
	    }
	 
	 //display movie details based on passed movieName as a parameter
	 public static void getMovie(String movieName) throws SQLException {
	        String selectQuery = "SELECT * FROM Movies WHERE movieName = ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	            preparedStatement.setString(1, movieName);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    Movie movie = new Movie();
	                    movie.setMovieName(resultSet.getString("movieName"));
	                    movie.setReleaseDate(resultSet.getString("releaseDate"));
	                    movie.setProductionCost(resultSet.getDouble("productionCost"));
	                    movie.setNoOfScreensReleased(resultSet.getInt("noOfScreensReleased"));
	                    movie.setDirectedBy(resultSet.getString("directedBy"));
	                    movie.setProducedBy(resultSet.getString("producedBy"));
	                    movie.setStatus(resultSet.getBoolean("status"));

	                    System.out.println("\nMovie details for " + movieName + ":");
	                    System.out.println("Release Date: " + movie.getReleaseDate());
	                    System.out.println("Production Cost: " + movie.getProductionCost());
	                    System.out.println("Directed By: " + movie.getDirectedBy());
	                    System.out.println("Produced By: " + movie.getProducedBy());
	                } else {
	                    System.out.println("\nMovie not found: " + movieName);
	                }
	            }
	        }
	    }
	 
	 //display movie name based on passed productionCost as a parameter
	 public static void getMovie(double productionCost) throws SQLException {
	        String selectQuery = "SELECT * FROM Movies WHERE productionCost > ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	            preparedStatement.setDouble(1, productionCost);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                List<Movie> movies = new ArrayList<>();
	                while (resultSet.next()) {
	                    Movie movie = new Movie();
	                    movie.setMovieName(resultSet.getString("movieName"));
	                    movie.setReleaseDate(resultSet.getString("releaseDate"));
	                    movie.setProductionCost(resultSet.getDouble("productionCost"));
	                    movie.setNoOfScreensReleased(resultSet.getInt("noOfScreensReleased"));
	                    movie.setDirectedBy(resultSet.getString("directedBy"));
	                    movie.setProducedBy(resultSet.getString("producedBy"));
	                    movie.setStatus(resultSet.getBoolean("status"));
	                    movies.add(movie);
	                }

	                System.out.println("\nMovies with production cost more than " + productionCost + " crores:");
	                for (Movie movie : movies) {
	                    System.out.println(movie.getMovieName());
	                }
	            }
	        }
	    }


	 //display movie details based on passed releaseDate as a parameter
	 public static void getMovie1(String releaseDate) throws SQLException {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    java.sql.Date sqlDate;
		    try {
		        java.util.Date utilDate = dateFormat.parse(releaseDate);
		        sqlDate = new java.sql.Date(utilDate.getTime());
		    } catch (ParseException e) {
		        System.out.println("Invalid release date format. Use YYYY-MM-DD.");
		        return;
		    }

		    String selectQuery = "SELECT * FROM Movies WHERE releaseDate = ?";

		    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
		        preparedStatement.setDate(1, sqlDate);

		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            List<Movie> movies = new ArrayList<>();
		            while (resultSet.next()) {
		                Movie movie = new Movie();
		                movie.setMovieName(resultSet.getString("movieName"));
		                movie.setReleaseDate(resultSet.getDate("releaseDate").toString());
		                movie.setProductionCost(resultSet.getDouble("productionCost"));
		                movie.setNoOfScreensReleased(resultSet.getInt("noOfScreensReleased"));
		                movie.setDirectedBy(resultSet.getString("directedBy"));
		                movie.setProducedBy(resultSet.getString("producedBy"));
		                movie.setStatus(resultSet.getBoolean("status"));
		                movies.add(movie);
		            }

		            if (movies.isEmpty()) {
		                System.out.println("\nNo movies released on " + releaseDate);
		            } else {
		                System.out.println("\nMovies released on " + releaseDate + ":");
		                for (Movie movie : movies) {
		                    System.out.println(movie.getMovieName());
		                }
		            }
		        }
		    }
		}

	 //to delete any movie based on passed movieName
	 public static void deleteMovie(String movieName) throws SQLException {
	        String deleteQuery = "DELETE FROM Movies WHERE movieName = ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
	            preparedStatement.setString(1, movieName);

	            int rowsAffected = preparedStatement.executeUpdate();
	            System.out.println("\nMovie deleted successfully. Rows affected: " + rowsAffected);
	        }
	    }
}

//All movies:
//The Dark Knight
//Inception
//Titanic
//The Shawshank Redemption
