package fr.enssat.dayoff_manager.db.dayoff_type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DayoffTypeDaoMockImpl implements DayoffTypeDao {

    private List<DayoffType> dayoffTypeList = new ArrayList<>();
    private int nextID = -1;

    public DayoffTypeDaoMockImpl() {
        DayoffType type1 = new DayoffType("Congés", 5.0f);
        type1.setId(++nextID);
        DayoffType type2 = new DayoffType("Maladie", null);
        type2.setId(++nextID);

        dayoffTypeList.add(type1);
        dayoffTypeList.add(type2);
    }

    @Override
    public void save(DayoffType entity) {
        entity.setId(++nextID);
        dayoffTypeList.add(entity);
    }

    @Override
    public void delete(DayoffType entity) {
        dayoffTypeList.remove(entity);
    }

    @Override
    public DayoffType findById(int id) {
        List<DayoffType> res = dayoffTypeList.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if (res.size() == 1) return res.get(0);
        return null;
    }

    @Override
    public List<DayoffType> getAll() {
        return Collections.unmodifiableList(dayoffTypeList);
    }
}
