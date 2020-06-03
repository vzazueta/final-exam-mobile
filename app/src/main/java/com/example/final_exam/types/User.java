package com.example.final_exam.types;

import java.util.Date;

public class User {
    private String id, name, gender, allergies;
    private boolean isAllergic;
    private Date dob;


    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getAllergies() { return allergies; }

    public void setAllergies(String allergies) { this.allergies = allergies; }

    public boolean isAllergic() { return isAllergic;  }

    public void setAllergic(boolean allergic) {  isAllergic = allergic;  }

    public Date getDob() {  return dob;  }

    public void setDob(Date dob) {  this.dob = dob;  }
}
