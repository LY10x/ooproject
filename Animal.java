import java.time.LocalDate;
import java.io.Serializable;

public abstract class Animal implements Serializable {
    private String id;
    private String name;
    private int age;
    private String breed;
    private Size size;
    private Temperament temperament;
    private boolean isAdopted;
    private LocalDate arrivalDate;

    // Конструктор - зареждаме всичко
    public Animal(String id, String name, int age, String breed, Size size, 
                  Temperament temperament, LocalDate arrivalDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.size = size;
        this.temperament = temperament;
        this.isAdopted = false; // Като влезе в приюта - свободно е
        this.arrivalDate = arrivalDate;
    }

    // Абстрактни методи - всяко животно си има свой звук и нужди
    public abstract String makeSound();
    public abstract String getSpecialNeeds();

    // Getters & Setters - стандартна работа
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }
    
    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }
    
    public Temperament getTemperament() { return temperament; }
    public void setTemperament(Temperament temperament) { this.temperament = temperament; }
    
    public boolean isAdopted() { return isAdopted; }
    public void setAdopted(boolean adopted) { isAdopted = adopted; }
    
    public LocalDate getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDate arrivalDate) { this.arrivalDate = arrivalDate; }

    @Override
    public String toString() {
        return String.format("ID: %s | %s (%s) | %d год. | %s | %s | %s | Статус: %s",
            id, name, breed, age, size, temperament, 
            arrivalDate, isAdopted ? "Осиновено" : "Свободно");
    }
}