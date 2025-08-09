//package com.slavomirlobotka.dailyroutineforkids.mappers;
//
//import com.slavomirlobotka.dailyroutineforkids.dtos.DisplayChildDTO;
//import com.slavomirlobotka.dailyroutineforkids.models.Child;
//
//import java.util.List;
//
//public class ChildMapper {
//    public static DisplayChildDTO toDTO(Child child) {
//        return new DisplayChildDTO(child.getId(), child.getName());
//    }
//
//    public static List<DisplayChildDTO> toDTOList(List<Child> children) {
//        return children.stream()
//                .map(ChildMapper::toDTO)
//                .toList();
//    }
//}
