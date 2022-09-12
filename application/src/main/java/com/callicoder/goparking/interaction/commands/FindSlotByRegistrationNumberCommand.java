package com.callicoder.goparking.interaction.commands;


import com.callicoder.goparking.handler.ParkingLotCommandHandler;
import com.callicoder.goparking.exceptions.InvalidParameterException;
import com.callicoder.goparking.utils.StringUtils;

public class FindSlotByRegistrationNumberCommand implements Command {
    private ParkingLotCommandHandler parkingLotCommandHandler;

    public FindSlotByRegistrationNumberCommand(ParkingLotCommandHandler parkingLotCommandHandler) {
        this.parkingLotCommandHandler = parkingLotCommandHandler;
    }

    @Override
    public String helpText() {
       return  "slot_number_for_registration_number <registrationNumber>";
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if(params.length < 1) {
            throw new InvalidParameterException("Expected one parameter <registrationNumber>");
        }

        /*if(!StringUtils.isInteger(params[0])) {
            throw new InvalidParameterException("colour must be an integer");
        }*/

        String registrationNumber = params[0];
        this.parkingLotCommandHandler.findSlotByRegistrationNumber(registrationNumber);
    }
}
