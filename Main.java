import java.time.LocalDate;
import java.util.*;

// Main.java - Главното конзолно приложение
public class Main {
    private static PetAdoptionSystem system = new PetAdoptionSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Стартираме със sample данни
        initializeSampleData();
        
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = readInt("Избор: ");
            
            switch (choice) {
                case 1: manageShelters(); break;
                case 2: manageAnimals(); break;
                case 3: manageAdopters(); break;
                case 4: adoptionProcess(); break;
                case 5: searchAndFilter(); break;
                case 6: waitingListMenu(); break;
                case 7: system.generateReports(); break;
                case 8: dataManagement(); break;
                case 9:
                    System.out.println("Чао! 👋");
                    running = false;
                    break;
                default:
                    System.out.println("Невалиден избор!");
            }
        }
        scanner.close();
    }

    // ГЛАВНО МЕНЮ
    private static void showMainMenu() {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║   🐾 СИСТЕМА ЗА ОСИНОВЯВАНЕ 🐾           ║");
        System.out.println("╠═══════════════════════════════════════════╣");
        System.out.println("║ 1. Управление на приюти                   ║");
        System.out.println("║ 2. Управление на животни                  ║");
        System.out.println("║ 3. Управление на осиновители              ║");
        System.out.println("║ 4. Процес на осиновяване                  ║");
        System.out.println("║ 5. Търсене и филтриране                   ║");
        System.out.println("║ 6. Лист на чакане                         ║");
        System.out.println("║ 7. Отчети и статистика                    ║");
        System.out.println("║ 8. Запазване/Зареждане                    ║");
        System.out.println("║ 9. Изход                                  ║");
        System.out.println("╚═══════════════════════════════════════════╝");
    }

    // 1. УПРАВЛЕНИЕ НА ПРИЮТИ
    private static void manageShelters() {
        System.out.println("\n--- ПРИЮТИ ---");
        System.out.println("1. Добави нов приют");
        System.out.println("2. Виж всички приюти");
        System.out.println("3. Виж животни в приют");
        System.out.println("4. Статистика за приют");
        
        int choice = readInt("Избор: ");
        switch (choice) {
            case 1: addNewShelter(); break;
            case 2: viewAllShelters(); break;
            case 3: viewAnimalsInShelter(); break;
            case 4: shelterStatistics(); break;
        }
    }

    private static void addNewShelter() {
        System.out.println("\nВид приют (1-Dog, 2-Cat, 3-Bird, 4-Rabbit, 5-Mixed): ");
        int type = readInt("");
        
        System.out.print("Име на приюта: ");
        String name = scanner.nextLine();
        System.out.print("Локация: ");
        String location = scanner.nextLine();
        int capacity = readInt("Капацитет: ");
        
        // Правим приют според типа
        switch (type) {
            case 1:
                system.registerShelter(new Shelter<Dog>(name, location, capacity));
                break;
            case 2:
                system.registerShelter(new Shelter<Cat>(name, location, capacity));
                break;
            case 3:
                system.registerShelter(new Shelter<Bird>(name, location, capacity));
                break;
            case 4:
                system.registerShelter(new Shelter<Rabbit>(name, location, capacity));
                break;
            default:
                system.registerShelter(new Shelter<Animal>(name, location, capacity));
        }
    }

    private static void viewAllShelters() {
        System.out.println("\n📋 Всички приюти:");
        if (system.getShelters().isEmpty()) {
            System.out.println("Няма регистрирани приюти");
            return;
        }
        for (int i = 0; i < system.getShelters().size(); i++) {
            System.out.println(i + ". " + system.getShelters().get(i));
        }
    }

    private static void viewAnimalsInShelter() {
        viewAllShelters();
        if (system.getShelters().isEmpty()) return;
        
        int index = readInt("\nИндекс на приют (0-" + (system.getShelters().size()-1) + "): ");
        if (index >= 0 && index < system.getShelters().size()) {
            Shelter<? extends Animal> shelter = system.getShelters().get(index);
            System.out.println("\n🐾 Животни в " + shelter.getShelterName() + ":");
            if (shelter.getAnimals().isEmpty()) {
                System.out.println("Няма животни");
            } else {
                shelter.getAnimals().forEach(System.out::println);
            }
        }
    }

    private static void shelterStatistics() {
        viewAllShelters();
        if (system.getShelters().isEmpty()) return;
        
        int index = readInt("\nИндекс на приют: ");
        if (index >= 0 && index < system.getShelters().size()) {
            Shelter<? extends Animal> shelter = system.getShelters().get(index);
            System.out.println("\n📊 Статистика за " + shelter.getShelterName());
            System.out.println("Капацитет: " + shelter.getAnimals().size() + "/" + shelter.getCapacity());
            System.out.println("Свободни: " + shelter.getAvailableAnimals().size());
            System.out.println("Осиновени: " + (shelter.getAnimals().size() - shelter.getAvailableAnimals().size()));
        }
    }

    // 2. УПРАВЛЕНИЕ НА ЖИВОТНИ
    private static void manageAnimals() {
        System.out.println("\n--- ЖИВОТНИ ---");
        System.out.println("1. Добави ново животно");
        System.out.println("2. Виж всички животни");
        System.out.println("3. Актуализирай животно");
        System.out.println("4. Премахни животно");
        System.out.println("5. Обучи животно");
        
        int choice = readInt("Избор: ");
        switch (choice) {
            case 1: addNewAnimal(); break;
            case 2: viewAllAnimals(); break;
            case 3: updateAnimal(); break;
            case 4: removeAnimal(); break;
            case 5: trainAnimal(); break;
        }
    }

    private static void addNewAnimal() {
        if (system.getShelters().isEmpty()) {
            System.out.println("Първо добави приют!");
            return;
        }
        
        System.out.println("\nВид: 1-Dog, 2-Cat, 3-Bird, 4-Rabbit");
        int type = readInt("");
        
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Име: ");
        String name = scanner.nextLine();
        int age = readInt("Възраст: ");
        System.out.print("Порода: ");
        String breed = scanner.nextLine();
        
        System.out.println("Размер: 1-SMALL, 2-MEDIUM, 3-LARGE");
        Size size = Size.values()[readInt("") - 1];
        
        System.out.println("Темперамент: 1-CALM, 2-FRIENDLY, 3-ENERGETIC, 4-SHY");
        Temperament temp = Temperament.values()[readInt("") - 1];
        
        LocalDate arrival = LocalDate.now();
        
        Animal animal = null;
        
        switch (type) {
            case 1: // Dog
                boolean trained = readInt("Обучено за вкъщи (1-Да, 0-Не): ") == 1;
                int energy = readInt("Енергия (1-10): ");
                animal = new Dog(id, name, age, breed, size, temp, arrival, trained, energy);
                break;
            case 2: // Cat
                boolean indoor = readInt("Само за вътре (1-Да, 0-Не): ") == 1;
                boolean litter = readInt("Знае тоалетна (1-Да, 0-Не): ") == 1;
                animal = new Cat(id, name, age, breed, size, temp, arrival, indoor, litter);
                break;
            case 3: // Bird
                boolean talks = readInt("Говори (1-Да, 0-Не): ") == 1;
                System.out.print("Размах на крилата (см): ");
                double wingspan = scanner.nextDouble();
                scanner.nextLine();
                animal = new Bird(id, name, age, breed, size, temp, arrival, talks, wingspan);
                break;
            case 4: // Rabbit
                System.out.println("Козина: 1-SHORT, 2-MEDIUM, 3-LONG");
                FurLength fur = FurLength.values()[readInt("") - 1];
                System.out.print("Диета: ");
                String diet = scanner.nextLine();
                animal = new Rabbit(id, name, age, breed, size, temp, arrival, fur, diet);
                break;
        }
        
        if (animal != null) {
            viewAllShelters();
            int shelterIdx = readInt("В кой приют (индекс): ");
            if (shelterIdx >= 0 && shelterIdx < system.getShelters().size()) {
                @SuppressWarnings("unchecked")
                Shelter<Animal> shelter = (Shelter<Animal>) system.getShelters().get(shelterIdx);
                shelter.addAnimal(animal);
            }
        }
    }

    private static void viewAllAnimals() {
        System.out.println("\n🐾 Всички животни:");
        int count = 0;
        for (Shelter<? extends Animal> shelter : system.getShelters()) {
            for (Animal animal : shelter.getAnimals()) {
                System.out.println(count++ + ". " + animal);
            }
        }
        if (count == 0) System.out.println("Няма животни");
    }

    private static void updateAnimal() {
        System.out.print("ID на животно: ");
        String id = scanner.nextLine();
        
        for (Shelter<? extends Animal> shelter : system.getShelters()) {
            Animal animal = shelter.findAnimalById(id);
            if (animal != null) {
                System.out.print("Ново име (или Enter за без промяна): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) animal.setName(name);
                
                int age = readInt("Нова възраст (-1 за без промяна): ");
                if (age >= 0) animal.setAge(age);
                
                System.out.println("✓ Обновено!");
                return;
            }
        }
        System.out.println("✗ Не е намерено");
    }

    private static void removeAnimal() {
        System.out.print("ID на животно за премахване: ");
        String id = scanner.nextLine();
        
        for (Shelter<? extends Animal> shelter : system.getShelters()) {
            Animal animal = shelter.findAnimalById(id);
            if (animal != null) {
                shelter.removeAnimal(id);
                System.out.println("✓ Премахнато!");
                return;
            }
        }
        System.out.println("✗ Не е намерено");
    }

    private static void trainAnimal() {
        System.out.print("ID на животно: ");
        String id = scanner.nextLine();
        
        for (Shelter<? extends Animal> shelter : system.getShelters()) {
            Animal animal = shelter.findAnimalById(id);
            if (animal instanceof Trainable) {
                System.out.print("Умение за обучение: ");
                String skill = scanner.nextLine();
                ((Trainable) animal).train(skill);
                System.out.println("✓ Обучено! Текущо ниво: " + ((Trainable) animal).getTrainingLevel());
                return;
            } else if (animal != null) {
                System.out.println("✗ Това животно не може да се обучава");
                return;
            }
        }
        System.out.println("✗ Не е намерено");
    }

    // 3. УПРАВЛЕНИЕ НА ОСИНОВИТЕЛИ
    private static void manageAdopters() {
        System.out.println("\n--- ОСИНОВИТЕЛИ ---");
        System.out.println("1. Регистрирай нов");
        System.out.println("2. Виж всички");
        System.out.println("3. Актуализирай профил");
        System.out.println("4. Задай предпочитания");
        
        int choice = readInt("Избор: ");
        switch (choice) {
            case 1: registerAdopter(); break;
            case 2: viewAllAdopters(); break;
            case 3: updateAdopter(); break;
            case 4: setPreferences(); break;
        }
    }

    private static void registerAdopter() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Име: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Телефон: ");
        String phone = scanner.nextLine();
        
        System.out.println("Жилище: 1-APARTMENT, 2-HOUSE, 3-FARM");
        HousingType housing = HousingType.values()[readInt("") - 1];
        
        boolean hasYard = readInt("Има двор (1-Да, 0-Не): ") == 1;
        boolean hasPets = readInt("Има други животни (1-Да, 0-Не): ") == 1;
        
        Adopter adopter = new Adopter(id, name, email, phone, housing, hasYard, hasPets);
        system.registerAdopter(adopter);
    }

    private static void viewAllAdopters() {
        System.out.println("\n👥 Всички осиновители:");
        if (system.getAdopters().isEmpty()) {
            System.out.println("Няма регистрирани");
            return;
        }
        for (int i = 0; i < system.getAdopters().size(); i++) {
            System.out.println(i + ". " + system.getAdopters().get(i));
        }
    }

    private static void updateAdopter() {
        viewAllAdopters();
        if (system.getAdopters().isEmpty()) return;
        
        int idx = readInt("Индекс: ");
        if (idx >= 0 && idx < system.getAdopters().size()) {
            Adopter adopter = system.getAdopters().get(idx);
            System.out.print("Нов телефон (или Enter): ");
            String phone = scanner.nextLine();
            if (!phone.isEmpty()) adopter.setPhoneNumber(phone);
            System.out.println("✓ Обновено!");
        }
    }

    private static void setPreferences() {
        viewAllAdopters();
        if (system.getAdopters().isEmpty()) return;
        
        int idx = readInt("Индекс на осиновител: ");
        if (idx < 0 || idx >= system.getAdopters().size()) return;
        
        System.out.print("Предпочитан вид (Dog/Cat/Bird/Rabbit, или Enter за всички): ");
        String species = scanner.nextLine();
        if (species.isEmpty()) species = null;
        
        System.out.println("Размер (1-SMALL, 2-MEDIUM, 3-LARGE, 0-всички): ");
        int sizeChoice = readInt("");
        Size size = sizeChoice > 0 ? Size.values()[sizeChoice - 1] : null;
        
        System.out.println("Темперамент (1-CALM, 2-FRIENDLY, 3-ENERGETIC, 4-SHY, 0-всички): ");
        int tempChoice = readInt("");
        Temperament temp = tempChoice > 0 ? Temperament.values()[tempChoice - 1] : null;
        
        int minAge = readInt("Мин. възраст (0 за без): ");
        int maxAge = readInt("Макс. възраст (0 за без): ");
        
        AnimalPreferences prefs = new AnimalPreferences(
            species, size, temp,
            minAge > 0 ? minAge : null,
            maxAge > 0 ? maxAge : null
        );
        
        system.getAdopters().get(idx).setPreferences(prefs);
        System.out.println("✓ Предпочитания зададени!");
    }

    // 4. ПРОЦЕС НА ОСИНОВЯВАНЕ
    private static void adoptionProcess() {
        System.out.println("\n--- ОСИНОВЯВАНЕ ---");
        System.out.println("1. Провери съвместимост");
        System.out.println("2. Обработи осиновяване");
        System.out.println("3. История на осиновявания");
        System.out.println("4. Генерирай сертификат");
        
        int choice = readInt("Избор: ");
        switch (choice) {
            case 1: checkCompatibility(); break;
            case 2: processAdoption(); break;
            case 3: viewAdoptionHistory(); break;
            case 4: generateCertificate(); break;
        }
    }

    private static void checkCompatibility() {
        System.out.print("ID на животно: ");
        String animalId = scanner.nextLine();
        System.out.print("ID на осиновител: ");
        String adopterId = scanner.nextLine();
        
        // Намираме ги
        Animal animal = null;
        for (Shelter<? extends Animal> shelter : system.getShelters()) {
            animal = shelter.findAnimalById(animalId);
            if (animal != null) break;
        }
        
        Adopter adopter = system.getAdopters().stream()
            .filter(a -> a.getId().equals(adopterId))
            .findFirst()
            .orElse(null);
        
        if (animal == null || adopter == null) {
            System.out.println("✗ Не са намерени");
            return;
        }
        
        System.out.println("\n🔍 Проверка на съвместимост:");
        System.out.println("Животно: " + animal.getName());
        System.out.println("Осиновител: " + adopter.getName());
        
        if (adopter.canAdopt(animal)) {
            System.out.println("✓ СЪВМЕСТИМИ! Може да продължите с осиновяване");
            if (animal instanceof Adoptable) {
                System.out.println("Такса: " + ((Adoptable) animal).getAdoptionFee() + " лв");
            }
        } else {
            System.out.println("✗ НЕ СА СЪВМЕСТИМИ");
            List<String> reasons = AdoptionEligibilityChecker.getIneligibilityReasons(adopter, animal);
            reasons.forEach(r -> System.out.println("  - " + r));
        }
    }

    private static void processAdoption() {
        System.out.print("ID на животно: ");
        String animalId = scanner.nextLine();
        System.out.print("ID на осиновител: ");
        String adopterId = scanner.nextLine();
        
        system.processAdoption(animalId, adopterId);
    }

    private static void viewAdoptionHistory() {
        System.out.println("\n📜 История на осиновявания:");
        if (system.getAdoptionRecords().isEmpty()) {
            System.out.println("Няма записи");
            return;
        }
        for (int i = 0; i < system.getAdoptionRecords().size(); i++) {
            System.out.println(i + ". " + system.getAdoptionRecords().get(i));
        }
    }

    private static void generateCertificate() {
        viewAdoptionHistory();
        if (system.getAdoptionRecords().isEmpty()) return;
        
        int idx = readInt("Индекс на запис: ");
        if (idx >= 0 && idx < system.getAdoptionRecords().size()) {
            System.out.println(system.getAdoptionRecords().get(idx).generateAdoptionCertificate());
        }
    }

    // 5. ТЪРСЕНЕ И ФИЛТРИРАНЕ
    private static void searchAndFilter() {
        System.out.println("\n--- ТЪРСЕНЕ ---");
        System.out.println("1. По вид");
        System.out.println("2. По възраст");
        System.out.println("3. По размер");
        System.out.println("4. По темперамент");
        System.out.println("5. По порода");
        System.out.println("6. Само свободни");
        System.out.println("7. Сортирай");
        
        int choice = readInt("Избор: ");
        
        List<Animal> results = new ArrayList<>();
        
        switch (choice) {
            case 1: // По вид
                System.out.print("Вид (Dog/Cat/Bird/Rabbit): ");
                String type = scanner.nextLine();
                results = system.searchAnimals(a -> a.getClass().getSimpleName().equals(type));
                break;
            case 2: // По възраст
                int age = readInt("Възраст: ");
                results = system.searchAnimals(a -> a.getAge() == age);
                break;
            case 3: // По размер
                System.out.println("1-SMALL, 2-MEDIUM, 3-LARGE");
                Size size = Size.values()[readInt("") - 1];
                results = system.searchAnimals(a -> a.getSize() == size);
                break;
            case 4: // По темперамент
                System.out.println("1-CALM, 2-FRIENDLY, 3-ENERGETIC, 4-SHY");
                Temperament temp = Temperament.values()[readInt("") - 1];
                results = system.searchAnimals(a -> a.getTemperament() == temp);
                break;
            case 5: // По порода
                System.out.print("Порода: ");
                String breed = scanner.nextLine();
                results = system.searchAnimals(a -> a.getBreed().equalsIgnoreCase(breed));
                break;
            case 6: // Само свободни
                results = system.searchAnimals(a -> !a.isAdopted());
                break;
            case 7: // Сортирай
                System.out.println("1-По име, 2-По възраст, 3-По дата на пристигане");
                int sortChoice = readInt("");
                results = system.searchAnimals(a -> true); // Всички
                switch (sortChoice) {
                    case 1: results.sort(Comparator.comparing(Animal::getName)); break;
                    case 2: results.sort(Comparator.comparing(Animal::getAge)); break;
                    case 3: results.sort(Comparator.comparing(Animal::getArrivalDate)); break;
                }
                break;
        }
        
        System.out.println("\n🔍 Резултати (" + results.size() + "):");
        results.forEach(System.out::println);
    }

    // 6. ЛИСТ НА ЧАКАНЕ
    private static void waitingListMenu() {
        System.out.println("\n--- ЛИСТ НА ЧАКАНЕ ---");
        System.out.println("1. Добави в листа");
        System.out.println("2. Виж позиция");
        System.out.println("3. Виж целия лист");
        System.out.println("4. Уведоми при животно");
        
        int choice = readInt("Избор: ");
        switch (choice) {
            case 1: addToWaitingList(); break;
            case 2: checkPosition(); break;
            case 3: system.getWaitingList().displayWaitingList(); break;
            case 4: notifyWaiters(); break;
        }
    }

    private static void addToWaitingList() {
        viewAllAdopters();
        if (system.getAdopters().isEmpty()) return;
        
        int idx = readInt("Индекс на осиновител: ");
        if (idx < 0 || idx >= system.getAdopters().size()) return;
        
        Adopter adopter = system.getAdopters().get(idx);
        if (adopter.getPreferences() == null) {
            System.out.println("Първо задайте предпочитания (меню 3.4)");
            return;
        }
        
        system.getWaitingList().addToWaitingList(adopter, adopter.getPreferences());
    }

    private static void checkPosition() {
        System.out.print("ID на осиновител: ");
        String id = scanner.nextLine();
        int pos = system.getWaitingList().getPosition(id);
        if (pos > 0) {
            System.out.println("Позиция в листа: " + pos);
        } else {
            System.out.println("Не е в листа на чакане");
        }
    }

    private static void notifyWaiters() {
        System.out.print("ID на ново животно: ");
        String id = scanner.nextLine();
        
        for (Shelter<? extends Animal> shelter : system.getShelters()) {
            Animal animal = shelter.findAnimalById(id);
            if (animal != null) {
                system.getWaitingList().notifyWaiters(animal);
                return;
            }
        }
        System.out.println("✗ Животно не е намерено");
    }

    // 8. DATA MANAGEMENT
    private static void dataManagement() {
        System.out.println("\n--- ДАННИ ---");
        System.out.println("1. Запази данни");
        System.out.println("2. Зареди данни");
        
        int choice = readInt("Izbор: ");
        if (choice == 1) {
            System.out.println("Запазване не е имплементирано (трябва Gson библиотека)");
        } else if (choice == 2) {
            System.out.println("Зареждане не е имплементирано");
        }
    }

    // HELPER МЕТОДИ
    private static int readInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Въведи число: ");
        }
        int num = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        return num;
    }

    // SAMPLE ДАННИ за тестване
    private static void initializeSampleData() {
        // Създаваме приюти
        Shelter<Dog> dogShelter = new Shelter<>("София Приют", "София", 20);
        Shelter<Cat> catShelter = new Shelter<>("Котешки рай", "Пловдив", 15);
        
        system.registerShelter(dogShelter);
        system.registerShelter(catShelter);
        
        // Добавяме животни
        Dog dog1 = new Dog("D001", "Шаро", 3, "Мъгъл", Size.MEDIUM, 
            Temperament.FRIENDLY, LocalDate.now().minusDays(30), true, 7);
        Dog dog2 = new Dog("D002", "Рекс", 5, "Немска овчарка", Size.LARGE,
            Temperament.CALM, LocalDate.now().minusDays(60), true, 5);
        
        Cat cat1 = new Cat("C001", "Мица", 2, "Персийска", Size.SMALL,
            Temperament.SHY, LocalDate.now().minusDays(20), true, true);
        
        dogShelter.addAnimal(dog1);
        dogShelter.addAnimal(dog2);
        catShelter.addAnimal(cat1);
        
        // Добавяме осиновител
        Adopter adopter1 = new Adopter("A001", "Иван Петров", "ivan@mail.com",
            "0888123456", HousingType.HOUSE, true, false);
        system.registerAdopter(adopter1);
        
        System.out.println("✓ Sample данни заредени!");
    }
}