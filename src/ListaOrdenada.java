import java.util.NoSuchElementException;
import java.util.Objects;

public class ListaOrdenada {
    private No head;

    private void addFirst(Dados value) {
        No no = new No(value);
        no.setNext(head);
        head = no;
    }

    public int size(){
        int i = 0;
        No size = head;
        while(size != null){
            i++;
            size = size.getNext();
        }
        return i;
    }
    public void addOrdered(Dados value) {
        if(isEmpty() || head.getDados().getIntervalo() > value.getIntervalo()){
            addFirst(value);
        }else{
            No aux = head;
            No no = new No(value);
            while (aux.getNext() != null) {
                if(aux.getNext().getDados().getIntervalo() > value.getIntervalo()){
                    break;
                }
                aux = aux.getNext();
            }
            no.setNext(aux.getNext());
            aux.setNext(no);
        }
    }

    public Dados search(Integer valueSearched) {
        if(isEmpty()){
            return null;
        }
        No search = head;
        while(search != null){
            if(search.getDados().getIntervalo() == valueSearched){
                return search.getDados();
            }
            search = search.getNext();
        }
        return null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void delete(Integer value) {
        if (isEmpty()) {
            throw new NoSuchElementException("Lista vazia.");
        }
        if (Objects.equals(head.getDados().getIntervalo(), value)) {
            head = head.getNext();
        } else {
            No search = head;
            while (search != null) {
                if (Objects.equals(search.getNext().getDados().getIntervalo(), value)) {
                    search.setNext(search.getNext().getNext());
                    return;
                }
                search = search.getNext();
            }
        }
    }
}