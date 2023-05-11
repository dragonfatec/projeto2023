package backend.util;

import backend.usuario.Usuario;
import database.conexao.ConnectionFactory;

import java.util.Map;

public class Login {
    public static boolean verificarLogin(String user, String senha, boolean setInstanciaUsuario) {
        ConnectionFactory conn = new ConnectionFactory();
//        Map<Integer, Usuario> usuarios = conn.getUsuario(user, senha);
        Usuario usuario = conn.getUsuario(user, senha).get(0);

//        boolean result =  user.equals(usuario.getLogin()) && Criptografia.criptografar(senha).equals(usuario.getSenha());
        boolean result =  user.equals(usuario.getLogin()) && senha.equals(usuario.getSenha());

        if (result && setInstanciaUsuario){
            Usuario.criarInstancia(usuario.getLogin(), usuario.getSenha(), usuario.getMatricula(), usuario.getNome(), usuario.getCargo(), usuario.getId_equipe());
        }

        return result;
    }
}
