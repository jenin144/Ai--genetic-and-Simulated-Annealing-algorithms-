import java.util.*;

public class Vehicle {
    private static int idCounter = 0;

    private int id;
    private int capacity;
    private Tour route;
    private int usedCapacity;
    private List<Package> loadedPackages;
    private Tour currentTour;

    
    
    public Vehicle(int capacity) {
        this.id = ++idCounter;
        this.capacity = capacity;
       // this.route = new Tour(new ArrayList<>());
        this.usedCapacity = 0;
        this.loadedPackages = new ArrayList<>();
        this.currentTour = new Tour(new ArrayList<>());
    }

    public int getCapacity() {
		return capacity;
	}

	public int getUsedCapacity() {
		return usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public int getId() {
        return id;
    }

    public boolean canAddPackage(Package p) {
        return usedCapacity + p.getWeight() <= capacity;
    }
    
    public void addPackage(Package p) {
        if (canAddPackage(p)) {
            route.getCities().add(p);
            usedCapacity += p.getWeight();
            route.setDistance(0); // Reset the distance
        }
    }
    
    

    public void loadPackage(Package pkg) {
        if (canLoadPackage(pkg)) {
            loadedPackages.add(pkg);
        //   currentTour.addPackage(pkg);
         // Update the current tour
            currentTour.updateTour(); 
        } else {
        	throw new IllegalArgumentException("CAREFUL! you have exceeded vehicle capacity, the Remaining capacity is: " + getRemainingCapacity());
        }
    }
    
    public void unloadPackage(Package pkg) {
        loadedPackages.remove(pkg);
        currentTour.removePackage(pkg);
    }
    
    public List<Vehicle> getFleet() {
    	  
        List<Vehicle> fleet = new ArrayList<>();
        fleet.add(this);
        return fleet;
    }

    public List<Package> getLoadedPackages() {
        return loadedPackages;
    }

    public boolean canLoadPackage(Package pkg) {
        return getRemainingCapacity() >= pkg.getWeight();
    }

    public int getRemainingCapacity() {
       
        int totalWeightLoaded = 0;
        for (Package loadedPackage : loadedPackages) {
        	totalWeightLoaded += loadedPackage.getWeight();
        }
        int remainingCapacity = capacity - totalWeightLoaded;
        return remainingCapacity;
    }

    
    public void setRemainingCapacity(int remainingCapacity) {
        this.capacity = remainingCapacity;
    }
    
    
    public Tour getCurrentTour() {
        return currentTour;
    }
 
    

    public Tour getRoute() {
        return route;
    }

    public void setRoute(Tour route) {
        this.route = route;
    }	
    
    /////////Added this
    @Override
    public String toString() {
        return "Vehicle with capacity: " + capacity;
    }
}