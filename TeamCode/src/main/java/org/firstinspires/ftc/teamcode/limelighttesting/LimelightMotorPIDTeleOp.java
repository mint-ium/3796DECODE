package org.firstinspires.ftc.teamcode.limelighttesting;  // Adjust to your package

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.List;

@TeleOp(name = "Limelight Motor PID TeleOp", group = "TeleOp")
public class LimelightMotorPIDTeleOp extends LinearOpMode {
    // Hardware
    private DcMotor motorTurn;  // The motor you're controlling (e.g., turret motor)
    private Limelight3A limelight;  // Limelight instance


    //TODO PID Constants - TUNE THESE ON ROBOT
    private static final double kP = 0.045;  // Proportional gain
    private static final double kI = 0.15;   // Integral gain
    private static final double kD = 0.0; // Derivative gain
    private static final double DEADZONE = 1.0;  // Degrees; ignore small errors

    //TODO Motor Encoder Setup - CALIBRATE FOR MOTOR/GEARBOX
    private static final double TICKS_PER_REV = 2150.8;  // 537.7 PPR * 4
    private static final double GEAR_RATIO = 19.2;       // Gobilda 312RPM Yellowjacket
    private static final double DEGREES_PER_TICK = 360.0 / (TICKS_PER_REV * GEAR_RATIO);

    // PID Variables
    private double integral = 0;
    private double previousError = 0;
    private long lastTime = 0;

    @Override
    public void runOpMode() {
        // Initialize hardware
        motorTurn = hardwareMap.get(DcMotor.class, "motorTurn");  // NAME FOR TURNTABLE MOTOR
        motorTurn.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorTurn.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorTurn.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Initialize Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");  // LIMELIGHT NAME

        waitForStart();
        lastTime = System.nanoTime();

        while (opModeIsActive()) {
            // Call the PID update method
            updateMotorPID();

            // Add other TeleOp controls here if needed
            telemetry.update();
        }
    }

    /**
     * Updates the motor using PID based on Limelight tx.
     */
    private void updateMotorPID() {
            // Get current motor angle from encoder
            double currentAngle = motorTurn.getCurrentPosition() * DEGREES_PER_TICK;

            // Get target angle from Limelight tx
            double targetAngle = getLimelightTx();  // Uses llResult.getTx()

            // Compute shortest angle error
            double rawError = targetAngle - currentAngle;
            double angleError = Math.atan2(Math.sin(Math.toRadians(rawError)), Math.cos(Math.toRadians(rawError))) * 180.0 / Math.PI;

            // Apply deadzone
            if (Math.abs(angleError) < DEADZONE) {
                motorTurn.setPower(0);
                integral = 0;
                telemetry.addData("Status", "In Deadzone - Motor Stopped");
                return;
            }

            // PID Calculation
            long currentTime = System.nanoTime();
            double dt = (currentTime - lastTime) / 1e9;
            lastTime = currentTime;

            integral += angleError * dt;
            double derivative = (angleError - previousError) / dt;
            double output = (kP * angleError) + (kI * integral) + (kD * derivative);
            previousError = angleError;

            // Clamp output
            output = Math.max(-1, Math.min(1, output));

            // Set motor power
            motorTurn.setPower(output);

            // Telemetry
            telemetry.addData("Target Angle (tx)", targetAngle);
            telemetry.addData("Current Angle", currentAngle);
            telemetry.addData("Angle Error", angleError);
            telemetry.addData("PID Output", output);
        }
    /**
     * Fetches Limelight's tx using llResult.getTx().
     * Returns 0 if no valid target.
     */
    private double getLimelightTx() {
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> fiducialResults = llResult.getFiducialResults();
        if (llResult != null && llResult.isValid()) {
            double tx = llResult.getTx();  // Horizontal angle offset in degrees
            telemetry.addData("Limelight tx", tx);
            return tx;
        } else {
            telemetry.addData("Limelight", "No Valid Target");
            return 0.0;  // Default or last known angle
        }
    }
}
