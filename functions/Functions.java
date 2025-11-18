package functions;

import functions.meta.*;

//вне класса - нельзя создавать объекты
public class Functions {
    //Конструктор(запрещаем создание объектов)
    private Functions() {
        throw new AssertionError("Нельзя создавать объекты класса Functions");
    }
    //возвращает объект ф-ии, полученной из исходной сдвигом вдоль осей
    public static Function shift(Function f, double shiftX, double shiftY) {
        return new Shift(f, shiftX, shiftY);
    }
    //возвращает объект ф-ии, полученной из исходной масштабированием вдоль осей
    public static Function scale(Function f, double scaleX, double scaleY) {
        return new Scale(f, scaleX, scaleY);
    }
    //возвращает объект ф-ии, являющейся заданной степенью исходной
    public static Function power(Function f, double power) {
        return new Power(f, power);
    }
    //возвращает объект ф-ии, являющейся суммой двух исходных
    public static Function sum(Function f1, Function f2) {
        return new Sum(f1, f2);
    }
    //возвращает объект ф-ии, являющейся произведением двух исходных
    public static Function mult(Function f1, Function f2) {
        return new Mult(f1, f2);
    }
    //возвращает объект ф-ии, являющейся композицией двух исходных
    public static Function composition(Function f1, Function f2) {
        return new Composition(f1, f2);
    }
}