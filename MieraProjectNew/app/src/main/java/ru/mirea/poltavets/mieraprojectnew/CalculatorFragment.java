package ru.mirea.poltavets.mieraprojectnew;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentCalculatorBinding;
import ru.mirea.poltavets.mieraprojectnew.databinding.FragmentDataBaseBinding;


public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;
    private static Logger logger = Logger.getLogger(CalculatorFragment.class.getName());
    private String line = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        TextView textView = binding.answer;


        showline(view, textView, R.id.zero);
        showline(view, textView, R.id.one);
        showline(view, textView, R.id.two);
        showline(view, textView, R.id.three);
        showline(view, textView, R.id.four);
        showline(view, textView, R.id.five);
        showline(view, textView, R.id.six);
        showline(view, textView, R.id.seven);
        showline(view, textView, R.id.eight);
        showline(view, textView, R.id.nine);
        showline(view, textView, R.id.times);
        showline(view, textView, R.id.plus);
        showline(view, textView, R.id.minus);
        showline(view, textView, R.id.divided);
        showline(view, textView, R.id.delete);
        showline(view, textView, R.id.equals);

        return binding.getRoot();
    }

    private void showline(View view, TextView textView, int number) {
        // калькулятор для 2-ух чисел. Формат ввода число-знак-число
        Button button = view.findViewById(number);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Button clickedBtn = (Button) v;
                String clickedBtnStr = clickedBtn.getText().toString();
                line += clickedBtnStr;
                logger.info("Clicked btn "+ clickedBtn.getText().toString());

                if (clickedBtnStr.equals("C")) {line = "";}
                if (clickedBtnStr.equals("=")) {
                    // Созданим переменную, в которой будем хранить введённое выражение
                    String expression = (String) textView.getText();
                    logger.info("Expression: " + expression);

                    ArrayList<Integer> operationsIndexList = new ArrayList<>();
                    // Ищем индекс вхождения знака
                    int indexPlus = expression.indexOf("+");
                    int indexMinus = expression.indexOf("-");
                    int indexDivide = expression.indexOf("/");
                    int indexMultiply = expression.indexOf("*");

                    // Ищем максимальный, т.к. у 3-ёх index будет равен -1 (т.к. не будет найден знак)
                    // А у оставшегося будет больше нуля
                    operationsIndexList.add(indexPlus);
                    operationsIndexList.add(indexMinus);
                    operationsIndexList.add(indexDivide);
                    operationsIndexList.add(indexMultiply);
                    int indexOfOperation = Collections.max(operationsIndexList);
                    logger.info("operation index: " + Collections.max(operationsIndexList));

                    // Делаем первое число
                    String stringNumOne = "";
                    for (int i = 0; i < indexOfOperation; i++) {
                        stringNumOne += expression.charAt(i);
                    }
                    int intNumOne = Integer.parseInt(stringNumOne);

                    // Делаем второе число
                    String stringNumTwo = "";
                    for (int i = indexOfOperation; i < expression.length() ; i++) {
                        stringNumTwo += expression.charAt(i);
                    }
                    int intNumTwo = Integer.parseInt(stringNumTwo);
                    int answer = intNumOne + intNumTwo;
                    textView.setText(String.valueOf(answer));

                } else textView.setText(line);
            }
        });
    }

}