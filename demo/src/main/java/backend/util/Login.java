package backend.util;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;

import java.util.Map;

public class Login {
    public static boolean verificarLogin(String user, String senha, boolean setInstanciaUsuario) {
        ConnectionFactory conn = new ConnectionFactory();
        Map<Integer, Usuario> usuarios = conn.getUsuarios();

        boolean result =  user.equals("lukas") && senha.equals("12345");

        if (result && setInstanciaUsuario){
            Usuario usuario = usuarios.get(0);
            Usuario.criarInstancia(usuario.getLogin(), usuario.getSenha(), usuario.getMatricula(), usuario.getNome(), usuario.getCargo(), usuario.getId_equipe());
        }

        return result;
    }
}
