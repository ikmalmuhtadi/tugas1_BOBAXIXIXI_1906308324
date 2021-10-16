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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OnDelete;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "store_bobatea")
public class StoreBobateaModel implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idStoreBobatea;

    @NotNull
    @Size(max=12)
    @Column(name="production_code", nullable = false, unique = true)
    private String kodeProduksi;

//    Relasi dengan Storemodel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idToko", referencedColumnName = "idToko", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StoreModel store;


    //    Relasi dengan BobateaModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idBobatea", referencedColumnName = "idBobatea", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BobateaModel bobatea;

}
