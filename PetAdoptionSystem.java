import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PetAdoptionSystem {
    private List<Shelter<? extends Animal>> shelters;
    private List<AdoptionRecord<?>> adoptionRecords;
    private List<Adopter> adopters;
    private WaitingList<Animal> waitingList;
    private List<Observer> observers;
    private DataManager dataManager;

    public PetAdoptionSystem() {
        this.shelters = new ArrayList<>();
        this.adoptionRecords = new ArrayList<>();
        this.adopters = new ArrayList<>();
        this.waitingList = new WaitingList<>();
        this.observers = new ArrayList<>();
        this.dataManager = new DataManager();
    }

    // Регистрираме нов приют
    public void registerShelter(Shelter<? extends Animal> shelter) {
        shelters.add(shelter);
        System.out.println("✓ Приют регистриран: " + shelter.getShelterName());
    }

    // Регистрираме нов осиновител
    public void registerAdopter(Adopter adopter) {
        adopters.add(adopter);
        System.out.println("✓ Осиновител регистриран: " + adopter.getName());
    }

    // ОСНОВНА ФУНКЦИЯ - Обработка на осиновяване
    public void processAdoption(String animalId, String adopterId) {
        // Намираме животното
        Animal animal = findAnimalInAllShelters(animalId);
        if (animal == null) {
            System.out.println("✗ Животно с ID " + animalId + " не е намерено");
            return;
        }

        // Намираме осиновителя
        Adopter adopter = findAdopterById(adopterId);
        if (adopter == null) {
            System.out.println("✗ Осиновител с ID " + adopterId + " не е намерен");
            return;
        }

        // Проверяваме дали може да го осинови
        if (!adopter.canAdopt(animal)) {
            System.out.println("✗ Осиновяването не е възможно!");
            List<String> reasons = AdoptionEligibilityChecker.getIneligibilityReasons(adopter, animal);
            reasons.forEach(r -> System.out.println("  - " + r));
            return;
        }

        // Всичко ОК - правим осиновяването
        animal.setAdopted(true);
        
        double fee = 0;
        if (animal instanceof Adoptable) {
            fee = ((Adoptable) animal).getAdoptionFee();
        }

        String recordId = "REC-" + System.currentTimeMillis();
        AdoptionRecord<Animal> record = new AdoptionRecord<>(
            recordId, animal, adopter, LocalDate.now(), fee, "Успешно осиновяване"
        );
        
        adoptionRecords.add(record);
        
        // Уведомяваме наблюдателите
        notifyObservers(animal);
        
        System.out.println("\n🎉 УСПЕШНО ОСИНОВЯВАНЕ! 🎉");
        System.out.println(record.generateAdoptionCertificate());
    }

    // Търсим животно във всички приюти
    private Animal findAnimalInAllShelters(String animalId) {
        for (Shelter<? extends Animal> shelter : shelters) {
            Animal animal = shelter.findAnimalById(animalId);
            if (animal != null) {
                return animal;
            }
        }
        return null;
    }

    // Намираме осиновител по ID
    private Adopter findAdopterById(String adopterId) {
        return adopters.stream()
            .filter(a -> a.getId().equals(adopterId))
            .findFirst()
            .orElse(null);
    }

    // Търсене на животни по критерии
    public List<Animal> searchAnimals(Predicate<Animal> criteria) {
    List<Animal> results = new ArrayList<>();
    for (Shelter<? extends Animal> shelter : shelters) {
        for (Animal animal : shelter.getAnimals()) {
            if (criteria.test(animal)) {
                results.add(animal);
            }
        }
    }
    return results;
}

    // Observer Pattern методи
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(Animal animal) {
        for (Observer observer : observers) {
            observer.update(animal);
        }
    }

    // ОТЧЕТИ И СТАТИСТИКА
    public void generateReports() {
        System.out.println("\n═══════════════ ОТЧЕТИ ═══════════════");
        
        // Брой животни по видове
        Map<String, Long> animalsByType = new HashMap<>();
        for (Shelter<? extends Animal> shelter : shelters) {
            for (Animal animal : shelter.getAnimals()) {
                String type = animal.getClass().getSimpleName();
                animalsByType.put(type, animalsByType.getOrDefault(type, 0L) + 1);
            }
        }
        
        System.out.println("\n📊 Животни по видове:");
        animalsByType.forEach((type, count) -> 
            System.out.println("  " + type + ": " + count));
        
        // Брой осиновявания
        System.out.println("\n📈 Общо осиновявания: " + adoptionRecords.size());
        
        // Средно време за осиновяване
        if (!adoptionRecords.isEmpty()) {
            double avgDays = adoptionRecords.stream()
                .filter(r -> r.getAnimal().getArrivalDate() != null)
                .mapToLong(r -> ChronoUnit.DAYS.between(
                    r.getAnimal().getArrivalDate(), 
                    r.getAdoptionDate()))
                .average()
                .orElse(0);
            System.out.println("⏱️  Средно време до осиновяване: " + (int)avgDays + " дни");
        }
        
        // Най-популярни породи
        Map<String, Long> breedCount = adoptionRecords.stream()
            .collect(Collectors.groupingBy(
                r -> r.getAnimal().getBreed(),
                Collectors.counting()
            ));
        
        if (!breedCount.isEmpty()) {
            System.out.println("\n🏆 Най-популярни породи:");
            breedCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
        }
        
        // Капацитет на приютите
        System.out.println("\n🏠 Статус на приютите:");
        for (Shelter<? extends Animal> shelter : shelters) {
            int used = shelter.getAnimals().size();
            int capacity = shelter.getCapacity();
            double percentage = (used * 100.0) / capacity;
            System.out.println(String.format("  %s: %d/%d (%.1f%%)", 
                shelter.getShelterName(), used, capacity, percentage));
        }
        
        System.out.println("════════════════════════════════════════\n");
    }

    // Getters
    public List<Shelter<? extends Animal>> getShelters() { return shelters; }
    public List<AdoptionRecord<?>> getAdoptionRecords() { return adoptionRecords; }
    public List<Adopter> getAdopters() { return adopters; }
    public WaitingList<Animal> getWaitingList() { return waitingList; }
    public DataManager getDataManager() { return dataManager; }
}