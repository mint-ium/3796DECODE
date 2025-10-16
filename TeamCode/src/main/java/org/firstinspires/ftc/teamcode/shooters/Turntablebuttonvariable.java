package org.firstinspires.ftc.teamcode.shooters;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp
public class Turntablebuttonvariable extends OpMode {
    private DcMotor motor;
    double velocity;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motorTurn");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        velocity = 0;
    }

    @Override
    public void loop() {
        telemetry.addData("Velocity", velocity);

        if(gamepad1.left_trigger > 0.05){
            velocity = velocity * -1.0;
            motor.setPower(velocity);

        }else if(gamepad1.right_trigger > 0.05){
            velocity = velocity * 1.0;
            motor.setPower(velocity);
        }else{
            motor.setPower(0);
        }

        if(gamepad1.aWasPressed()){
            velocity += 0.1;
        }

        if(gamepad1.bWasPressed()){
            velocity += -0.1;
        }
    }
}
