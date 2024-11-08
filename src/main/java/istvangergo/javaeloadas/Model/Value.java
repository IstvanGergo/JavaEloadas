package istvangergo.javaeloadas.Model;
import javax.persistence.*;
@Entity
@Table(name = "Value")
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
}
