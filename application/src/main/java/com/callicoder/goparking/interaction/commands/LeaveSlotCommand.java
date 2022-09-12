package com.callicoder.goparking.interaction.commands;


import com.callicoder.goparking.handler.ParkingLotCommandHandler;
import com.callicoder.goparking.exceptions.InvalidParameterException;
import com.callicoder.goparking.utils.StringUtils;

public class LeaveSlotCommand implements Command {
    private ParkingLotCommandHandler parkingLotCommandHandler;

    public LeaveSlotCommand(ParkingLotCommandHandler parkingLotCommandHandler) {
        this.parkingLotCommandHandler = parkingLotCommandHandler;
    }

    @Override
    public String helpText() {
       return  "leave_slot <slotNumber>";
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if(params.length < 1) {
            throw new InvalidParameterException("Expected one parameter <slotNumber>");
        }

        if(!StringUtils.isInteger(params[0])) {
            throw new InvalidParameterException("slotNumber must be an integer");
        }

        int slotNumber = Integer.parseInt(params[0]);
        this.parkingLotCommandHandler.leaveSlot(slotNumber);
    }
}
