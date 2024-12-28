package br.com.eremate.ecommerce.service;

import br.com.eremate.ecommerce.exception.ResourceNotFoundException;
import br.com.eremate.ecommerce.model.User;
import br.com.eremate.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User salvarUsuario(User user) {
        return userRepository.save(user);
    }

    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    public User buscarPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + id));
    }

    public void deletarUsuario(Long id) {
        userRepository.deleteById(id);
    }
}
