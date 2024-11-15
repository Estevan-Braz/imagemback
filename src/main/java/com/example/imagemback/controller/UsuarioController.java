package com.example.imagemback.controller;
 import com.example.imagemback.model.Usuario;
 import com.example.imagemback.repository.UsuarioRepositorio;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.*;
 import org.springframework.web.multipart.MultipartFile;
 import java.io.File;
 import java.io.IOException;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    private static String UPLOAD_DIR = "uploads/";

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Usuario> registerUser(
            @RequestParam("nome") String nome,
            @RequestParam("email") String email,
            @RequestParam("senha") String senha,
            @RequestParam("foto") MultipartFile foto) {
        String photoPath = savePhoto(foto);
        Usuario user = new Usuario();
        user.setNome(nome);
        user.setEmail(email);
        user.setSenha(senha);
        user.setCaminhoFoto(photoPath);
        Usuario savedUser = usuarioRepositorio.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    private String savePhoto(MultipartFile photo) {
        if (photo.isEmpty()) {
            return null;
        }
        try {
            byte[] bytes = photo.getBytes();
            Path path = Paths.get(UPLOAD_DIR + photo.getOriginalFilename());
            Files.write(path, bytes);
            return path.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Usuario> getAllUsers() {
        return usuarioRepositorio.findAll();
    }
}