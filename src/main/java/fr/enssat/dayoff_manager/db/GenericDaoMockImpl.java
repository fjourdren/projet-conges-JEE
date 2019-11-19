package fr.enssat.dayoff_manager.db;

import java.util.*;
import java.util.stream.Collectors;

public class GenericDaoMockImpl<E extends GenericEntity> implements GenericDao<E> {

    private Map<Integer, E> items = new HashMap<>();
    private int nextID = 0;

    @Override
    public void save(E entity) {
        System.out.println("[MOCK] save : " + entity.toString());
        if (entity.getId() == 0) {
            //new item
            entity.setId(++nextID);
        }

        items.put(entity.getId(), entity);
        printAllContent();
    }

    @Override
    public void delete(E entity) {
        System.out.println("[MOCK] delete : " + entity.toString());
        E savedItem = items.get(entity.getId());
        if (savedItem == null) {
            throw new RuntimeException("Item not found");
        } else {
            items.remove(entity.getId());
        }
        printAllContent();
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

    private void printAllContent() {
        System.out.println("---");
        System.out.println(DaoProvider.getEmployeeDao().toString());
        System.out.println("---");
        System.out.println(DaoProvider.getDepartmentDao().toString());
        System.out.println("---");
        System.out.println(DaoProvider.getDayoffCountDao().toString());
        System.out.println("---");
        System.out.println(DaoProvider.getDayoffTypeDao().toString());
        System.out.println("---");
        System.out.println(DaoProvider.getDayoffDao().toString());
        System.out.println("---");
    }
}
