package ru.course.task.three;

import java.util.Objects;

public class StateFraction {
    private int num;
    private int denum;
    private long startTime;

    public StateFraction(int num, int denum, long startTime) {
        this.num = num;
        this.denum = denum;
        this.startTime = startTime;
    }
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDenum() {
        return denum;
    }

    public void setDenum(int denum) {
        this.denum = denum;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        final StateFraction other = (StateFraction) obj;
        return Objects.equals(this.num, other.num)
                && Objects.equals(this.denum, other.denum);
    }

    @Override
    public String toString() {
        return "StateFraction{" +
                "num=" + num +
                ", denum=" + denum +
                ", startTime=" + startTime +
                '}';
    }
}
