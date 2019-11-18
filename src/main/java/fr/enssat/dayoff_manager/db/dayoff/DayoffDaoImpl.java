package fr.enssat.dayoff_manager.db.dayoff;

import fr.enssat.dayoff_manager.db.GenericDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class DayoffDaoImpl extends GenericDaoImpl<Dayoff> implements DayoffDao {

    public DayoffDaoImpl() {
        super(Dayoff.class);
    }

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
        // probleme : pas d'id dans la classe Dayoff ?
        return null;
    }

    @Override
    public List<Dayoff> getAll() {
        List<Dayoff> listDayoff = new ArrayList<Dayoff>();
        return listDayoff;
    }

}
