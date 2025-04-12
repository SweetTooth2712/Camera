import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Lesson extends OpenCvPipeline { // Наследуем класс OpenCvPipeline

    Mat HSV = new Mat();
    Mat END = new Mat(); // Здесь мы указываем "матрицы"
    // Матрица — поток в памяти камеры. Например, Mat input — поток входного изображения, который посылается на камеру.  

    Scalar HighColor = new Scalar(25, 255, 255); // Указываем скалярные значения для обработки цвет
    Scalar LowColor = new Scalar(10, 100, 100);

    int pixels; // Обозначаем переменную, которая будет хранить значение о количестве пикселей, входящих в вышеуказанный диапазон

    Telemetry telemetry; // Подключаем класс с телеиетрией, благодаря нему мы сможем видеть количество пикселей нужного цвета

    public Lesson(Telemetry telemetry) { // Прописываем конструктор 
        this.telemetry = telemetry;
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, HSV, Imgproc.COLOR_RGB2HSV); // Эта строчка переводит входной поток в матрицу HSV, меняя с RGB в HSV
        Core.inRange(HSV, LowColor, HighColor, END); // А здесь уже происходит вся магия. Матрица END становится черно-белой (буквально черный и белый)

        for (int i = 0; i < input.width(); i++) { // Этот цикл позволяет проанализировать каждый пиксель матрицы END 
            for (int j = 0; j < input.height(); j++) {
                if (END.get(j, i)[0] == 255) {
                    pixels++; // В итоге кол-во пикселей, входящих в диапазон, записывается сюда 
                }
            }
        }
        
        telemetry.addData("Pixels", pixels); // Непосредтвенно вывод и последующие обнуление, чтобы анализировался каждый кадр, а не весь поток
        telemetry.update();
        pixels = 0;

        return END;
    }
}
