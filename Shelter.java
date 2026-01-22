import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.io.Serializable;

// Shelter.java - Генеричен приют
public class Shelter<T extends Animal> implements Serializable {
    private String shelterName;
    private String location;
    private ArrayList<T> animals;
    private int capacity; // Колко животни можем да побереме

    public Shelter(String shelterName, String location, int capacity) {
        this.shelterName = shelterName;
        this.location = location;
        this.capacity = capacity;
        this.animals = new ArrayList<>();
    }

    // Добави животно (ако има място)
    public void addAnimal(T animal) {
        if (animals.size() < capacity) {
            animals.add(animal);
            System.out.println("✓ Добавено: " + animal.getName());
        } else {
            System.out.println("✗ Приютът е пълен!");
        }
    }

    // Махни животно по ID
    public void removeAnimal(String id) {
        animals.removeIf(a -> a.getId().equals(id));
    }

    // Намери по ID
    public T findAnimalById(String id) {
        return animals.stream()
            .filter(a -> a.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    // Намери по условие (предикат)
    public List<T> findAnimals(Predicate<T> predicate) {
        return animals.stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    // Само свободните животни
    public List<T> getAvailableAnimals() {
        return animals.stream()
            .filter(a -> !a.isAdopted())
            .collect(Collectors.toList());
    }

    // Сортирай животните
    public void sortAnimals(Comparator<T> comparator) {
        animals.sort(comparator);
    }

    // Колко свободни места има
    public int getAvailableCapacity() {
        return capacity - animals.size();
    }

    // Getters & Setters
    public String getShelterName() { return shelterName; }
    public void setShelterName(String shelterName) { this.shelterName = shelterName; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public ArrayList<T> getAnimals() { return animals; }
    
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @Override
    public String toString() {
        return String.format("%s (%s) - Животни: %d/%d", 
            shelterName, location, animals.size(), capacity);
    }
}