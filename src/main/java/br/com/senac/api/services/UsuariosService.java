package br.com.senac.api.services;

import br.com.senac.api.dtos.UsuariosRequestDto;
import br.com.senac.api.entidades.Usuarios;
import br.com.senac.api.repositorios.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService implements UserDetailsService {

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuarios criar(UsuariosRequestDto dto) {
        if(dto.getLogin() == null || dto.getSenha() == null) {
            throw new RuntimeException("Login e senha são obrigatórios!");
        }
        if(usuariosRepositorio.findByLogin(dto.getLogin()).isPresent()) {
            throw new RuntimeException("Login já existe!");
        }

        Usuarios usuario = new Usuarios();
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));

        return usuariosRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuarios usuario = usuariosRepositorio.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));

        return User.withUsername(usuario.getLogin())
                .password(usuario.getSenha())
                .roles("USER")
                .build();
    }
}
