package org.firstinspires.ftc.teamcode.shooters;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
@TeleOp
public class ShooterExampleCode extends OpMode {

    private DcMotor motorL, motorR;


    @Override
    public void init() {
        motorL = hardwareMap.get(DcMotor.class, "motorL");
        motorR = hardwareMap.get(DcMotor.class, "motorR");

        motorL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        motorR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorL.setDirection(DcMotor.Direction.REVERSE);
    }

    double speed = 0;

    @Override
    public void loop() {

        if (gamepad1.right_trigger > 0.05) {
            motorL.setPower(speed);
            motorR.setPower(speed);
        } else {
            motorL.setPower(0);
            motorR.setPower(0);
        }
        if (gamepad1.dpadUpWasPressed()) {
            speed += 0.05;
        }
        if (gamepad1.dpadDownWasPressed()) {
            speed -= 0.05;
        }
    }
}
