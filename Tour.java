import java.util.*;

public class Tour {
    private List<City> cities;
    private int distance;
    private Vehicle vehicle;
    private int remainingCapacity;
    private int lengthOfTour;
    
    public Tour(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.cities = new ArrayList<>();
        this.lengthOfTour = 0;
        this.remainingCapacity = vehicle.getCapacity();
        
    }

    public boolean canAddPackage(Package pkg) {
        return vehicle.getRemainingCapacity() >= pkg.getWeight();
    }

    public void addPackage(Package pkg) {
        cities.add(pkg);
        vehicle.loadPackage(pkg);
        updateTour();
    }

    public void removePackage(Package pkg) {
        cities.remove(pkg);
        vehicle.unloadPackage(pkg);
        updateTour();
    }
    
    
    public Tour(List<City> cities) {
        this.cities = new ArrayList<>(cities);

        // Extract and remove the starting city (0,0) if it exists
        City startingCity = null;
        for(City city: this.cities) {
            if(city.getX() == 0 && city.getY() == 0) {
                startingCity = city;
                break;
            }
        }
        if(startingCity != null) {
            this.cities.remove(startingCity);
        }
        Collections.shuffle(this.cities);
        if(startingCity != null) {
        }
    }

    public City getCity(int index) {
        return cities.get(index);
    }

    public int getTourLength() {
        if (distance != 0) return distance;

        int totalDistance = 0;
        City previousCity = new City(0, 0); // Start with the origin (0,0)

        for (int i = 0; i < noCities(); i++) {
            City currentCity = getCity(i);
            totalDistance += Operations.distance(previousCity, currentCity);
            previousCity = currentCity;
        }

        // Connect the last city back to the origin (0,0)
        totalDistance += Operations.distance(previousCity, new City(0, 0));

        distance = totalDistance;
        return totalDistance;
    }

    
    
    public int getTourLength2() {
    	
    	
        // Calculate the total distance traveled for the current tour
        int distance = 0;
        City prevCity = new City(0, 0);

        for (City city : cities) {
            if (prevCity != null) {
                distance += calculateDistance(prevCity, city);
            }
            prevCity = city;
        }

            distance += calculateDistance(prevCity, new City(0, 0));
       
        return distance;
        

    	}


    public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Tour duplicate() {
        return new Tour(new ArrayList<>(cities));
    }

    public int noCities() {
        return cities.size();
    }
    

    @Override
    public String toString() {
        List<City> tourCities = new ArrayList<>(cities);

        City originCity = new City(0, 0);
        if (!tourCities.contains(originCity)) {
            tourCities.add(0, originCity);
        }

        // Add (0,0) point at the end of the list
        tourCities.add(originCity);
        

        return "Tour [cities=" + tourCities + ", distance=" + distance + "]";
    

}
    public boolean containsCity(City city) {
        return cities.contains(city);
    }

	public Tour[] getRoutes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	  public Vehicle getVehicle() {
	        return vehicle;
	    }

	    public void switchCities() {
	        // Swap the cities in the tour
	        if (cities.size() < 2) {
	            return;
	        }

	        int index1 = new Random().nextInt(cities.size());
	        int index2 = new Random().nextInt(cities.size());

	        while (index1 == index2) {
	            index2 = new Random().nextInt(cities.size());
	        }

	        City city1 = cities.get(index1);
	        City city2 = cities.get(index2);

	        cities.set(index1, city2);
	        cities.set(index2, city1);
	    }
	    
	    public void movePackage() {
	        // Move a randomly selected package to a different vehicle
	        if (cities.isEmpty()) {
	        	System.out.println("\nEMPTY\n");
	            return; 
	        }

	        int packageIndex = new Random().nextInt(cities.size());
	        Package pkgToMove = (Package) cities.get(packageIndex);

	        // Find a different vehicle to move the package to
	        List<Vehicle> availableVehicles = new ArrayList<>(vehicle.getFleet());
	        availableVehicles.remove(vehicle);

	        if (!availableVehicles.isEmpty()) {
	            Vehicle newVehicle = availableVehicles.get(new Random().nextInt(availableVehicles.size()));

	            if (newVehicle.canLoadPackage(pkgToMove)) {
	                // Remove the package from the source tour
	                removePackage(pkgToMove);

	                // Add the package to the destination tour
	                newVehicle.loadPackage(pkgToMove);
	            }

	        }
	        
         
	    }

	    private int calculateDistance(City city1, City city2) {
	    	return (int) Operations.distance(city1, city2);
	    }
	    
	    public void updateTour() {
	    	lengthOfTour = getTourLength2();
	     //   remainingCapacity = vehicle.getCapacity();

	        for (City city : cities) {
	            if (city instanceof Package) {
	                Package pkg = (Package) city;
	                remainingCapacity -= pkg.getWeight();
	            }
	        }
	   //     if (vehicle.getRemainingCapacity() != remainingCapacity) {
	       //     vehicle.setRemainingCapacity(remainingCapacity);
	   //     }
	    }

	    public Tour duplicate2() {
	        // duplicate tour, same cities and vehicle
	        Tour duplicatedTour = new Tour(vehicle);
	        duplicatedTour.cities.addAll(cities);
	        return duplicatedTour;
	    }
	  /////THIS IS FINALLLLLLLLLLLLLL
	   // @Override
	    public String toString22() {
	        StringBuilder sb = new StringBuilder("Tour:\n");
	        sb.append("Cities: ");
	        for (City city : cities) {
	            sb.append(city).append("    ");
	        }
	        sb.append("\nTotal Distance: ").append(getTourLength2());
	        return sb.toString();
	    }
	    
	   
}