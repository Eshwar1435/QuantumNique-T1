package services;

import models.Ticket;

public interface FeeStrategy {
    double calculateFee(Ticket ticket);
}
