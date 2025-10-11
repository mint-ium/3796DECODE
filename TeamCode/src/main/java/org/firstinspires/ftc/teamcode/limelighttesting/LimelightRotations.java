package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class LimelightRotations extends OpMode {

    private Servo positionServo;
    private Limelight3A limelight;


    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        positionServo = hardwareMap.get(Servo.class, "servoPos");
    }

    @Override
    public void start(){
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();
        if (llResult != null) {
            if (llResult.isValid()) {
                telemetry.addData("Tx", llResult.getTx());
            }
        }
    }
}
