import functions.*;
import functions.basic.*;//(Exp, Log, Sin, Cos)
import functions.meta.*;//(Sum, Mult, Composition)
import java.io.*;//для работы с файлами и сериализации

public class Main {

    public static void main(String[] args) {
        System.out.println("ТЕСТИРОВАНИЕ\n");

        try {
            testAssignment8(); //задание 8
            testAssignment9(); //задание 9

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Тестирование задания 8
    private static void testAssignment8() throws IOException {

        //1. Sin и Cos
        System.out.println("1. Sin и Cos от 0 до π с шагом 0.1:");
        Function sin = new Sin();
        Function cos = new Cos();

        System.out.println("Sin:");
        for (double x = 0; x <= Math.PI; x += 0.1) {
            System.out.printf("x=%.1f: %.4f%n", x, sin.getFunctionValue(x));
        }

        System.out.println("\nCos:");
        for (double x = 0; x <= Math.PI; x += 0.1) {
            System.out.printf("x=%.1f: %.4f%n", x, cos.getFunctionValue(x));
        }

        //2. Табулированные аналоги
        System.out.println("\n2. Табулированные аналоги :");
        TabulatedFunction tabSin = TabulatedFunctions.tabulate(sin, 0, Math.PI, 10);
        TabulatedFunction tabCos = TabulatedFunctions.tabulate(cos, 0, Math.PI, 10);

        System.out.println("Сравнение Sin:");
        for (double x = 0; x <= Math.PI; x += 0.1) {
            double orig = sin.getFunctionValue(x);
            double tab = tabSin.getFunctionValue(x);
            System.out.printf("x=%.1f: аналитич. = %.4f; табул. = %.4f%n", x, orig, tab);
        }

        //3. Сумма квадратов
        System.out.println("\n3. ---ТЕСТИРОВАНИЕ КОМБИНАЦИЙ ФУНКЦИЙ---");
        System.out.println("Проверка тождества sin^2 (x) + cos^2 (x) = 1:");

        TabulatedFunction testSin = TabulatedFunctions.tabulate(sin, 0, Math.PI, 10);
        TabulatedFunction testCos = TabulatedFunctions.tabulate(cos, 0, Math.PI, 10);
        Function sumSquares = Functions.sum(Functions.power(testSin, 2), Functions.power(testCos, 2));

        for (double x = 0; x <= Math.PI; x += 0.1) {
            System.out.printf("sin^2 (%.1f) + cos^2 (%.1f) = %.4f%n", x, x, sumSquares.getFunctionValue(x));
        }

        //4. Экспонента - текстовый файл
        System.out.println("\n4. Экспонента - текстовый файл:");
        TabulatedFunction exp = TabulatedFunctions.tabulate(new Exp(), 0, 10, 11);

        //запись в текстовый файл
        try (FileWriter writer = new FileWriter("exp.txt")) {
            TabulatedFunctions.writeTabulatedFunction(exp, writer);
        }

        //чтение из текстового файла
        TabulatedFunction expRead;
        try (FileReader reader = new FileReader("exp.txt")) {
            expRead = TabulatedFunctions.readTabulatedFunction(reader);
        }

        System.out.println("Сравнение экспоненты:");
        for (double x = 0; x <= 10; x += 1) {
            System.out.printf("x=%.0f: исходная = %.4f; из файла = %.4f%n",
                    x, exp.getFunctionValue(x), expRead.getFunctionValue(x));
        }

        //5. Логарифм - бинарный файл
        System.out.println("\n5. Логарифм - бинарный файл:");
        TabulatedFunction log = TabulatedFunctions.tabulate(new Log(Math.E), 0.1, 10, 11);

        //запись в бинарный файл
        try (FileOutputStream out = new FileOutputStream("log.dat")) {
            TabulatedFunctions.outputTabulatedFunction(log, out);
        }

        //чтение из бинарного файла
        TabulatedFunction logRead;
        try (FileInputStream in = new FileInputStream("log.dat")) {
            logRead = TabulatedFunctions.inputTabulatedFunction(in);
        }

        System.out.println("Сравнение логарифма:");
        for (double x = 1; x <= 10; x += 1) {
            System.out.printf("x=%.0f: исходная = %.4f; из файла = %.4f%n",
                    x, log.getFunctionValue(x), logRead.getFunctionValue(x));
        }
    }

    //Тестирование задания 9
    private static void testAssignment9() throws IOException, ClassNotFoundException {
        System.out.println("\n--- ТЕСТИРОВАНИЕ СЕРИАЛИЗАЦИИ ---");

        //композиция ln(exp(x))
        System.out.println("исходные функции (log(exp(x))):");
        Function composition = Functions.composition(new Log(Math.E), new Exp());

        //создаем две табулированные функции
        TabulatedFunction arrayFunc = TabulatedFunctions.tabulate(composition, 0, 10, 11);

        //создаем LinkedListTabulatedFunction(через конструктор с массивом точек)
        FunctionPoint[] points = new FunctionPoint[11];
        for (int i = 0; i <= 10; i++) {
            points[i] = new FunctionPoint(i, i); 
        }
        TabulatedFunction listFunc = new LinkedListTabulatedFunction(points);

        //вывод исходных функций
        System.out.println("Исходные функции:");
        for (double x = 0; x <= 10; x += 1) {
            System.out.printf("x=%.1f: array=%.4f, list=%.4f%n",
                    x, arrayFunc.getFunctionValue(x), listFunc.getFunctionValue(x));
        }

        //ArrayTabulatedFunction - Serializable
        System.out.println("\n--- ArrayTabulatedFunction (Serializable) ---");
        System.out.println("сравнение Array после Serializable:");

        //сериализуем ArrayTabulatedFunction
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("array_serializable.ser"))) {
            out.writeObject(arrayFunc);
        }

        //десериализуем ArrayTabulatedFunction
        TabulatedFunction arrayRestored;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("array_serializable.ser"))) {
            arrayRestored = (TabulatedFunction) in.readObject();
        }

        System.out.println("сравнение исходной и восстановленной функции:");
        for (double x = 0; x <= 10; x += 1) {
            System.out.printf("x=%4.1f: исходная = %6.4f, полученная = %6.4f%n",
                    x, arrayFunc.getFunctionValue(x), arrayRestored.getFunctionValue(x));
        }

        //LinkedListTabulatedFunction - Externalizable
        System.out.println("\n--- LinkedListTabulatedFunction (Externalizable) ---");
        System.out.println("сравнение List после Externalizable:");

        //сериализуем LinkedListTabulatedFunction
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("list_externalizable.ser"))) {
            out.writeObject(listFunc);
        }

        //десериализуем LinkedListTabulatedFunction
        TabulatedFunction listRestored;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("list_externalizable.ser"))) {
            listRestored = (TabulatedFunction) in.readObject();
        }

        System.out.println("сравнение исходной и восстановленной функции:");
        for (double x = 0; x <= 10; x += 1) {
            System.out.printf("x=%4.1f: исходная = %6.4f, полученная = %6.4f%n",
                    x, listFunc.getFunctionValue(x), listRestored.getFunctionValue(x));
        }
    }
}