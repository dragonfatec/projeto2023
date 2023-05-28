package backend.usuario;

public enum Situacao {
    ATIVO{
        public Boolean verificaSituacao(String situacao){
            if (Situacao.ATIVO.toString() == situacao){
                return true;
            }else {
                return false;
            }
        }
    },
    INATIVO{
        public Boolean verificaSituacao(String situacao){
            if (Situacao.INATIVO.toString() == situacao){
                return true;
            }else {
                return false;
            }
        }
    };
    public abstract Boolean verificaSituacao(String situacao);
}
