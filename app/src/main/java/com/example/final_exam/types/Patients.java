package com.example.final_exam.types;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.ThreadLocalRandom;

public class Patients {
    String name, allergy, problem, dob;
    private String id;
    private String age;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Patients(){
        this.setId("", "");
        this.name = "";
        this.setAge("");
        this.allergy = "";
        this.problem = "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Patients(String name, String MM_DD_YYYY, String allergy, String problem){
        this.setId(name, MM_DD_YYYY);
        this.name = name;
        this.setAge(fromDobToAge(MM_DD_YYYY));
        this.dob = MM_DD_YYYY;
        this.allergy = allergy;
        this.problem = problem;
    }

    private static String idGenerator(String name, String dob){
        String id = "";
        String month, day, year;
        String alphanum = "0ABC1DEF2GHI3JKL4MNO5PQR6STU7VWX8YZ9";

        String name_by_parts[] = nameByParts(name);
        String dob_by_parts[] = dateByParts(dob);

        month = dob_by_parts[0];
        day = dob_by_parts[1];
        year = dob_by_parts[2];

        if(name_by_parts.length == 3){
            id += name_by_parts[1].charAt(0)+
                    name_by_parts[1].charAt(1)+
                    name_by_parts[2].charAt(0);
        } else if(name_by_parts.length == 4){
            id += name_by_parts[2].charAt(0)+
                    name_by_parts[3].charAt(1)+
                    name_by_parts[3].charAt(0);
        }

        id += year.charAt(2) + year.charAt(3);

        if((month.length() == 1) && (Integer.parseInt(month) < 10)){
            id += "0" + month;
        } else {
            id += month;
        }

        if((day.length() == 1) && (Integer.parseInt(day) < 10)){
            id += "0" + day;
        } else {
            id += day;
        }

        for(int i=0; i<3; i++){
            int randomNum = ThreadLocalRandom.current().nextInt(0, alphanum.length());
            id += alphanum.charAt(randomNum)+"";
        }

        return id;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static String fromDobToAge(String dob){
        int day_of_birth, month_of_birth, year_of_birth;

        String dob_by_parts[] = dateByParts(dob);

        month_of_birth = Integer.parseInt(dob_by_parts[0]);
        day_of_birth = Integer.parseInt(dob_by_parts[1]);
        year_of_birth = Integer.parseInt(dob_by_parts[2]);


        LocalDate now = LocalDate.now();
        LocalDate birthday = LocalDate.of(year_of_birth,
                                            month_of_birth,
                                            day_of_birth);

        Period p = Period.between(birthday, now);

        return p.getYears()+"";
    }

    private static String[] dateByParts(String dob){
        return dob.trim().split("/");
    }

    private static String[] nameByParts(String name){
        return name.trim().split("\\+s");
    }

    public String getAge() {
        return age;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setAge(String dob) {
        if(dob.equals("")) this.age = "";
        this.age = fromDobToAge(dob);
    }

    public String getId() {
        return id;
    }

    public void setId(String name, String dob) {
        if(name.equals("") || dob.equals("")) this.age = "";
        this.age = idGenerator(name, dob);
    }
}
