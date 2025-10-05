package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MotorCode{

    private DcMotor motor;

    public void init(HardwareMap hwMap){
        motor = hwMap.get(DcMotor.class, "motorGo");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void setMotorSpeed(double speed){
        //accepts -1.0 to 1.0
        motor.setPower(speed);
    }
}
