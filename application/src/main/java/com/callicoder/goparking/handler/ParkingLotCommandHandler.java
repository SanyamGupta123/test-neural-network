package com.callicoder.goparking.handler;

import com.callicoder.goparking.exceptions.ParkingLotFullException;
import com.callicoder.goparking.exceptions.SlotNotFoundException;
import com.callicoder.goparking.domain.Car;
import com.callicoder.goparking.domain.ParkingLot;
import com.callicoder.goparking.domain.ParkingSlot;
import com.callicoder.goparking.domain.Ticket;
import com.callicoder.goparking.utils.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.callicoder.goparking.utils.MessageConstants.*;

public class ParkingLotCommandHandler {
    private ParkingLot parkingLot;

    public void createParkingLot(int numSlots) {
        if(isParkingLotCreated()) {
            System.out.println(PARKING_LOT_ALREADY_CREATED);
            return;
        }

        try {
            parkingLot = new ParkingLot(numSlots);
            System.out.println(String.format(PARKING_LOT_CREATED_MSG, parkingLot.getNumSlots()));
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        }
    }

    public void park(String registrationNumber, String color) {
        if(!isParkingLotCreated()) {
            System.out.println(PARKING_LOT_NOT_CREATED);
            return;
        }
        //TODO: VALIDATION FOR DUPLICATE VEHICLE
        try {
            Car car = new Car(registrationNumber, color);
            Ticket ticket = parkingLot.reserveSlot(car);
            System.out.println(String.format(PARKING_SLOT_ALLOCATED_MSG, ticket.getSlotNumber()));
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        } catch (ParkingLotFullException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void status() {
        if(!isParkingLotCreated()) {
            System.out.println(PARKING_LOT_NOT_CREATED);
            return;
        }

        System.out.println(SLOT_NO + "    " + REGISTRATION_NO + "    " + Color);
        parkingLot.getOccupiedSlots().forEach(parkingSlot -> {
            System.out.println(
                    StringUtils.rightPadSpaces(Integer.toString(parkingSlot.getSlotNumber()), SLOT_NO.length()) + "    " +
                    StringUtils.rightPadSpaces(parkingSlot.getCar().getRegistrationNumber(), REGISTRATION_NO.length()) + "    " +
                    parkingSlot.getCar().getColor());
        });
    }


    private boolean isParkingLotCreated() {
        if(parkingLot == null) {
            return false;
        }
        return true;
    }

    public void leaveSlot(int slotNumber) {
        if(!isParkingLotCreated()) {
            System.out.println(PARKING_LOT_NOT_CREATED);
            return;
        }

        try {
            ParkingSlot  parkingSlot= parkingLot.leaveSlot(slotNumber);
            System.out.println(String.format(SLOT_FREE_MSG, slotNumber));
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        }
    }

    public void findCarsByColour(String colour) {
        if(!isParkingLotCreated()) {
            System.out.println(PARKING_LOT_NOT_CREATED);
            return;
        }

        try {
            List<String>  registrationNumbers = parkingLot.getRegistrationNumbersByColor(colour);
            System.out.println(registrationNumbers.size());
            for(int index = 0; index < registrationNumbers.size()-1; index ++) {
                System.out.print(registrationNumbers.get(index) + ", ");
            }
            if (registrationNumbers.size() > 0)
                System.out.println(registrationNumbers.get(registrationNumbers.size()-1));
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        }
    }

    public void findSlotsByColour(String colour) {
        if(!isParkingLotCreated()) {
            System.out.println(PARKING_LOT_NOT_CREATED);
            return;
        }

        try {
            List<Integer>  slotNumbers = parkingLot.getSlotNumbersByColor(colour);
            System.out.println(slotNumbers.size());
            for(int index = 0; index < slotNumbers.size()-1; index ++) {
                System.out.print(slotNumbers.get(index) + ", ");
            }
            if (slotNumbers.size() > 0)
                System.out.println(slotNumbers.get(slotNumbers.size()-1));
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        }
    }

    public void findSlotByRegistrationNumber(String registrationNumber) {
        if(!isParkingLotCreated()) {
            System.out.println(PARKING_LOT_NOT_CREATED);
            return;
        }

        try {
            Optional<Integer> slotNumber = parkingLot.getSlotNumberByRegistrationNumber(registrationNumber);
            if (!slotNumber.isPresent()) {
                System.out.println("Not found");
            }
            else {
                System.out.println(slotNumber.get());
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Bad input: " + ex.getMessage());
        }
    }
}
