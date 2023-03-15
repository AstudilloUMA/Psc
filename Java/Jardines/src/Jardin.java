public class Jardin {
    int visitantes;

    public void entrar(int id){
        visitantes++;
        System.out.println("Entrar por la puerta " + id + ". Total " + visitantes);
    }

    public int publicarVisitantes(){
        return visitantes;
    }
}
