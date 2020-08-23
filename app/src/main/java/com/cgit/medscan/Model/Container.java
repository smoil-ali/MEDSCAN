package com.cgit.medscan.Model;

public class Container {
   private static Form1ModelClass form1ModelClass = new Form1ModelClass();
   private static Form2ModelClass form2ModelClass = new Form2ModelClass();

    public static Form1ModelClass getForm1ModelClass() {
        return form1ModelClass;
    }

    public static void setForm1ModelClass(Form1ModelClass form1ModelClass) {
        Container.form1ModelClass = form1ModelClass;
    }

    public static Form2ModelClass getForm2ModelClass() {
        return form2ModelClass;
    }

    public static void setForm2ModelClass(Form2ModelClass form2ModelClass) {
        Container.form2ModelClass = form2ModelClass;
    }
}
