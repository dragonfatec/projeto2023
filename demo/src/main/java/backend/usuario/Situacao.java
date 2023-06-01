package backend.usuario;

public enum Situacao {
    Ativo {
        public Boolean verificaSituacao(String situacao){
            if (Situacao.Ativo.toString() == situacao){
                return true;
            }else {
                return false;
            }
        }
    },
    Inativo {
        public Boolean verificaSituacao(String situacao){
            if (Situacao.Inativo.toString() == situacao){
                return true;
            }else {
                return false;
            }
        }
    };
    public abstract Boolean verificaSituacao(String situacao);
}
