package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class motorEncoderTracking extends OpMode {
    private DcMotor motor1;

    @Override
    public void init() {
        motor1 = hardwareMap.get(DcMotor.class , "motor1");
        motor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void start() {
        super.start();
        //Reset to 0 ticks
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    @Override
    public void loop() {
        //6000 RPM Motor Encoder has a PPR (Pulse Per Revolution) of 28 and CPR (Counts Per Revolution) = PPR*4
        double CPR = (28*4) ;

        int position = motor1.getCurrentPosition();
        double revolutions = position/CPR;

        double angle = revolutions * 360;
        double angleNormalized = angle % 360;

        telemetry.addData("Encoder Position", position);
        telemetry.addData("Encoder Revolutions", revolutions);
        telemetry.addData("Encoder Angle (Degrees)", angle);
        telemetry.addData("Revolutions: ", revolutions);
        telemetry.addData("Encoder Angle - Normalized (Degrees)", angleNormalized);


        if(gamepad1.aWasPressed()){
            motor1.setPower(0.3);
        }else{
            motor1.setPower(0);
        }
    }
}
