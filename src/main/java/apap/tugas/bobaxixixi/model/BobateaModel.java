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
@Table(name = "bobatea")
public class BobateaModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idBobatea;

    @NotNull
    @Size(max=255)
    @Column(name="name_bobatea", nullable = false)
    private String namaVarian;

    @NotNull
    @Column(name = "price_bobatea", nullable = false)
    private Integer hargaBobatea;

    @NotNull
    @Column(name = "size", nullable = false)
    private Integer ukuranBobatea;

    @NotNull
    @Column(name = "ice_level", nullable = false)
    private Integer icelevelBobatea;

    @NotNull
    @Column(name = "sugar_level", nullable = false)
    private Integer sugarlevelBobatea;

    //Relasi dengan ToppingModel
    @ManyToOne(fetch = FetchType.LAZY)
    private ToppingModel Topping;

    // Relasi dengan StoreBobateaModel
    @OneToMany(mappedBy = "bobatea", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoreBobateaModel> ListStoreBobatea;
}
