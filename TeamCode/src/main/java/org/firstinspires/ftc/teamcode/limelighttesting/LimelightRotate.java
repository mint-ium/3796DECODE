package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@TeleOp
public class LimelightRotate extends OpMode {

    private Servo turretServo;
    private Limelight3A limelight;

    private static final double minServo = 0.3;
    private static final double maxServo = 0.5;

    private static final double maxStep = 0.001; // max servo movement per loop

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        turretServo = hardwareMap.get(Servo.class, "servoPos");
        turretServo.setPosition(0.3); // start centered
    }

    @Override
    public void start() {
        limelight.start();
    }

    @Override
    public void loop() {
        LLResult llResult = limelight.getLatestResult();

        if (llResult != null && llResult.isValid()) {
            double tx = llResult.getTx();
            telemetry.addData("Tx", tx);

            // Map TX (-27.25 to 27.25) to servo target position (0 to 0.6, opposite direction)
            double targetPos = 0.3 - (tx / 27.25) * 0.3;
            targetPos = Range.clip(targetPos, minServo, maxServo);

            // Current servo position
            double currentPos = turretServo.getPosition();

            // Move incrementally toward target
            if (Math.abs(targetPos - currentPos) > maxStep) {
                if (targetPos > currentPos) {
                    currentPos += maxStep;
                } else {
                    currentPos -= maxStep;
                }
            } else {
                currentPos = targetPos; // small difference, move directly
            }

            turretServo.setPosition(currentPos);
        }
    }
}