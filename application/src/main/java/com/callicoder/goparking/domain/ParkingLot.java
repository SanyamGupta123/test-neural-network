package com.callicoder.goparking.domain;

import com.callicoder.goparking.exceptions.ParkingLotFullException;
import com.callicoder.goparking.exceptions.SlotNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLot {
    private final int numSlots;
    private final int numFloors;
    private SortedSet<ParkingSlot> availableSlots = new TreeSet<>();
    private Set<ParkingSlot> occupiedSlots = new HashSet<>();

    public ParkingLot(int numSlots) {
        if(numSlots <= 0) {
            throw new IllegalArgumentException("Number of slots in the Parking Lot must be greater than zero.");
        }

        // Assuming Single floor since only numSlots are specified in the input.
        this.numSlots = numSlots;
        this.numFloors = 1;

        for(int i = 0; i < numSlots; i++) {
            ParkingSlot parkingSlot = new ParkingSlot(i+1, 1);
            this.availableSlots.add(parkingSlot);
        }
    }

    public synchronized Ticket reserveSlot(Car car) {
        if(car == null) {
            throw new IllegalArgumentException("Car must not be null");
        }

        if(this.isFull()) {
            throw new ParkingLotFullException();
        }

        ParkingSlot nearestSlot = this.availableSlots.first();

        nearestSlot.reserve(car);
        this.availableSlots.remove(nearestSlot);
        this.occupiedSlots.add(nearestSlot);

        return new Ticket(nearestSlot.getSlotNumber(), car.getRegistrationNumber(), car.getColor());
    }

    public ParkingSlot leaveSlot(int slotNumber) {
        //TODO: implement leave
        ParkingSlot temp = new ParkingSlot(slotNumber, 1);
        boolean isSlotFilled = this.getOccupiedSlots().contains(temp);
        ParkingSlot parkingSlot = new ParkingSlot(slotNumber, 1);
        if (isSlotFilled) {
            this.occupiedSlots.remove(temp);
            this.availableSlots.add(parkingSlot);
        }
        else {
            if (this.numSlots < slotNumber) {
                throw new SlotNotFoundException(slotNumber);
            }
            throw new IllegalArgumentException("Parking slot already empty");
        }
        return parkingSlot;
    }

    public boolean isFull() {
        return this.availableSlots.isEmpty();
    }

    public List<String> getRegistrationNumbersByColor(String color) {
        //TODO: implement getRegistrationNumbersByColor
        List<String> registrationNumbers = new ArrayList<String>();
        Iterator<ParkingSlot> slots = this.getOccupiedSlots().iterator();
        while(slots.hasNext()) {

            Car car = slots.next().getCar();
            System.out.println(car.getColor());
            if (car.getColor().equals(color)) {
                registrationNumbers.add(car.getRegistrationNumber());
            }
        }
        return registrationNumbers;
    }

    public List<Integer> getSlotNumbersByColor(String color) {
        //TODO: implement getSlotNumbersByColor
        List<Integer> slotNumbers = new ArrayList<Integer>();
        Iterator<ParkingSlot> slots = this.getOccupiedSlots().iterator();
        while(slots.hasNext()) {

            ParkingSlot slot = slots.next();
            System.out.println(slot.getCar().getColor());
            if (slot.getCar().getColor().equals(color)) {
                slotNumbers.add(slot.getSlotNumber());
            }
        }
        return slotNumbers;
    }

    public Optional<Integer> getSlotNumberByRegistrationNumber(String registrationNumber) {
        //TODO: implement getSlotNumberByRegistrationNumber
        Optional<Integer> slotNumber = Optional.empty();
        Iterator<ParkingSlot> slots = this.getOccupiedSlots().iterator();
        while(slots.hasNext()) {

            ParkingSlot slot = slots.next();
            System.out.println(slot.getCar().getRegistrationNumber());
            if (slot.getCar().getRegistrationNumber().equals(registrationNumber)) {
                slotNumber = Optional.of(slot.getSlotNumber());
                break;
            }
        }
        return slotNumber;
    }


    public int getNumSlots() {
        return numSlots;
    }

    public int getNumFloors() {
        return numFloors;
    }

    public SortedSet<ParkingSlot> getAvailableSlots() {
        return availableSlots;
    }

    public Set<ParkingSlot> getOccupiedSlots() {
        return occupiedSlots;
    }
}
