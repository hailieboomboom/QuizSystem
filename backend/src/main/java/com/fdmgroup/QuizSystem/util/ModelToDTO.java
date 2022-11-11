package com.fdmgroup.QuizSystem.util;

import com.fdmgroup.QuizSystem.dto.UserOutputDTO;
import com.fdmgroup.QuizSystem.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelToDTO {

    private ModelMapper modelMapper;

    @Autowired
    public ModelToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.typeMap(User.class, UserOutputDTO.class)
                .addMapping(User::getRole, UserOutputDTO::setRole);
    }

    public UserOutputDTO userToOutput(User user){
        return modelMapper.map(user, UserOutputDTO.class);
    }



}
