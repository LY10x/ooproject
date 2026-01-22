import java.io.Serializable;

public class Adopter implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private HousingType housingType; // Къща/апартамент/ферма
    private boolean hasYard; // Има ли двор
    private boolean hasOtherPets; // Има ли други животни
    private AnimalPreferences preferences;

    public Adopter(String id, String name, String email, String phoneNumber,
                   HousingType housingType, boolean hasYard, boolean hasOtherPets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.housingType = housingType;
        this.hasYard = hasYard;
        this.hasOtherPets = hasOtherPets;
    }

    // Може ли да осинови това животно (базов чек)
    public boolean canAdopt(Animal animal) {
        if (preferences != null && !preferences.matches(animal)) {
            return false; // Не отговаря на предпочитанията
        }
        return AdoptionEligibilityChecker.checkEligibility(this, animal);
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public HousingType getHousingType() { return housingType; }
    public void setHousingType(HousingType housingType) { this.housingType = housingType; }
    
    public boolean hasYard() { return hasYard; }
    public void setHasYard(boolean hasYard) { this.hasYard = hasYard; }
    
    public boolean hasOtherPets() { return hasOtherPets; }
    public void setHasOtherPets(boolean hasOtherPets) { this.hasOtherPets = hasOtherPets; }
    
    public AnimalPreferences getPreferences() { return preferences; }
    public void setPreferences(AnimalPreferences preferences) { this.preferences = preferences; }

    @Override
    public String toString() {
        return String.format("%s (%s) | %s | Двор: %s | Други животни: %s",
            name, id, housingType, hasYard ? "Да" : "Не", hasOtherPets ? "Да" : "Не");
    }
}