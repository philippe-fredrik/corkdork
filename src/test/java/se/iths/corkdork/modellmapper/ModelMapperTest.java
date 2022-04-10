//package se.iths.corkdork.modellmapper;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import se.iths.corkdork.dtos.Role;
//import se.iths.corkdork.dtos.User;
//import se.iths.corkdork.entity.RoleEntity;
//import se.iths.corkdork.entity.UserEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class UserModelMapperTest {
//
//
//    private final ModelMapper modelMapper = new ModelMapper();
//
//    @Test
//    void testModelMapperToEntity() {
//        User dto = new User("frege", "eriksson", "frege", "123", "test@test.se", new Role());
//
//
//        UserEntity entity = modelMapper.map(dto, UserEntity.class);
//
//        assertEquals(entity.getId(), dto.getId());
//        assertEquals(entity.getFirstName(), dto.getFirstName());
//        assertEquals(entity.getLastName(), dto.getLastName());
//        assertEquals(entity.getUsername(), dto.getUsername());
//        assertEquals(entity.getPassword(), dto.getPassword());
//        assertEquals(entity.getEmail(), dto.getEmail());
//
//    }
//
//    @Test
//    void testModelMapperToDto() {
//        UserEntity entity = new UserEntity("frege", "eriksson", "frege", "123", "test@test.se", new RoleEntity());
//
//        User dto = modelMapper.map(entity, User.class);
//
//        assertEquals(dto.getId(), entity.getId());
//        assertEquals(dto.getFirstName(), entity.getFirstName());
//        assertEquals(dto.getLastName(), entity.getLastName());
//        assertEquals(dto.getUsername(), entity.getUsername());
//        assertEquals(dto.getPassword(), entity.getPassword());
//        assertEquals(dto.getEmail(), entity.getEmail());
//    }
//}