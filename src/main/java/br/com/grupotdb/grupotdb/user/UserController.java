package br.com.grupotdb.grupotdb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/criar-usuario")
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = this.userRepository.findByemail(userModel.getEmail());
        if (user != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);
        var userCreated = this.userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }

    @GetMapping("/list-users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody UserModel login) {
        UserModel user = this.userRepository.findByemail(login.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorreta");
        }

        if (BCrypt.verifyer().verify(login.getPassword().toCharArray(), user.getPassword()).verified) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha incorreta");
        }
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<UserModel> getProfile(@PathVariable String email) {
        UserModel user = this.userRepository.findByemail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile/{email}")
    public ResponseEntity<UserModel> updateProfile(@PathVariable String email, @RequestBody UserModel updatedUser) {
        UserModel user = this.userRepository.findByemail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        user.setCidade(updatedUser.getCidade());
        user.setName(updatedUser.getName());
       

        this.userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}
