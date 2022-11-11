package ru.nsu.fit.crocodile.message.Server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewMasterMessage {
   private final Long masterId;
}
