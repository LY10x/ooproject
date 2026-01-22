import java.io.Serializable;

public class AnimalPreferences implements Serializable {
    private String preferredSpecies; // Dog/Cat/Bird/Rabbit
    private Size preferredSize;
    private Temperament preferredTemperament;
    private Integer maxAge; // Максимална възраст
    private Integer minAge; // Минимална възраст

    public AnimalPreferences(String preferredSpecies, Size preferredSize,
                           Temperament preferredTemperament, Integer minAge, Integer maxAge) {
        this.preferredSpecies = preferredSpecies;
        this.preferredSize = preferredSize;
        this.preferredTemperament = preferredTemperament;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    // Пасва ли животното на предпочитанията
    public boolean matches(Animal animal) {
        if (preferredSpecies != null && !animal.getClass().getSimpleName().equals(preferredSpecies)) {
            return false;
        }
        if (preferredSize != null && animal.getSize() != preferredSize) {
            return false;
        }
        if (preferredTemperament != null && animal.getTemperament() != preferredTemperament) {
            return false;
        }
        if (minAge != null && animal.getAge() < minAge) {
            return false;
        }
        if (maxAge != null && animal.getAge() > maxAge) {
            return false;
        }
        return true;
    }

    // Getters & Setters
    public String getPreferredSpecies() { return preferredSpecies; }
    public void setPreferredSpecies(String preferredSpecies) { this.preferredSpecies = preferredSpecies; }
    
    public Size getPreferredSize() { return preferredSize; }
    public void setPreferredSize(Size preferredSize) { this.preferredSize = preferredSize; }
    
    public Temperament getPreferredTemperament() { return preferredTemperament; }
    public void setPreferredTemperament(Temperament preferredTemperament) { 
        this.preferredTemperament = preferredTemperament; 
    }
    
    public Integer getMaxAge() { return maxAge; }
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }
    
    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    @Override
    public String toString() {
        return String.format("Вид: %s | Размер: %s | Темп.: %s | Възраст: %s-%s",
            preferredSpecies != null ? preferredSpecies : "Всички",
            preferredSize != null ? preferredSize : "Всички",
            preferredTemperament != null ? preferredTemperament : "Всички",
            minAge != null ? minAge : "0",
            maxAge != null ? maxAge : "∞");
    }
}


