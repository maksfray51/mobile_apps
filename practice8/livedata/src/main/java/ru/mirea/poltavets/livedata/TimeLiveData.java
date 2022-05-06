package ru.mirea.poltavets.livedata;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class TimeLiveData {
    private static MutableLiveData<Long> data = new MutableLiveData<Long>();
    //sets latest time to TimeLiveData
    static LiveData<Long> getTime(){
        data.setValue(new Date().getTime());
        return data;
    }
    static void setTime(){
        data.setValue(new Date().getTime());
    }
    // преобразование long в дату
    private static LiveData getStringTime = Transformations.map(data, new
            Function<Long, String>() {
                @Override
                public String apply(Long input) {
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return df.format(calendar.getTime());
                }
            });
    static LiveData<String> getDate(){
        return getStringTime;
    }
}