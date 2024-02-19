package jpashop.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpashop.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { // id값이 없다는 것은 새로 생성되었다는 것 >> 신규로 등록한다 >> persist
            em.persist(item);
        } else {
            em.merge(item);
        }

    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {

        return em.createQuery("select i from Item i", Item.class).getResultList();
    }
}
