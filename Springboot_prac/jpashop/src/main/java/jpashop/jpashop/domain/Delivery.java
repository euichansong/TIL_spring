package jpashop.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter

public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    // ORDINAL 이 기본인데 왠만하면 안쓰는게 낫다
    // 밑의 경우 ready가 0, comp가 1,인데 그 사이 abc 가 하나 추가 되는 경우
    // comp가 2로 밀리고 comp가 다 abc가 되어 버린다
    private DeliveyStatus status; // ready, comp

}
