package ru.mirea.poltavets.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String line = "";

    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        TextView textView = view.findViewById(R.id.answer);

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

        return view;
    }

    Button button;
    private void showline(View view, TextView textView, int number) {
        // калькулятор для 2-ух чисел. Формат ввода число-знак-число
        button = view.findViewById(number);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Button clickedBtn = (Button)v;
                String clickedBtnStr = clickedBtn.getText().toString();
                line += clickedBtnStr;
                if (clickedBtnStr.equals("C")) {line = "";}
                if (clickedBtnStr.equals("=")) {
                    // Созданим переменную, в которой будем хранить введённое значение
                    String expression = (String) textView.getText();

                    // Создадим 2 переменные. В opList будем хранить список операций
                    // В num будем хранить число в виде строки
                    ArrayList<Character> opList = new ArrayList<>();
                    ArrayList<Integer> numList = new ArrayList<>();
                    String num = "";

                    // В цикле будем разбирать наше выржение до тех пор, пока не найдём операцию '-', '+', '*' или '/'
                    for (int i = 0; i < expression.length(); i++) {
                        char value = (char) expression.charAt(i);
                        if (value != '+' && value != '*' && value != '-' && value != '/'){
                            num += value;
                        } else {
                            // Заносим операцию в список операций
                            opList.add(value);
                            // Заносим получившееся число в список numList
                            int n = Integer.parseInt(num);
                            numList.add(n);
                            // Обнуляем num
                            num = "";
                        }
                    }

                    int sizeOfNumList = numList.size();
                    int countInNumList = 0;
                    int answer = 0;

                    for (int j = 0; j < opList.size(); j++) {
                        char value = opList.get(j);
                        switch (value){
                            case '+':
                                if ((countInNumList + 1 <= sizeOfNumList) &&(answer == 0)) {
                                    answer = numList.get(countInNumList) + numList.get(countInNumList + 1);
                                    countInNumList += 2;
                                } else {
                                    if (countInNumList + 1 <= sizeOfNumList) {
                                        answer += numList.get(countInNumList);
                                    }
                                }
                                break;

                            case '*':
                                if ((countInNumList + 1 <= sizeOfNumList) &&(answer == 0)) {
                                    answer = numList.get(countInNumList) * numList.get(countInNumList + 1);
                                    countInNumList += 2;
                                } else {
                                    if (countInNumList + 1 <= sizeOfNumList) {
                                        answer *= numList.get(countInNumList);
                                    }
                                }
                                break;

                            case '-':
                                if ((countInNumList + 1 <= sizeOfNumList) &&(answer == 0)) {
                                    answer = numList.get(countInNumList) - numList.get(countInNumList + 1);
                                    countInNumList += 2;
                                } else {
                                    if (countInNumList + 1 <= sizeOfNumList) {
                                        answer -= numList.get(countInNumList);
                                    }
                                }
                                break;

                            case '/':
                                if ((countInNumList + 1 <= sizeOfNumList) &&(answer == 0)) {
                                    answer = numList.get(countInNumList) / numList.get(countInNumList + 1);
                                    countInNumList += 2;
                                } else {
                                    if (countInNumList + 1 <= sizeOfNumList) {
                                        answer /= numList.get(countInNumList);
                                    }
                                }
                                break;

                            default:
                                return;
                        }
                        textView.setText(answer);
                        line = "";
                    }
                } else textView.setText(line);
            }
        });
    }
}