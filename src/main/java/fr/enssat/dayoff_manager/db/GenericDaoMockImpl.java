package fr.enssat.dayoff_manager.db;

import java.util.*;
import java.util.stream.Collectors;

public abstract class GenericDaoMockImpl<E extends GenericEntity> implements GenericDao<E> {

    private Map<Integer, E> items = new HashMap<>();
    private int nextID = 0;

    /**
     * Vérifie si la nouvelle entité ne déclenche pas d'erreurs de contraintes (lance une exception sinon)
     *
     * @param entity entité
     */
    public abstract void newEntityConstraintsCheck(E entity);

    @Override
    public void save(E entity) {
        if (entity.getId() == 0) {
            //new item
            newEntityConstraintsCheck(entity);
            entity.setId(++nextID);
        }

        items.put(entity.getId(), entity);
    }

    @Override
    public void delete(E entity) {
        E savedItem = items.get(entity.getId());
        if (savedItem == null) {
            throw new RuntimeException("Item not found");
        } else {
            items.remove(entity.getId());
        }
    }

    @Override
    public E findById(int id) {
        List<E> res = items.values().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (res.size() == 1) return res.get(0);
        return null;
    }

    @Override
    public List<E> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(items.values()));
    }

    @Override
    public String toString() {
        return Arrays.toString(items.values().toArray());
    }
}
