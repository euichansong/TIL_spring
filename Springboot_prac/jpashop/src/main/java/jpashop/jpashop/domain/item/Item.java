package jpashop.jpashop.domain.item;

import jakarta.persistence.*;
import jpashop.jpashop.domain.Category;
import jpashop.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 비즈니스 로직 // //Setter로 변경하는게 아니라 핵심 비즈니스 로직 가지고 처리해야 한다
    /*
    stock 증가
    */
    public void addStore(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    /*
    stock 감소
     */
    public void removeStore(int quantity) {
        this.stockQuantity -= quantity;
    }

}
