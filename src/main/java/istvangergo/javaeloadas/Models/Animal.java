package istvangergo.javaeloadas.Models;
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
}
