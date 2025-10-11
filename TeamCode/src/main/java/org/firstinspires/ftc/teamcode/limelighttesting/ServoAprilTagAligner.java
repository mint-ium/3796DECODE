package org.firstinspires.ftc.teamcode.limelighttesting;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;



@TeleOp(name = "Limelight 3A Servo Aligner", group = "Vision")
public class ServoAprilTagAligner extends LinearOpMode {

    private Servo cameraServo;
    private Limelight3A limelight;

    // Constants
    private static final double TX_RANGE = 54.5; // (-27.25 .. +27.25)
    private static final double KP = 0.5;
    private static final double SERVO_MIN = 0.0;
    private static final double SERVO_MAX = 1.0;
    private static final long UPDATE_INTERVAL_MS = 1000; // 1-second update rate

    @Override
    public void runOpMode() {
        // Initialize servo
        cameraServo = hardwareMap.get(Servo.class, "servoPos");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        // Create Limelight instance using the private Limelight interface
        // name as configured in Robot Configuration

        double servoPosition = 0.5; // start centered
        cameraServo.setPosition(servoPosition);

        telemetry.addLine("Limelight 3A Servo Aligner Ready");
        telemetry.addLine("Waiting for start...");
        telemetry.update();

        waitForStart();

        long lastUpdate = 0;

        while (opModeIsActive()) {

            // Get latest Limelight data
            LLResult results = limelight.getLatestResult();

            // If the Limelight has a valid target
            if (results != null && results.isValid()) {
                double tx = results.getTx(); // horizontal offset (degrees)
                double tv = results.isValid() ? 1.0 : 0.0;

                long now = System.currentTimeMillis();

                // Update servo position only once per second
                if (tv == 1.0 && now - lastUpdate >= UPDATE_INTERVAL_MS) {
                    servoPosition = adjustServoToCenter(tx, servoPosition);
                    cameraServo.setPosition(servoPosition);
                    lastUpdate = now;
                }

                telemetry.addData("tx (deg)", tx);
                telemetry.addData("tv", tv);
                telemetry.addData("Servo Pos", servoPosition);
                telemetry.addData("Servo Angle (deg)", servoToDegrees(servoPosition));
            } else {
                telemetry.addLine("No valid Limelight target");
            }

            telemetry.update();
            sleep(50);
        }
    }

    private double adjustServoToCenter(double tx, double servoCurrent) {
        double deltaServo = -KP * (tx / TX_RANGE);
        double newServo = servoCurrent + deltaServo;
        return clamp(newServo, SERVO_MIN, SERVO_MAX);
    }

    private double servoToDegrees(double servoValue) {
        return servoValue * 300.0;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}