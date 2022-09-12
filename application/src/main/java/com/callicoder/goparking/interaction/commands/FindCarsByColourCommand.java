package com.callicoder.goparking.interaction.commands;


import com.callicoder.goparking.handler.ParkingLotCommandHandler;
import com.callicoder.goparking.exceptions.InvalidParameterException;
import com.callicoder.goparking.utils.StringUtils;

public class FindCarsByColourCommand implements Command {
    private ParkingLotCommandHandler parkingLotCommandHandler;

    public FindCarsByColourCommand(ParkingLotCommandHandler parkingLotCommandHandler) {
        this.parkingLotCommandHandler = parkingLotCommandHandler;
    }

    @Override
    public String helpText() {
       return  "find_registration_numbers_by colour <colour>";
    }

    @Override
    public void execute(String[] params) throws InvalidParameterException {
        if(params.length < 1) {
            throw new InvalidParameterException("Expected one parameter <colour>");
        }

        /*if(!StringUtils.isInteger(params[0])) {
            throw new InvalidParameterException("colour must be an integer");
        }*/

        String colour = params[0];
        this.parkingLotCommandHandler.findCarsByColour(colour);
    }
}
