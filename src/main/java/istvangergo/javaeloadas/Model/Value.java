package istvangergo.javaeloadas.Model;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Value")
public class Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer forint;

    public Value(Integer id, Integer forint) {
        this.id = id;
        this.forint = forint;
    }

    public Value() {}

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null|| getClass() != obj.getClass()) {
            return false;
        }
        Value value = (Value) obj;
        return Objects.equals(id, value.id) && Objects.equals(forint, value.forint);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getForint() {
        return forint;
    }

    public void setForint(Integer forint) {
        this.forint = forint;
    }

}
