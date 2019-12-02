package com.fitpolo.support.entity;

/**
 * @Date 2017/5/14 0014
 * @Author wenzheng.liu
 * @Description Información personal
 * @ClassPath com.fitpolo.support.entity.UserInfo
 */
public class UserInfo {
    public int weight;// Peso
    public int height;// Altura
    public int age;// Edad
    public int birthdayMonth;// Mes de nacimiento
    public int birthdayDay;// Cumpleaños
    public int gender;// Sexo Masculino: 0; Femenino: 1
    public int stepExtent;// Zancada

    @Override
    public String toString() {
        return "UserInfo{" +
                "weight=" + weight +
                ", height=" + height +
                ", age=" + age +
                ", birthdayMonth=" + birthdayMonth +
                ", birthdayDay=" + birthdayDay +
                ", gender=" + gender +
                ", stepExtent=" + stepExtent +
                '}';
    }
}
