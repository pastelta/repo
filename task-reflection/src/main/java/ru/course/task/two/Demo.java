package ru.course.task.two;

public class Demo {
    public static void main(String[] args) {

        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        num.doubleValue(); //sout - invoke double value
        num.doubleValue(); //sout is silent
        num.doubleValue(); //sout is silent
        num.setNum(5);
        num.doubleValue(); //sout - invoke double value
        num.doubleValue(); //sout is silent
        num.setDenum(6);
        num.doubleValue(); //sout - invoke double value
        num.doubleValue(); //sout is silent
        num.doubleValue(); //sout is silent
    }
}
