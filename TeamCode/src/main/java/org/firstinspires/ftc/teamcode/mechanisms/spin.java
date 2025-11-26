package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class spin extends OpMode {
    private DcMotor motorTurn;

    @Override
    public void init() {
        motorTurn = hardwareMap.get(DcMotor.class, "motorTurn");
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger > 0.05){
            motorTurn.setPower(.5);
        }else if(gamepad1.left_trigger > 0.05){
            motorTurn.setPower(-.5);
        }else{
            motorTurn.setPower(0);
        }
    }
}
