package fr.enssat.dayoff_manager.db.dayoff;

import java.util.ArrayList;
import java.util.List;

public class DayoffDaoMockImpl implements DayoffDao {

    @Override
    public void save(Dayoff entity) {
        //nothing
    }

    @Override
    public void delete(Dayoff entity) {
        //nothing
    }

    @Override
    public Dayoff findById(int id) {
        //TODO
        return null;
    }

    @Override
    public List<Dayoff> getAll() {
        //TODO
        return new ArrayList<>();
    }


    @Override
    public void validate(Dayoff dayoff, String comment) {

    }

    @Override
    public void refuse(Dayoff dayoff, String comment) {

    }
}
