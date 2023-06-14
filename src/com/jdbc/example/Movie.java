package com.jdbc.example;

public class Movie {
	
	    private String movieName;
	    private String releaseDate;
	    private double productionCost;
	    private int noOfScreensReleased;
	    private String directedBy;
	    private String producedBy;
	    private boolean status;

	    // Constructor 1: Initializing Movie object with movieName, releaseDate, status
	    public Movie(String movieName, String releaseDate, boolean status) {
	        this.movieName = movieName;
	        this.releaseDate = releaseDate;
	        this.status = status;
	    }

	    // Constructor 2: Initializing Movie object with movieName, directedBy, productionCost
	    public Movie(String movieName, String directedBy, double productionCost) {
	        this.movieName = movieName;
	        this.directedBy = directedBy;
	        this.productionCost = productionCost;
	    }

	    // Default constructor: Initializes Movie object with assumed values
	    public Movie() {
	        this.movieName = "Untitled";
	        this.releaseDate = "Unknown";
	        this.productionCost = 0.0;
	        this.noOfScreensReleased = 0;
	        this.directedBy = "Unknown";
	        this.producedBy = "Unknown";
	        this.status = false;
	    }

	    // Getter and Setter methods for each variable
	    public String getMovieName() {
	        return movieName;
	    }

	    public void setMovieName(String movieName) {
	        this.movieName = movieName;
	    }

	    public String getReleaseDate() {
	        return releaseDate;
	    }

	    public void setReleaseDate(String releaseDate) {
	        this.releaseDate = releaseDate;
	    }

	    public double getProductionCost() {
	        return productionCost;
	    }

	    public void setProductionCost(double productionCost) {
	        this.productionCost = productionCost;
	    }

	    public int getNoOfScreensReleased() {
	        return noOfScreensReleased;
	    }

	    public void setNoOfScreensReleased(int noOfScreensReleased) {
	        this.noOfScreensReleased = noOfScreensReleased;
	    }

	    public String getDirectedBy() {
	        return directedBy;
	    }

	    public void setDirectedBy(String directedBy) {
	        this.directedBy = directedBy;
	    }

	    public String getProducedBy() {
	        return producedBy;
	    }

	    public void setProducedBy(String producedBy) {
	        this.producedBy = producedBy;
	    }

	    public boolean getStatus() {
	        return status;
	    }

	    public void setStatus(boolean status) {
	        this.status = status;
	    }
	
	
}
