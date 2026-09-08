package com.example.regattend.model.dao;

import com.example.regattend.model.entity.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    boolean crear(Usuario usuario);
    Usuario buscarPorCorreo(String correo);
    List<Usuario> listarActivos();
    boolean actualizar(Usuario usuario);
    boolean desactivar(int id);
}
