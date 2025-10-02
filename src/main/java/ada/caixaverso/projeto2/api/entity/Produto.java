package ada.caixaverso.projeto2.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "taxaJurosAnual", nullable = false, precision = 12, scale = 9)
    private BigDecimal taxaJurosAnual;

    @Column(name = "prazoMaximoMeses")
    private Integer prazoMaximoMeses;
}
