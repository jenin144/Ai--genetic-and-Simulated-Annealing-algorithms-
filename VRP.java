import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class VRP {

	
	   public static void main(String[] args) {
	        List<Package> packages;
	        List<Vehicle> vehicles = new ArrayList<>();
	      //  List<Vehicle> vehicles2 = new ArrayList<>();

	       
	        
	        Scanner scanner = new Scanner(System.in);
	        
	        // Input for the number of vehicles
	        System.out.print("Enter the number of vehicles: ");
	        int numVehicles = scanner.nextInt();
	        
        	

            List<Integer> vehicleCapacities = new ArrayList<>();
        	
          
            ////////////
            // Let user enter Vehicle Capacity
            for (int i = 1; i <= numVehicles; i++) {
                int vehicleCapacity = 0;
                
                while (vehicleCapacity <= 0) {
                	
                    System.out.print("Please Enter the capacity of Vehicle " + i + " (in kilograms): ");
                    if (scanner.hasNextInt()) {
                        vehicleCapacity = scanner.nextInt();
                        if (vehicleCapacity <= 0) {
                            System.out.println("Please enter a positive vehicle capacity.");
                        } else {
                        	//setting the Capacity for Vehicle
                            vehicleCapacities.add(vehicleCapacity);
                            vehicles.add(new Vehicle(vehicleCapacity));

                        }
                    } else {
                    	//if there is an invalid input, remove it
                        System.out.println("Sorry, vehicle capacity is invalid input.");
                        scanner.next(); 
                    }
                    
                }
            }

           // scanner.close();

	     
	       
	        // Display the menu
	        int choice;
	        do {
	            System.out.println("Choose an algorithm:");
	            System.out.println("1. Simulated Annealing");
	            System.out.println("2.  Genetic Algorithm");
	            System.out.println("0. Exit");
	            System.out.print("Enter your choice: ");
	            choice = scanner.nextInt();

	            switch (choice) {

	            
	            case 1:


	                    // FILE INPUT 
	                    String filePath ="C:\\Users\\HP ProBook A9\\eclipse-workspace\\AIfinalProject\\src\\pack.txt";
	                    List<City> cities = new ArrayList<>();
	                    List<Vehicle> vehicles2 = new ArrayList<>();

	                    try {
	                        BufferedReader reader = new BufferedReader(new FileReader(filePath));
	                        String line;
	                        // Reading line by line and dividing the information
	                        while ((line = reader.readLine()) != null) {
	                            String[] parts = line.split(" ");
	                            if (parts.length == 3) {
	                                int x = Integer.parseInt(parts[0]);
	                                int y = Integer.parseInt(parts[1]);
	                                int weight = Integer.parseInt(parts[2]);
	                                cities.add(new Package(x, y, weight));
	                            }
	                        }

	                        reader.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }

	                    // Shuffle to increase randomness
	                    Collections.shuffle(cities, new Random());

	                    // add vehicles to list
	                    for (int i = 0; i < numVehicles; i++) {
	                        vehicles2.add(new Vehicle(vehicleCapacities.get(i)));
	                    }

	                    // Initial tours are initialized 
	                    List<Tour> initialTours = new ArrayList<>();
	                    int cityIndex = 0;
	                    //calculating remaining capacity for vehicles
	                    for (Vehicle vehicle : vehicles2) {
	                        Tour initialTour = new Tour(vehicle);
	                        int remainingCapacity = vehicle.getCapacity();
	                        
	                        // Assign cities to the vehicle until capacity is reached
	                        while (cityIndex < cities.size()) {
	                            City city = cities.get(cityIndex);
	                            if (city instanceof Package) {
	                                Package pkg = (Package) city;
	                                if (remainingCapacity >= pkg.getWeight()) {
	                                    initialTour.addPackage(pkg);
	                                    remainingCapacity -= pkg.getWeight();
	                                    cityIndex++;
	                                } else {
	                                    break; // if capacity is exceeded then move to the next vehicle 
	                                }
	                            }
	                        }
	                        
	                        initialTours.add(initialTour);
	                    }

	                    // Print the initial tours
	                    System.out.println("\nInitial Tours:");
	                    for (int i = 0; i < numVehicles; i++) {
	                        System.out.println("Vehicle " + (i + 1) + " " + initialTours.get(i).toString22());
	                    }
	                    System.out.println("\n\n ");
	                    // Perform Simulated Annealing for each vehicle
	                    List<Tour> optimizedTours = simulatedAnnealing(cities, vehicles2, initialTours);

	                    // Print the optimized tours
	                    System.out.println("\nOptimized Tours:");
	                    for (int i = 0; i < numVehicles; i++) {
	                        System.out.println("Vehicle " + (i + 1) + " " + optimizedTours.get(i).toString22());
	                    }
	                    System.out.println("\n\n");
	                
                    break;

	            
	                case 2:


	        	         packages = readPackagesFromFile("pack.txt");

	        	        // Initialize the genetic algorithm parameters
	        	        int populationSize = 5;
	        	        int generations = 20;
	        	        double mutationRate = 0.80;

	        	        // Create an initial population of routes for all vehicles
	        	        List<List<Tour>> population = initializePopulation(packages, vehicles,populationSize);
	        	        // Run the genetic algorithm
	        	         
	        	        List<Tour> bestRoutes = geneticAlgorithm(population, packages, mutationRate, vehicles);

	        	         System.out.println("*********************************************************");

	        	        
	        	        
	        	     // Print the best routes found for each vehicle
	        	     for (int i = 0; i < numVehicles; i++) {
	        	         System.out.println("Best Route Found for Vehicle " + (i + 1) + ":");
	        	         Tour routeForVehicle = bestRoutes.get(i);
	        	         System.out.println("Route Length: " + calculateTotalDistance(routeForVehicle));
	        	         System.out.println("Route: " + routeForVehicle);
	        	     }
	                    
	                    break;
	                case 0:
	                    System.out.println("Exiting...");
	                    break;
	                default:
	                    System.out.println("Invalid choice. Please enter a valid option.");
	            }
	        } while (choice != 0);

	        scanner.close();
	    
	}

	   
	    
	 
	////////////////////////////
	    public static List<Package> readPackagesFromFile(String filename) {
	        List<Package> packages = new ArrayList<>();

	        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\HP ProBook A9\\eclipse-workspace\\AIfinalProject\\src\\pack.txt"))) {
	            String line;
	            while ((line = br.readLine()) != null) {
	                String[] parts = line.split(" ");
	                if (parts.length == 3) {
	                    int x = Integer.parseInt(parts[0]);
	                    int y = Integer.parseInt(parts[1]);
	                    int weight = Integer.parseInt(parts[2]);
	                    packages.add(new Package(x, y, weight));
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        


	      


	        return packages;
	    }
	    
 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    												//	Simulated Annealing algorithm
	    public static List<Tour> simulatedAnnealing(List<City> cities, List<Vehicle> vehicles, List<Tour> initialTours) {
	    	
	    	 List<Tour> betterTour = new ArrayList<>();
	         double temp = 10;
	         double coolingFactor = 0.85;
	         int iteration = 0;

	         // Initialize the best tours for each vehicle
	         for (int i = 0; i < vehicles.size(); i++) {
	        	 betterTour.add(initialTours.get(i));
	         }

	         while (temp > 1) {
	        	 System.out.println("--------------------->\n");
	             System.out.println("Iteration " + iteration + " ");
	            
	             // Perform simulated annealing for each vehicle
	             for (int i = 0; i < vehicles.size(); i++) {
	                 Vehicle vehicle = vehicles.get(i);
	                 Tour currentTour = betterTour.get(i);

	                 System.out.println("\nVehicle " + (i + 1) + " ");
	                 System.out.println(currentTour.toString22());
	                 System.out.println("The Remaining Capacity: " + vehicle.getRemainingCapacity());

	                 if (Math.random() < 0.5) {
	                     currentTour.switchCities();
	                 } else {
	                     currentTour.movePackage();
	                 }

	                 int currentLength = currentTour.getTourLength2();
	                 int betterLength = betterTour.get(i).getTourLength2();

	                 if (currentLength < betterLength || Math.random() < Operations.probability(currentLength, betterLength, temp)) {
	                	 betterTour.set(i, currentTour.duplicate2());
	                 } else {
	                     if (Math.random() < 0.5) {
	                         currentTour.switchCities();
	                     } else {
	                         currentTour.movePackage();
	                     }
	                 }
	             }

	             temp *= coolingFactor;
	             iteration++;
	          
	         }
	         return betterTour;
	    }
	
////////////////////////////////////////////////////////////////////////////////
public static Tour initializeTour(List<City> cities, Vehicle vehicle) {

Tour initialTour = new Tour(vehicle);

for (City city : cities) {
Package pkg = (Package) city;
boolean added = false;
//check the capacity and if allowed to add a package
if (vehicle.getRemainingCapacity() >= pkg.getWeight() && initialTour.canAddPackage(pkg)) {
initialTour.addPackage(pkg);
added = true;
}

if (!added) {
// break the loop
System.out.println("Sorry, there arent enough vehicles available.");
break;
}
}

System.out.println("Initial Tour for Vehicle " + vehicle + ":");
System.out.println(initialTour);

return initialTour;

}


    
    ////////////////////////
    public static int calculateTotalWeight(List<Package> packages) {
        int totalWeight = 0;

        for (Package p : packages) {
            totalWeight += p.getWeight();
        }

        return totalWeight;
    }

       
    public static int findIndexOfPackageWithMaxCapacity(List<Package> packages) {
        if (packages.isEmpty()) {
            // Handle the case when the list is empty
            return -1; // Return -1 to indicate no packages found
        }

        int maxCapacityIndex = 0; // Initialize with the index of the first package

        for (int i = 1; i < packages.size(); i++) {
            if (packages.get(i).getWeight() > packages.get(maxCapacityIndex).getWeight()) {
                maxCapacityIndex = i;
            }
        }

        return maxCapacityIndex;
    }

    
    
    
    
    static Package selectRandomPackage(List<Package> packages) {
        Random random = new Random();
        int randomIndex = random.nextInt(packages.size());
        return packages.get(randomIndex);
    }

    
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    												//GENETIC ALGORITHM
    
    
    public static List<Tour> geneticAlgorithm(List<List<Tour>> population, List<Package> packages, double mutationRate, List<Vehicle> vehicles) {
        Random random = new Random();
  //////////////////////////////////////////////////
        //FITNESS FUNCTION to select parents 
        List<Tour> parent1 = null;
        List<Tour> parent2 = null;
        int lowestCost1 = Integer.MAX_VALUE;
        int lowestCost2 = Integer.MAX_VALUE;

        for (List<Tour> vehicleRoutes : population) {
            int totalDistance = 0;
            for (Tour route : vehicleRoutes) {
                totalDistance += calculateTotalDistance(route);
            }

            if (totalDistance < lowestCost1) {
                parent2 = parent1;
                lowestCost2 = lowestCost1;
                parent1 = new ArrayList<>(vehicleRoutes); // Create a copy of the selected population
                lowestCost1 = totalDistance;
            } else if (totalDistance < lowestCost2 && !vehicleRoutes.equals(parent1)) {
                parent2 = new ArrayList<>(vehicleRoutes); // Create a copy of the selected population
                lowestCost2 = totalDistance;
            }
        }

        // Print the chosen populations
        System.out.println("Chosen Parent 1:");
        for (Tour tour : parent1) {
            System.out.println(tour);
        }

        System.out.println("Chosen Parent 2:");
        for (Tour tour : parent2) {
            System.out.println(tour);
        }
        
        
        
        System.out.println("*************************");


//////////////////////////////////////////////////////////////////
        
        
 
        	// Create children using crossover
        	Tour child11 = crossover(parent1.get(0), parent2.get(0)); 
        	Tour child22 = crossover(parent1.get(1), parent2.get(0));

        	// Create a list to hold the children
        	List<Tour> child1 = new ArrayList<>();
        	List<Tour> child2 = new ArrayList<>();
            List<City> reminingpackages1 = new ArrayList<>(); // to hold remaining packages 
            List<City> reminingpackages2 = new ArrayList<>(); // to hold remaining packages 

        	child1.add(child11);    
        	child2.add(child22);  

        	//for v2 add the package that who is not in v1 for generation1
            for (Package pack : packages) {
                if (!child11.containsCity(pack)) {
            		reminingpackages1.add(pack)	;
                }
            } 
           	//for v2 add the package that who is not in v1 for for generation2
            for (Package pack : packages) {
                if (!child22.containsCity(pack)) {
            		reminingpackages2.add(pack)	;
                }
            }
           
        		
        	
            Tour reminingTour1 = new Tour(reminingpackages1);
            Tour reminingTour2 = new Tour(reminingpackages2);

            child1.add(reminingTour1);
            child2.add(reminingTour2);
 
        	// Add children to the population
        	population.add(child1);
        	population.add(child2);


        	// Print the result of crossover
        	System.out.println(" \n                               Results of Crossover for Children:");
        	System.out.println("Child 1:");
        	for (int j = 0; j < child1.size(); j++) {
        	    Tour route = child1.get(j);
        	    System.out.print("Route " + (j + 1) + ": ");
        	    System.out.println("Route Length: " + calculateTotalDistance(route));
        	    System.out.println("Route: " + route);
        	}
        	System.out.println("****");

        	
        	System.out.println("Child 2:");
        	for (int j = 0; j < child2.size(); j++) {
        	    Tour route = child2.get(j);
        	    System.out.print("Route " + (j + 1) + ": ");
        	    System.out.println("Route Length: " + calculateTotalDistance(route));
        	    System.out.println("Route: " + route);
        	}
        	
        	
        	System.out.println();
        	
        	
            // Apply mutation to the children with a certain probability
            if (random.nextDouble() < mutationRate) {
                mutate(child1.get(0));
            }
            if (random.nextDouble() < mutationRate) {
                mutate(child2.get(0));
            }
            
            System.out.println("***************************");

            
         // Print the routes after mutation
            System.out.println("                                        Routes After Mutation:   ");
            System.out.println("Child 1:");
            for (int j = 0; j < child1.size(); j++) {
                Tour route = child1.get(j);
                System.out.print("Route " + (j + 1) + ": ");
                System.out.println("Route Length: " + calculateTotalDistance(route));
                System.out.println("Route: " + route);
            }
            System.out.println("****");

            System.out.println("Child 2:");
            for (int j = 0; j < child2.size(); j++) {
                Tour route = child2.get(j);
                System.out.print("Route " + (j + 1) + ": ");
                System.out.println("Route Length: " + calculateTotalDistance(route));
                System.out.println("Route: " + route);
            }
            
            
///////////////////////////////////////////////////////////// 
            List<Tour> bestPopulation = null;
            int lowestCostt = Integer.MAX_VALUE;


            // Calculate total distance for the best routes in the population
            for (List<Tour> vehicleRoutes : population) {
                int totalDistance = 0;
                for (Tour route : vehicleRoutes) {
                    totalDistance += calculateTotalDistance(route);
                }

                if (totalDistance < lowestCostt) {
                    bestPopulation = new ArrayList<>(vehicleRoutes); // Create a copy of the selected population
                    lowestCostt = totalDistance;
                }
            }

            // Print the best population
            System.out.println("*********************");

            System.out.println("                                    Best Population:");
            for (Tour tour : bestPopulation) {
                System.out.println(tour);
            }

           

            
 
            
             

       
        return bestPopulation;
    }

    
   

   ////////////////////////////////////// 
    public static Tour crossover(Tour parent1, Tour parent2) {

        // Create an empty child tour
        List<City> childCities = new ArrayList<>();


        
                Random random = new Random();
                int startIndex, endIndex;
            	int numCities= parent1.noCities();
        if( numCities != 0 ) {

            startIndex = random.nextInt(numCities);
            System.out.print( "starting index:"+startIndex);
            endIndex = random.nextInt(numCities);
            System.out.print( "   ending index:"+endIndex);
        }
        else {
        	return parent2 ;
        	
        }
        

        // Ensure startIndex is less than endIndex
        if (startIndex > endIndex) {
            int temp = startIndex;
            startIndex = endIndex;
            endIndex = temp;
        }
        // Print selected packages based on start and end indices
        System.out.println("\n Selected Packages:");
        for (int i = startIndex; i <= endIndex; i++) {
                City city1 = parent1.getCity(i);
                System.out.print("From Parent 1: " + city1);
            
        }
        System.out.println();
        // Copy the segment from parent1 to the child
        for (int i = startIndex; i <= endIndex; i++) {
            City city = parent1.getCity(i);
           childCities.add(city); 

            
        }

        // Add the remaining cities from parent2 while avoiding duplicates
        for (City city : parent2.getCities()) {
            if (!childCities.contains(city)) {
                childCities.add(city);
            }
        }
      
     // Ensure that the packages from parent2 are added after the ones from parent1
        int indexToInsert = endIndex + 1;
        for (City pack : parent2.getCities()) {
            if (!childCities.contains(pack)) {
                childCities.add(indexToInsert, pack);
                indexToInsert++; // Increment the index to maintain order
            }
        }  
     
     
        // Create the child tour from the childCities list
        Tour childTour = new Tour(childCities);
        return childTour;
    }


    
    
    public static boolean isCityInTours(City city, Tour tour1, Tour tour2) {
        return tour1.containsCity(city) || tour2.containsCity(city);
    }

//////////////////////////////////////////////////////////
    public static void mutate(Tour tour) {
        List<City> cities = tour.getCities();
        int numCities = cities.size();

        if (numCities < 2) {
            return; // No mutation can occur with fewer than 2 cities
        }

        Random random = ThreadLocalRandom.current();
        int index1 = random.nextInt(numCities);
        int index2 = random.nextInt(numCities);

        // Ensure that the two indices are distinct
        while (index1 == index2) {
            index2 = random.nextInt(numCities);
        }

        // Swap the two cities
        Collections.swap(cities, index1, index2);
    }

    public static int calculateTotalDistance(Tour tour) {
        return tour.getTourLength();
    }
    
    
    
    public static int calculateTotalWeightOfCities(List<City> cities) {
        int totalWeight = 0;

        for (City city : cities) {
            totalWeight += city.getWeight(); 
        }

        return totalWeight;
    }
//////////////////////////////////////
    public static List<List<Tour>> initializePopulation(List<Package> packages, List<Vehicle> vehicles, int populationSize) {
        System.out.println("									Populations");

        List<List<Tour>> population = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            List<Package> pc = new ArrayList<>(packages);
            List<Tour> vehicleRoutes = new ArrayList<>();

            // Randomly divide the packages between the two vehicles
            List<Package> vehicle1Packages = new ArrayList<>();
            List<Package> vehicle2Packages = new ArrayList<>();

            for (Package p : pc) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    vehicle1Packages.add(p);
                } else {
                    vehicle2Packages.add(p);
                }
            }

            // Create tours for each vehicle
            List<City> cities1 = new ArrayList<>();
            int totalWeight1 = 0;
            for (Package p : vehicle1Packages) {
                if (totalWeight1 + p.getWeight() <= vehicles.get(0).getCapacity()) {
                    cities1.add(p);
                    totalWeight1 += p.getWeight();
                }
            }

            List<City> cities2 = new ArrayList<>();
            int totalWeight2 = 0;
            for (Package p : vehicle2Packages) {
                if (totalWeight2 + p.getWeight() <= vehicles.get(1).getCapacity()) {
                    cities2.add(p);
                    totalWeight2 += p.getWeight();
                }
            }

            Tour tour1 = new Tour(cities1);
            Tour tour2 = new Tour(cities2);

            vehicleRoutes.add(tour1);
            vehicleRoutes.add(tour2);

            population.add(vehicleRoutes);

            // Print the population
            System.out.println("Population " + (i + 1) + ":");

            for (int j = 0; j < vehicles.size(); j++) {
                System.out.println("Vehicle " + (j + 1) + ":");
                Tour route = vehicleRoutes.get(j);
                System.out.println("Route " + (j + 1) + ":");
                System.out.println("Route Length: " + calculateTotalDistance(route));
                System.out.println("Route: " + route);
            }


            System.out.println();

        }
        System.out.println("*************************");

        return population;
    }


    
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
 
    
}