package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "store")
public class StoreModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idToko;

    @NotNull
    @Size(max=255)
    @Column(name="store_name", nullable = false)
    private String namaToko;

    @NotNull
    @Size(max=255)
    @Column(name="address", nullable = false)
    private String alamatToko;

    @NotNull
    @Size(max=10)
    @Column(name="store_code", nullable = false, unique = true)
    private String noTeleponToko;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuBuka;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuTutup;

    // Relasi dengan ManagerModel
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idManager", referencedColumnName = "idManager")
    private ManagerModel manager;

    // Relasi dengan StoreBobateaModel
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoreBobateaModel> listStoreBobatea;


}
