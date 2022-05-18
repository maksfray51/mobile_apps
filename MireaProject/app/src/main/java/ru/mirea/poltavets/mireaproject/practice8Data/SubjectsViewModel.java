package ru.mirea.poltavets.mireaproject.practice8Data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SubjectsViewModel extends ViewModel {
    private final LiveData<List<Subjects>> disciplines;
    private final AppDataBase appDatabase = App.instance.getDatabase();
    private final SubjectsDao subjectsDao = appDatabase.subjectsDao();

    public SubjectsViewModel(){
        disciplines = subjectsDao.getAllDisciplines();
    }

    public void addSubject(Subjects disciplines){
        subjectsDao.insert(disciplines);
    }

    public LiveData<List<Subjects>> getDiscipLiveData(){
        return disciplines;
    }
    public List<Subjects> getDiscip(){
        return disciplines.getValue();
    }
}
