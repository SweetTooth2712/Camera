package org.firstinspires.ftc.teamcode.pip;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Mat_Detector extends OpenCvPipeline {
    Mat hsv = new Mat();  //create hsv mat
    Mat mask1, mask2 = new Mat();  //create masks
    Mat end = new Mat();   //create output mat

    String color = "green";  // create the color string

    Scalar scalarLow = new Scalar(90, 100, 100);
    Scalar scalarHigh = new Scalar(140, 255, 255);

    Telemetry telemetry;

    public Mat_Detector(Telemetry telemetry) {
        this.telemetry = telemetry;
    }


    @Override
    public Mat processFrame(Mat input) {
        Scalar scalarLow, scalarHigh; //create scalars for high and low values

        if (color == "yellow") {
            scalarLow = new Scalar(20, 100, 100);
            scalarHigh = new Scalar(30, 255, 255);
        } else if (color == "blue") {
            scalarLow = new Scalar(90, 100, 100);
            scalarHigh = new Scalar(140, 255, 255);
        } else if (color == "green") {
            scalarLow = new Scalar(40, 100, 100);
            scalarHigh = new Scalar(75, 255, 255);
        } else {
            scalarLow = new Scalar(0, 0, 0);
            scalarHigh = new Scalar(0, 0, 0);
        }
        Imgproc.cvtColor(input, hsv, Imgproc.COLOR_RGB2HSV);//change to hsv

        if (!color.equals("red")) {
            Core.inRange(hsv, scalarLow, scalarHigh, end);//detect color, output to end

            telemetry.addLine("Color is " + color);
            telemetry.update();
        }
        if (color == "red") {
            Core.inRange(hsv, new Scalar(0, 70, 50), new Scalar(8, 255, 255), mask1);
            Core.inRange(hsv, new Scalar(172, 70, 50), new Scalar(180, 255, 255), mask2);
            Core.bitwise_or(mask1, mask2, end);//takes both masks and combines them
        }
        return end;
    }
}
