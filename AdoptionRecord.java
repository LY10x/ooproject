import java.time.LocalDate;
import java.io.Serializable;

// AdoptionRecord.java - Запис за осиновяване
public class AdoptionRecord<T extends Animal> implements Serializable {
    private String recordId;
    private T animal;
    private Adopter adopter;
    private LocalDate adoptionDate;
    private double adoptionFee;
    private String notes;

    public AdoptionRecord(String recordId, T animal, Adopter adopter, 
                         LocalDate adoptionDate, double adoptionFee, String notes) {
        this.recordId = recordId;
        this.animal = animal;
        this.adopter = adopter;
        this.adoptionDate = adoptionDate;
        this.adoptionFee = adoptionFee;
        this.notes = notes;
    }

    // Генерира сертификат за осиновяване
    public String generateAdoptionCertificate() {
        StringBuilder cert = new StringBuilder();
        cert.append("\n╔════════════════════════════════════════════════╗\n");
        cert.append("║       СЕРТИФИКАТ ЗА ОСИНОВЯВАНЕ                ║\n");
        cert.append("╠════════════════════════════════════════════════╣\n");
        cert.append(String.format("║ Запис: %-39s ║\n", recordId));
        cert.append(String.format("║ Дата: %-40s ║\n", adoptionDate));
        cert.append("╠════════════════════════════════════════════════╣\n");
        cert.append(String.format("║ Животно: %-37s ║\n", animal.getName()));
        cert.append(String.format("║ Вид: %-41s ║\n", animal.getClass().getSimpleName()));
        cert.append(String.format("║ Порода: %-38s ║\n", animal.getBreed()));
        cert.append("╠════════════════════════════════════════════════╣\n");
        cert.append(String.format("║ Осиновител: %-34s ║\n", adopter.getName()));
        cert.append(String.format("║ Email: %-39s ║\n", adopter.getEmail()));
        cert.append(String.format("║ Телефон: %-37s ║\n", adopter.getPhoneNumber()));
        cert.append("╠════════════════════════════════════════════════╣\n");
        cert.append(String.format("║ Такса: %.2f лв %-30s ║\n", adoptionFee, ""));
        if (notes != null && !notes.isEmpty()) {
            cert.append(String.format("║ Бележки: %-35s ║\n", notes));
        }
        cert.append("╚════════════════════════════════════════════════╝\n");
        
        return cert.toString();
    }

    // Getters & Setters
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    
    public T getAnimal() { return animal; }
    public void setAnimal(T animal) { this.animal = animal; }
    
    public Adopter getAdopter() { return adopter; }
    public void setAdopter(Adopter adopter) { this.adopter = adopter; }
    
    public LocalDate getAdoptionDate() { return adoptionDate; }
    public void setAdoptionDate(LocalDate adoptionDate) { this.adoptionDate = adoptionDate; }
    
    public double getAdoptionFee() { return adoptionFee; }
    public void setAdoptionFee(double adoptionFee) { this.adoptionFee = adoptionFee; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return String.format("Запис %s: %s осинови %s на %s за %.2f лв",
            recordId, adopter.getName(), animal.getName(), adoptionDate, adoptionFee);
    }
}