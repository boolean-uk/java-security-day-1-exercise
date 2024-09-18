package com.booleanuk.api.model.DTO;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LendDTO {
    private User userId;
    private Game gameId;
}
