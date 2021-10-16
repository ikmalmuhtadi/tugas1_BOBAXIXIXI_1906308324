package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OnDelete;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "topping")
public class ToppingModel implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idTopping;

    @NotNull
    @Size(max=255)
    @Column(name="name_topping", nullable = false)
    private String namaTopping;

    @NotNull
    @Column(name = "price_topping", nullable = false)
    private Integer hargaTopping;

    // Relasi dengan BobateaModel
    @OneToMany(mappedBy = "Topping", fetch = FetchType.LAZY)
    private List<BobateaModel> listBobatea;
}
