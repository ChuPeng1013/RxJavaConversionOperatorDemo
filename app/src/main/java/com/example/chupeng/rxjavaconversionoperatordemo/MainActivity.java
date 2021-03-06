package com.example.chupeng.rxjavaconversionoperatordemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

public class MainActivity extends AppCompatActivity
{
    private Button mapButton;
    private Button flatMapButton;
    private Button flatMapIterableButton;
    private Button scanButton;
    private Button groupByButton;
    private List<Student> studentList;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化数据
        studentList = Information();
        mapButton = (Button) findViewById(R.id.mapButton);
        flatMapButton = (Button) findViewById(R.id.flatMapButton);
        flatMapIterableButton = (Button) findViewById(R.id.flatMapIterableButton);
        scanButton = (Button) findViewById(R.id.scanButton);
        groupByButton = (Button) findViewById(R.id.groupByButton);

        mapButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.just(1, 2, 3, 4, 5)
                        .map(new Func1<Integer, String>()
                        {
                            public String call(Integer integer)
                            {
                                return "This is" + integer;
                            }
                        })
                        .subscribe(new Action1<String>()
                        {
                            public void call(String s)
                            {
                                Log.d("map", s);
                            }
                        });
            }
        });

        flatMapButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                /*Observable.from(studentList)
                        .map(new Func1<Student, List<Integer>>()
                        {
                            public List<Integer> call(Student student)
                            {
                                return student.getScoreList();
                            }
                        })
                        .subscribe(new Action1<List<Integer>>()
                        {
                            public void call(List<Integer> integers)
                            {
                                for(int i = 0; i < integers.size(); i++)
                                {
                                    Log.d("flatMap", "分数为：" + integers.get(i));
                                }
                            }
                        });*/

                Observable.from(studentList)
                        .flatMap(new Func1<Student, Observable<Integer>>()
                        {
                            public Observable<Integer> call(Student student)
                            {
                                return Observable.from(student.getScoreList());
                            }
                        })
                        .subscribe(new Action1<Integer>()
                        {
                            public void call(Integer integer)
                            {
                                Log.d("flatMap", "分数为：" + integer);
                            }
                        });
            }
        });

        flatMapIterableButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.from(studentList)
                        .flatMapIterable(new Func1<Student, Iterable<Integer>>()
                        {
                            public Iterable<Integer> call(Student student)
                            {
                                return student.getScoreList();
                            }
                        })
                        .subscribe(new Action1<Integer>()
                        {
                            public void call(Integer integer)
                            {
                                Log.d("flatMapIterable", "分数为：" + integer);
                            }
                        });
            }
        });

        scanButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                        .scan(new Func2<Integer, Integer, Integer>()
                        {
                            public Integer call(Integer integer, Integer integer2)
                            {
                                return integer + integer2;
                            }
                        })
                        .subscribe(new Action1<Integer>()
                        {
                            public void call(Integer integer)
                            {
                                Log.d("scan", String.valueOf(integer));
                            }
                        });
            }
        });

        groupByButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

                Observable<GroupedObservable<String, Student>> groupByItem = Observable.from(studentList)
                        .groupBy(new Func1<Student, String>()
                        {
                            public String call(Student student)
                            {
                                return student.getGender();
                            }
                        });
                Observable.concat(groupByItem)
                        .subscribe(new Action1<Student>()
                        {
                            public void call(Student student)
                            {
                                Log.d("groupBy", "学号" + student.getNumber()
                                        + "  姓名" + student.getName()
                                        + "  性别" + student.getGender());
                            }
                        });
            }
        });
    }

    //初始化数据
    private List<Student> Information()
    {
        List<Student> informationList = new ArrayList<Student>();
        List<Integer> courseScoreList1 = new ArrayList<Integer>();
        Student student1 = new Student();
        student1.setNumber("A001");
        student1.setName("小红");
        student1.setGender("女");
        courseScoreList1.add(87);//语文
        courseScoreList1.add(90);//数学
        courseScoreList1.add(90);//英语
        student1.setScoreList(courseScoreList1);
        informationList.add(student1);

        List<Integer> courseScoreList2 = new ArrayList<Integer>();
        Student student2 = new Student();
        student2.setNumber("A002");
        student2.setName("小明");
        student2.setGender("男");
        courseScoreList2.add(65);
        courseScoreList2.add(74);
        courseScoreList2.add(88);
        student2.setScoreList(courseScoreList2);
        informationList.add(student2);

        List<Integer> courseScoreList3 = new ArrayList<Integer>();
        Student student3 = new Student();
        student3.setNumber("A003");
        student3.setName("小强");
        student3.setGender("男");
        courseScoreList3.add(54);
        courseScoreList3.add(75);
        courseScoreList3.add(92);
        student3.setScoreList(courseScoreList3);
        informationList.add(student3);

        List<Integer> courseScoreList4 = new ArrayList<Integer>();
        Student student4 = new Student();
        student4.setNumber("A004");
        student4.setName("小亨");
        student4.setGender("男");
        courseScoreList4.add(75);
        courseScoreList4.add(49);
        courseScoreList4.add(64);
        student4.setScoreList(courseScoreList4);
        informationList.add(student4);

        List<Integer> courseScoreList5 = new ArrayList<Integer>();
        Student student5 = new Student();
        student5.setNumber("A005");
        student5.setName("小琴");
        student5.setGender("女");
        courseScoreList5.add(58);
        courseScoreList5.add(75);
        courseScoreList5.add(64);
        student5.setScoreList(courseScoreList5);
        informationList.add(student5);

        List<Integer> courseScoreList6 = new ArrayList<Integer>();
        Student student6 = new Student();
        student6.setNumber("A006");
        student6.setName("小东");
        student6.setGender("男");
        courseScoreList6.add(70);
        courseScoreList6.add(68);
        courseScoreList6.add(66);
        student6.setScoreList(courseScoreList6);
        informationList.add(student6);

        List<Integer> courseScoreList7 = new ArrayList<Integer>();
        Student student7 = new Student();
        student7.setNumber("A007");
        student7.setName("小妮");
        student7.setGender("女");
        courseScoreList7.add(43);
        courseScoreList7.add(88);
        courseScoreList7.add(56);
        student7.setScoreList(courseScoreList7);
        informationList.add(student7);

        List<Integer> courseScoreList8 = new ArrayList<Integer>();
        Student student8 = new Student();
        student8.setNumber("A008");
        student8.setName("小晶");
        student8.setGender("女");
        courseScoreList8.add(68);
        courseScoreList8.add(93);
        courseScoreList8.add(77);
        student8.setScoreList(courseScoreList8);
        informationList.add(student8);

        List<Integer> courseScoreList9 = new ArrayList<Integer>();
        Student student9 = new Student();
        student9.setNumber("A009");
        student9.setName("小倩");
        student9.setGender("女");
        courseScoreList9.add(88);
        courseScoreList9.add(79);
        courseScoreList9.add(98);
        student9.setScoreList(courseScoreList9);
        informationList.add(student9);

        List<Integer> courseScoreList10 = new ArrayList<Integer>();
        Student student10 = new Student();
        student10.setNumber("A010");
        student10.setName("小鹏");
        student10.setGender("男");
        courseScoreList10.add(99);
        courseScoreList10.add(89);
        courseScoreList10.add(86);
        student10.setScoreList(courseScoreList10);
        informationList.add(student10);
        return informationList;
    }
}