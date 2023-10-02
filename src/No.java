public class No {
    private Dados dados;
    private No next;

    public No(Dados dados){
        this.dados = dados;
        next = null;
    }

    public Dados getDados() {
        return dados;
    }

    public No getNext() {
        return next;
    }

    public void setNext(No next) {
        this.next = next;
    }
}