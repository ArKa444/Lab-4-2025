package functions;

public class FunctionPoint {
    private double x;  //коорд. x точки
    private double y;  //коорд. y точки
    private static final double EPSILON = 1e-10;
    
    //конструктор1(создаем точку с заданными коорд. x и y)
    public FunctionPoint(double x, double y) {
        this.x = x;    //сохраняем переданный x
        this.y = y;    //сохраняем переданный y
    }
    
    //конструктор2(создаем копию сущ. точки)
    public FunctionPoint(FunctionPoint point) {
        this.x = point.x;  //берем x из исходной точки
        this.y = point.y;  //берем y из исходной точки
    }
    
    //конструктор3(создаем точку в начале координат (0,0))
    public FunctionPoint() {
        this.x = 0.0;  
        this.y = 0.0;  
    }
    
    //ГЕТТЕРЫ(методы для получения значений)
    //получаем значение x
    public double getX() {
        return x;  //возвращаем коорд. x
    }
    
    //получаем значение y
    public double getY() {
        return y;  //возвращаем коорд. y
    }
    
    //СЕТТЕРЫ(методы для установки значений)
    
    //устанавливаем новое значение x
    public void setX(double x) {
        this.x = x;  //меняем коорд. x
    }
    
    //устанавливаем новое значение y
    public void setY(double y) {
        this.y = y;  //меняем коорд. y
    }
}