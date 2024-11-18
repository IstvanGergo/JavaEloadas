package istvangergo.javaeloadas.Model;
import javax.persistence.*;

@Entity
@Table(name = "Animal")
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "ValueID")
    private Value value;

    private Integer year;

    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;

    public Animal(int id, String name,  int year, Value value, Category category) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.value = value;
        this.category = category;
    }

    public Animal() {}

    public Integer getValue() {
        return value.getId();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getCategory() {
        return category.getId();
    }
    public String getCategoryName() {
        return category != null ? category.getName() : "";
    }

    public int getForint() {
        return value != null ? value.getForint() : 0;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
