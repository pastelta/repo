package ru.course.task.three;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DemoTest {
    public static ByteArrayOutputStream baos;
    public static PrintStream ps;

    @BeforeAll
    public static void preparing() {
        baos = new ByteArrayOutputStream();
        ps = new PrintStream(baos);
        System.setOut(ps);
    }

    @Test
    public void demoSizeTest() throws InterruptedException {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);
        num.doubleValue(); //sout - invoke double value
        num.doubleValue(); //sout is silent

        num.setNum(5);
        num.doubleValue(); //sout - invoke double value
        num.doubleValue(); //sout is silent

        num.setNum(2);
        num.doubleValue(); //sout is silent
        num.doubleValue(); //sout is silent

        Thread.sleep(1500);

        num.doubleValue(); //sout - invoke double value
        num.doubleValue(); //sout is silent
        num.doubleValue(); //sout is silent
        num.doubleValue(); //sout is silent

        Long size = (long) baos.size();

        Assertions.assertEquals(63, size);
    }
}